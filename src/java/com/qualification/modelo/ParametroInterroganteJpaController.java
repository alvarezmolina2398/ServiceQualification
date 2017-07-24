/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualification.modelo;

import com.qualification.modelo.exceptions.NonexistentEntityException;
import com.qualification.modelo.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Desarrollo
 */
public class ParametroInterroganteJpaController implements Serializable {

    public ParametroInterroganteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ParametroInterrogante parametroInterrogante) throws PreexistingEntityException, Exception {
        if (parametroInterrogante.getParametroInterrogantePK() == null) {
            parametroInterrogante.setParametroInterrogantePK(new ParametroInterrogantePK());
        }
        char paramet[];
        paramet = parametroInterrogante.getParametro().getIdparametro().toCharArray();
        parametroInterrogante.getParametroInterrogantePK().setParametroIdparametro(paramet[0]);
        parametroInterrogante.getParametroInterrogantePK().setInterroganteIdinterrogante(parametroInterrogante.getInterrogante().getIdinterrogante());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Interrogante interrogante = parametroInterrogante.getInterrogante();
            if (interrogante != null) {
                interrogante = em.getReference(interrogante.getClass(), interrogante.getIdinterrogante());
                parametroInterrogante.setInterrogante(interrogante);
            }
            Parametro parametro = parametroInterrogante.getParametro();
            if (parametro != null) {
                parametro = em.getReference(parametro.getClass(), parametro.getIdparametro());
                parametroInterrogante.setParametro(parametro);
            }
            em.persist(parametroInterrogante);
            if (interrogante != null) {
                interrogante.getParametroInterroganteList().add(parametroInterrogante);
                interrogante = em.merge(interrogante);
            }
            if (parametro != null) {
                parametro.getParametroInterroganteList().add(parametroInterrogante);
                parametro = em.merge(parametro);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findParametroInterrogante(parametroInterrogante.getParametroInterrogantePK()) != null) {
                throw new PreexistingEntityException("ParametroInterrogante " + parametroInterrogante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ParametroInterrogante parametroInterrogante) throws NonexistentEntityException, Exception {
        char parameter[];
        parameter = parametroInterrogante.getParametro().getIdparametro().toCharArray();
        parametroInterrogante.getParametroInterrogantePK().setParametroIdparametro(parameter[0]);
        parametroInterrogante.getParametroInterrogantePK().setInterroganteIdinterrogante(parametroInterrogante.getInterrogante().getIdinterrogante());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ParametroInterrogante persistentParametroInterrogante = em.find(ParametroInterrogante.class, parametroInterrogante.getParametroInterrogantePK());
            Interrogante interroganteOld = persistentParametroInterrogante.getInterrogante();
            Interrogante interroganteNew = parametroInterrogante.getInterrogante();
            Parametro parametroOld = persistentParametroInterrogante.getParametro();
            Parametro parametroNew = parametroInterrogante.getParametro();
            if (interroganteNew != null) {
                interroganteNew = em.getReference(interroganteNew.getClass(), interroganteNew.getIdinterrogante());
                parametroInterrogante.setInterrogante(interroganteNew);
            }
            if (parametroNew != null) {
                parametroNew = em.getReference(parametroNew.getClass(), parametroNew.getIdparametro());
                parametroInterrogante.setParametro(parametroNew);
            }
            parametroInterrogante = em.merge(parametroInterrogante);
            if (interroganteOld != null && !interroganteOld.equals(interroganteNew)) {
                interroganteOld.getParametroInterroganteList().remove(parametroInterrogante);
                interroganteOld = em.merge(interroganteOld);
            }
            if (interroganteNew != null && !interroganteNew.equals(interroganteOld)) {
                interroganteNew.getParametroInterroganteList().add(parametroInterrogante);
                interroganteNew = em.merge(interroganteNew);
            }
            if (parametroOld != null && !parametroOld.equals(parametroNew)) {
                parametroOld.getParametroInterroganteList().remove(parametroInterrogante);
                parametroOld = em.merge(parametroOld);
            }
            if (parametroNew != null && !parametroNew.equals(parametroOld)) {
                parametroNew.getParametroInterroganteList().add(parametroInterrogante);
                parametroNew = em.merge(parametroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ParametroInterrogantePK id = parametroInterrogante.getParametroInterrogantePK();
                if (findParametroInterrogante(id) == null) {
                    throw new NonexistentEntityException("The parametroInterrogante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ParametroInterrogantePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ParametroInterrogante parametroInterrogante;
            try {
                parametroInterrogante = em.getReference(ParametroInterrogante.class, id);
                parametroInterrogante.getParametroInterrogantePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parametroInterrogante with id " + id + " no longer exists.", enfe);
            }
            Interrogante interrogante = parametroInterrogante.getInterrogante();
            if (interrogante != null) {
                interrogante.getParametroInterroganteList().remove(parametroInterrogante);
                interrogante = em.merge(interrogante);
            }
            Parametro parametro = parametroInterrogante.getParametro();
            if (parametro != null) {
                parametro.getParametroInterroganteList().remove(parametroInterrogante);
                parametro = em.merge(parametro);
            }
            em.remove(parametroInterrogante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ParametroInterrogante> findParametroInterroganteEntities() {
        return findParametroInterroganteEntities(true, -1, -1);
    }

    public List<ParametroInterrogante> findParametroInterroganteEntities(int maxResults, int firstResult) {
        return findParametroInterroganteEntities(false, maxResults, firstResult);
    }

    private List<ParametroInterrogante> findParametroInterroganteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParametroInterrogante.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ParametroInterrogante findParametroInterrogante(ParametroInterrogantePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ParametroInterrogante.class, id);
        } finally {
            em.close();
        }
    }

    public int getParametroInterroganteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ParametroInterrogante> rt = cq.from(ParametroInterrogante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

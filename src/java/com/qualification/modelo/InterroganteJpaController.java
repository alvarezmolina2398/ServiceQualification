/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualification.modelo;

import com.qualification.modelo.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Desarrollo
 */
public class InterroganteJpaController implements Serializable {

    public InterroganteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Interrogante interrogante) {
        if (interrogante.getParametroList() == null) {
            interrogante.setParametroList(new ArrayList<Parametro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Parametro> attachedParametroList = new ArrayList<Parametro>();
            for (Parametro parametroListParametroToAttach : interrogante.getParametroList()) {
                parametroListParametroToAttach = em.getReference(parametroListParametroToAttach.getClass(), parametroListParametroToAttach.getIdparametro());
                attachedParametroList.add(parametroListParametroToAttach);
            }
            interrogante.setParametroList(attachedParametroList);
            em.persist(interrogante);
            for (Parametro parametroListParametro : interrogante.getParametroList()) {
                parametroListParametro.getInterroganteList().add(interrogante);
                parametroListParametro = em.merge(parametroListParametro);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Interrogante interrogante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Interrogante persistentInterrogante = em.find(Interrogante.class, interrogante.getIdinterrogante());
            List<Parametro> parametroListOld = persistentInterrogante.getParametroList();
            List<Parametro> parametroListNew = interrogante.getParametroList();
            List<Parametro> attachedParametroListNew = new ArrayList<Parametro>();
            for (Parametro parametroListNewParametroToAttach : parametroListNew) {
                parametroListNewParametroToAttach = em.getReference(parametroListNewParametroToAttach.getClass(), parametroListNewParametroToAttach.getIdparametro());
                attachedParametroListNew.add(parametroListNewParametroToAttach);
            }
            parametroListNew = attachedParametroListNew;
            interrogante.setParametroList(parametroListNew);
            interrogante = em.merge(interrogante);
            for (Parametro parametroListOldParametro : parametroListOld) {
                if (!parametroListNew.contains(parametroListOldParametro)) {
                    parametroListOldParametro.getInterroganteList().remove(interrogante);
                    parametroListOldParametro = em.merge(parametroListOldParametro);
                }
            }
            for (Parametro parametroListNewParametro : parametroListNew) {
                if (!parametroListOld.contains(parametroListNewParametro)) {
                    parametroListNewParametro.getInterroganteList().add(interrogante);
                    parametroListNewParametro = em.merge(parametroListNewParametro);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = interrogante.getIdinterrogante();
                if (findInterrogante(id) == null) {
                    throw new NonexistentEntityException("The interrogante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Interrogante interrogante;
            try {
                interrogante = em.getReference(Interrogante.class, id);
                interrogante.getIdinterrogante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The interrogante with id " + id + " no longer exists.", enfe);
            }
            List<Parametro> parametroList = interrogante.getParametroList();
            for (Parametro parametroListParametro : parametroList) {
                parametroListParametro.getInterroganteList().remove(interrogante);
                parametroListParametro = em.merge(parametroListParametro);
            }
            em.remove(interrogante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Interrogante> findInterroganteEntities() {
        return findInterroganteEntities(true, -1, -1);
    }

    public List<Interrogante> findInterroganteEntities(int maxResults, int firstResult) {
        return findInterroganteEntities(false, maxResults, firstResult);
    }

    private List<Interrogante> findInterroganteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Interrogante.class));
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

    public Interrogante findInterrogante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Interrogante.class, id);
        } finally {
            em.close();
        }
    }

    public int getInterroganteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Interrogante> rt = cq.from(Interrogante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

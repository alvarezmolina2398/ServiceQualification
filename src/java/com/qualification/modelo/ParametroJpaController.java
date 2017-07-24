/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualification.modelo;

import com.qualification.modelo.exceptions.IllegalOrphanException;
import com.qualification.modelo.exceptions.NonexistentEntityException;
import com.qualification.modelo.exceptions.PreexistingEntityException;
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
public class ParametroJpaController implements Serializable {

    public ParametroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Parametro parametro) throws PreexistingEntityException, Exception {
        if (parametro.getParametroInterroganteList() == null) {
            parametro.setParametroInterroganteList(new ArrayList<ParametroInterrogante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ParametroInterrogante> attachedParametroInterroganteList = new ArrayList<ParametroInterrogante>();
            for (ParametroInterrogante parametroInterroganteListParametroInterroganteToAttach : parametro.getParametroInterroganteList()) {
                parametroInterroganteListParametroInterroganteToAttach = em.getReference(parametroInterroganteListParametroInterroganteToAttach.getClass(), parametroInterroganteListParametroInterroganteToAttach.getParametroInterrogantePK());
                attachedParametroInterroganteList.add(parametroInterroganteListParametroInterroganteToAttach);
            }
            parametro.setParametroInterroganteList(attachedParametroInterroganteList);
            em.persist(parametro);
            for (ParametroInterrogante parametroInterroganteListParametroInterrogante : parametro.getParametroInterroganteList()) {
                Parametro oldParametroOfParametroInterroganteListParametroInterrogante = parametroInterroganteListParametroInterrogante.getParametro();
                parametroInterroganteListParametroInterrogante.setParametro(parametro);
                parametroInterroganteListParametroInterrogante = em.merge(parametroInterroganteListParametroInterrogante);
                if (oldParametroOfParametroInterroganteListParametroInterrogante != null) {
                    oldParametroOfParametroInterroganteListParametroInterrogante.getParametroInterroganteList().remove(parametroInterroganteListParametroInterrogante);
                    oldParametroOfParametroInterroganteListParametroInterrogante = em.merge(oldParametroOfParametroInterroganteListParametroInterrogante);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findParametro(parametro.getIdparametro()) != null) {
                throw new PreexistingEntityException("Parametro " + parametro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Parametro parametro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Parametro persistentParametro = em.find(Parametro.class, parametro.getIdparametro());
            List<ParametroInterrogante> parametroInterroganteListOld = persistentParametro.getParametroInterroganteList();
            List<ParametroInterrogante> parametroInterroganteListNew = parametro.getParametroInterroganteList();
            List<String> illegalOrphanMessages = null;
            for (ParametroInterrogante parametroInterroganteListOldParametroInterrogante : parametroInterroganteListOld) {
                if (!parametroInterroganteListNew.contains(parametroInterroganteListOldParametroInterrogante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ParametroInterrogante " + parametroInterroganteListOldParametroInterrogante + " since its parametro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ParametroInterrogante> attachedParametroInterroganteListNew = new ArrayList<ParametroInterrogante>();
            for (ParametroInterrogante parametroInterroganteListNewParametroInterroganteToAttach : parametroInterroganteListNew) {
                parametroInterroganteListNewParametroInterroganteToAttach = em.getReference(parametroInterroganteListNewParametroInterroganteToAttach.getClass(), parametroInterroganteListNewParametroInterroganteToAttach.getParametroInterrogantePK());
                attachedParametroInterroganteListNew.add(parametroInterroganteListNewParametroInterroganteToAttach);
            }
            parametroInterroganteListNew = attachedParametroInterroganteListNew;
            parametro.setParametroInterroganteList(parametroInterroganteListNew);
            parametro = em.merge(parametro);
            for (ParametroInterrogante parametroInterroganteListNewParametroInterrogante : parametroInterroganteListNew) {
                if (!parametroInterroganteListOld.contains(parametroInterroganteListNewParametroInterrogante)) {
                    Parametro oldParametroOfParametroInterroganteListNewParametroInterrogante = parametroInterroganteListNewParametroInterrogante.getParametro();
                    parametroInterroganteListNewParametroInterrogante.setParametro(parametro);
                    parametroInterroganteListNewParametroInterrogante = em.merge(parametroInterroganteListNewParametroInterrogante);
                    if (oldParametroOfParametroInterroganteListNewParametroInterrogante != null && !oldParametroOfParametroInterroganteListNewParametroInterrogante.equals(parametro)) {
                        oldParametroOfParametroInterroganteListNewParametroInterrogante.getParametroInterroganteList().remove(parametroInterroganteListNewParametroInterrogante);
                        oldParametroOfParametroInterroganteListNewParametroInterrogante = em.merge(oldParametroOfParametroInterroganteListNewParametroInterrogante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = parametro.getIdparametro();
                if (findParametro(id) == null) {
                    throw new NonexistentEntityException("The parametro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Parametro parametro;
            try {
                parametro = em.getReference(Parametro.class, id);
                parametro.getIdparametro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parametro with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ParametroInterrogante> parametroInterroganteListOrphanCheck = parametro.getParametroInterroganteList();
            for (ParametroInterrogante parametroInterroganteListOrphanCheckParametroInterrogante : parametroInterroganteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Parametro (" + parametro + ") cannot be destroyed since the ParametroInterrogante " + parametroInterroganteListOrphanCheckParametroInterrogante + " in its parametroInterroganteList field has a non-nullable parametro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(parametro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Parametro> findParametroEntities() {
        return findParametroEntities(true, -1, -1);
    }

    public List<Parametro> findParametroEntities(int maxResults, int firstResult) {
        return findParametroEntities(false, maxResults, firstResult);
    }

    private List<Parametro> findParametroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Parametro.class));
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

    public Parametro findParametro(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Parametro.class, id);
        } finally {
            em.close();
        }
    }

    public int getParametroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Parametro> rt = cq.from(Parametro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualification.modelo;

import com.qualification.modelo.exceptions.IllegalOrphanException;
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
        if (interrogante.getParametroInterroganteList() == null) {
            interrogante.setParametroInterroganteList(new ArrayList<ParametroInterrogante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ParametroInterrogante> attachedParametroInterroganteList = new ArrayList<ParametroInterrogante>();
            for (ParametroInterrogante parametroInterroganteListParametroInterroganteToAttach : interrogante.getParametroInterroganteList()) {
                parametroInterroganteListParametroInterroganteToAttach = em.getReference(parametroInterroganteListParametroInterroganteToAttach.getClass(), parametroInterroganteListParametroInterroganteToAttach.getParametroInterrogantePK());
                attachedParametroInterroganteList.add(parametroInterroganteListParametroInterroganteToAttach);
            }
            interrogante.setParametroInterroganteList(attachedParametroInterroganteList);
            em.persist(interrogante);
            for (ParametroInterrogante parametroInterroganteListParametroInterrogante : interrogante.getParametroInterroganteList()) {
                Interrogante oldInterroganteOfParametroInterroganteListParametroInterrogante = parametroInterroganteListParametroInterrogante.getInterrogante();
                parametroInterroganteListParametroInterrogante.setInterrogante(interrogante);
                parametroInterroganteListParametroInterrogante = em.merge(parametroInterroganteListParametroInterrogante);
                if (oldInterroganteOfParametroInterroganteListParametroInterrogante != null) {
                    oldInterroganteOfParametroInterroganteListParametroInterrogante.getParametroInterroganteList().remove(parametroInterroganteListParametroInterrogante);
                    oldInterroganteOfParametroInterroganteListParametroInterrogante = em.merge(oldInterroganteOfParametroInterroganteListParametroInterrogante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Interrogante interrogante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Interrogante persistentInterrogante = em.find(Interrogante.class, interrogante.getIdinterrogante());
            List<ParametroInterrogante> parametroInterroganteListOld = persistentInterrogante.getParametroInterroganteList();
            List<ParametroInterrogante> parametroInterroganteListNew = interrogante.getParametroInterroganteList();
            List<String> illegalOrphanMessages = null;
            for (ParametroInterrogante parametroInterroganteListOldParametroInterrogante : parametroInterroganteListOld) {
                if (!parametroInterroganteListNew.contains(parametroInterroganteListOldParametroInterrogante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ParametroInterrogante " + parametroInterroganteListOldParametroInterrogante + " since its interrogante field is not nullable.");
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
            interrogante.setParametroInterroganteList(parametroInterroganteListNew);
            interrogante = em.merge(interrogante);
            for (ParametroInterrogante parametroInterroganteListNewParametroInterrogante : parametroInterroganteListNew) {
                if (!parametroInterroganteListOld.contains(parametroInterroganteListNewParametroInterrogante)) {
                    Interrogante oldInterroganteOfParametroInterroganteListNewParametroInterrogante = parametroInterroganteListNewParametroInterrogante.getInterrogante();
                    parametroInterroganteListNewParametroInterrogante.setInterrogante(interrogante);
                    parametroInterroganteListNewParametroInterrogante = em.merge(parametroInterroganteListNewParametroInterrogante);
                    if (oldInterroganteOfParametroInterroganteListNewParametroInterrogante != null && !oldInterroganteOfParametroInterroganteListNewParametroInterrogante.equals(interrogante)) {
                        oldInterroganteOfParametroInterroganteListNewParametroInterrogante.getParametroInterroganteList().remove(parametroInterroganteListNewParametroInterrogante);
                        oldInterroganteOfParametroInterroganteListNewParametroInterrogante = em.merge(oldInterroganteOfParametroInterroganteListNewParametroInterrogante);
                    }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            List<ParametroInterrogante> parametroInterroganteListOrphanCheck = interrogante.getParametroInterroganteList();
            for (ParametroInterrogante parametroInterroganteListOrphanCheckParametroInterrogante : parametroInterroganteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Interrogante (" + interrogante + ") cannot be destroyed since the ParametroInterrogante " + parametroInterroganteListOrphanCheckParametroInterrogante + " in its parametroInterroganteList field has a non-nullable interrogante field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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

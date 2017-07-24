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
public class PromocionJpaController implements Serializable {

    public PromocionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Promocion promocion) {
        if (promocion.getPromocionCooperativaList() == null) {
            promocion.setPromocionCooperativaList(new ArrayList<PromocionCooperativa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PromocionCooperativa> attachedPromocionCooperativaList = new ArrayList<PromocionCooperativa>();
            for (PromocionCooperativa promocionCooperativaListPromocionCooperativaToAttach : promocion.getPromocionCooperativaList()) {
                promocionCooperativaListPromocionCooperativaToAttach = em.getReference(promocionCooperativaListPromocionCooperativaToAttach.getClass(), promocionCooperativaListPromocionCooperativaToAttach.getPromocionCooperativaPK());
                attachedPromocionCooperativaList.add(promocionCooperativaListPromocionCooperativaToAttach);
            }
            promocion.setPromocionCooperativaList(attachedPromocionCooperativaList);
            em.persist(promocion);
            for (PromocionCooperativa promocionCooperativaListPromocionCooperativa : promocion.getPromocionCooperativaList()) {
                Promocion oldPromocionOfPromocionCooperativaListPromocionCooperativa = promocionCooperativaListPromocionCooperativa.getPromocion();
                promocionCooperativaListPromocionCooperativa.setPromocion(promocion);
                promocionCooperativaListPromocionCooperativa = em.merge(promocionCooperativaListPromocionCooperativa);
                if (oldPromocionOfPromocionCooperativaListPromocionCooperativa != null) {
                    oldPromocionOfPromocionCooperativaListPromocionCooperativa.getPromocionCooperativaList().remove(promocionCooperativaListPromocionCooperativa);
                    oldPromocionOfPromocionCooperativaListPromocionCooperativa = em.merge(oldPromocionOfPromocionCooperativaListPromocionCooperativa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Promocion promocion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promocion persistentPromocion = em.find(Promocion.class, promocion.getIdpromocion());
            List<PromocionCooperativa> promocionCooperativaListOld = persistentPromocion.getPromocionCooperativaList();
            List<PromocionCooperativa> promocionCooperativaListNew = promocion.getPromocionCooperativaList();
            List<String> illegalOrphanMessages = null;
            for (PromocionCooperativa promocionCooperativaListOldPromocionCooperativa : promocionCooperativaListOld) {
                if (!promocionCooperativaListNew.contains(promocionCooperativaListOldPromocionCooperativa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PromocionCooperativa " + promocionCooperativaListOldPromocionCooperativa + " since its promocion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PromocionCooperativa> attachedPromocionCooperativaListNew = new ArrayList<PromocionCooperativa>();
            for (PromocionCooperativa promocionCooperativaListNewPromocionCooperativaToAttach : promocionCooperativaListNew) {
                promocionCooperativaListNewPromocionCooperativaToAttach = em.getReference(promocionCooperativaListNewPromocionCooperativaToAttach.getClass(), promocionCooperativaListNewPromocionCooperativaToAttach.getPromocionCooperativaPK());
                attachedPromocionCooperativaListNew.add(promocionCooperativaListNewPromocionCooperativaToAttach);
            }
            promocionCooperativaListNew = attachedPromocionCooperativaListNew;
            promocion.setPromocionCooperativaList(promocionCooperativaListNew);
            promocion = em.merge(promocion);
            for (PromocionCooperativa promocionCooperativaListNewPromocionCooperativa : promocionCooperativaListNew) {
                if (!promocionCooperativaListOld.contains(promocionCooperativaListNewPromocionCooperativa)) {
                    Promocion oldPromocionOfPromocionCooperativaListNewPromocionCooperativa = promocionCooperativaListNewPromocionCooperativa.getPromocion();
                    promocionCooperativaListNewPromocionCooperativa.setPromocion(promocion);
                    promocionCooperativaListNewPromocionCooperativa = em.merge(promocionCooperativaListNewPromocionCooperativa);
                    if (oldPromocionOfPromocionCooperativaListNewPromocionCooperativa != null && !oldPromocionOfPromocionCooperativaListNewPromocionCooperativa.equals(promocion)) {
                        oldPromocionOfPromocionCooperativaListNewPromocionCooperativa.getPromocionCooperativaList().remove(promocionCooperativaListNewPromocionCooperativa);
                        oldPromocionOfPromocionCooperativaListNewPromocionCooperativa = em.merge(oldPromocionOfPromocionCooperativaListNewPromocionCooperativa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = promocion.getIdpromocion();
                if (findPromocion(id) == null) {
                    throw new NonexistentEntityException("The promocion with id " + id + " no longer exists.");
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
            Promocion promocion;
            try {
                promocion = em.getReference(Promocion.class, id);
                promocion.getIdpromocion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The promocion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PromocionCooperativa> promocionCooperativaListOrphanCheck = promocion.getPromocionCooperativaList();
            for (PromocionCooperativa promocionCooperativaListOrphanCheckPromocionCooperativa : promocionCooperativaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Promocion (" + promocion + ") cannot be destroyed since the PromocionCooperativa " + promocionCooperativaListOrphanCheckPromocionCooperativa + " in its promocionCooperativaList field has a non-nullable promocion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(promocion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Promocion> findPromocionEntities() {
        return findPromocionEntities(true, -1, -1);
    }

    public List<Promocion> findPromocionEntities(int maxResults, int firstResult) {
        return findPromocionEntities(false, maxResults, firstResult);
    }

    private List<Promocion> findPromocionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Promocion.class));
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

    public Promocion findPromocion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Promocion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPromocionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Promocion> rt = cq.from(Promocion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

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
public class PromocionCooperativaJpaController implements Serializable {

    public PromocionCooperativaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PromocionCooperativa promocionCooperativa) throws PreexistingEntityException, Exception {
        if (promocionCooperativa.getPromocionCooperativaPK() == null) {
            promocionCooperativa.setPromocionCooperativaPK(new PromocionCooperativaPK());
        }
        promocionCooperativa.getPromocionCooperativaPK().setCooperativaIdcooperativa(promocionCooperativa.getCooperativa().getIdcooperativa());
        promocionCooperativa.getPromocionCooperativaPK().setPromocionIdpromocion(promocionCooperativa.getPromocion().getIdpromocion());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cooperativa cooperativa = promocionCooperativa.getCooperativa();
            if (cooperativa != null) {
                cooperativa = em.getReference(cooperativa.getClass(), cooperativa.getIdcooperativa());
                promocionCooperativa.setCooperativa(cooperativa);
            }
            Promocion promocion = promocionCooperativa.getPromocion();
            if (promocion != null) {
                promocion = em.getReference(promocion.getClass(), promocion.getIdpromocion());
                promocionCooperativa.setPromocion(promocion);
            }
            em.persist(promocionCooperativa);
            if (cooperativa != null) {
                cooperativa.getPromocionCooperativaList().add(promocionCooperativa);
                cooperativa = em.merge(cooperativa);
            }
            if (promocion != null) {
                promocion.getPromocionCooperativaList().add(promocionCooperativa);
                promocion = em.merge(promocion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPromocionCooperativa(promocionCooperativa.getPromocionCooperativaPK()) != null) {
                throw new PreexistingEntityException("PromocionCooperativa " + promocionCooperativa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PromocionCooperativa promocionCooperativa) throws NonexistentEntityException, Exception {
        promocionCooperativa.getPromocionCooperativaPK().setCooperativaIdcooperativa(promocionCooperativa.getCooperativa().getIdcooperativa());
        promocionCooperativa.getPromocionCooperativaPK().setPromocionIdpromocion(promocionCooperativa.getPromocion().getIdpromocion());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PromocionCooperativa persistentPromocionCooperativa = em.find(PromocionCooperativa.class, promocionCooperativa.getPromocionCooperativaPK());
            Cooperativa cooperativaOld = persistentPromocionCooperativa.getCooperativa();
            Cooperativa cooperativaNew = promocionCooperativa.getCooperativa();
            Promocion promocionOld = persistentPromocionCooperativa.getPromocion();
            Promocion promocionNew = promocionCooperativa.getPromocion();
            if (cooperativaNew != null) {
                cooperativaNew = em.getReference(cooperativaNew.getClass(), cooperativaNew.getIdcooperativa());
                promocionCooperativa.setCooperativa(cooperativaNew);
            }
            if (promocionNew != null) {
                promocionNew = em.getReference(promocionNew.getClass(), promocionNew.getIdpromocion());
                promocionCooperativa.setPromocion(promocionNew);
            }
            promocionCooperativa = em.merge(promocionCooperativa);
            if (cooperativaOld != null && !cooperativaOld.equals(cooperativaNew)) {
                cooperativaOld.getPromocionCooperativaList().remove(promocionCooperativa);
                cooperativaOld = em.merge(cooperativaOld);
            }
            if (cooperativaNew != null && !cooperativaNew.equals(cooperativaOld)) {
                cooperativaNew.getPromocionCooperativaList().add(promocionCooperativa);
                cooperativaNew = em.merge(cooperativaNew);
            }
            if (promocionOld != null && !promocionOld.equals(promocionNew)) {
                promocionOld.getPromocionCooperativaList().remove(promocionCooperativa);
                promocionOld = em.merge(promocionOld);
            }
            if (promocionNew != null && !promocionNew.equals(promocionOld)) {
                promocionNew.getPromocionCooperativaList().add(promocionCooperativa);
                promocionNew = em.merge(promocionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PromocionCooperativaPK id = promocionCooperativa.getPromocionCooperativaPK();
                if (findPromocionCooperativa(id) == null) {
                    throw new NonexistentEntityException("The promocionCooperativa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PromocionCooperativaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PromocionCooperativa promocionCooperativa;
            try {
                promocionCooperativa = em.getReference(PromocionCooperativa.class, id);
                promocionCooperativa.getPromocionCooperativaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The promocionCooperativa with id " + id + " no longer exists.", enfe);
            }
            Cooperativa cooperativa = promocionCooperativa.getCooperativa();
            if (cooperativa != null) {
                cooperativa.getPromocionCooperativaList().remove(promocionCooperativa);
                cooperativa = em.merge(cooperativa);
            }
            Promocion promocion = promocionCooperativa.getPromocion();
            if (promocion != null) {
                promocion.getPromocionCooperativaList().remove(promocionCooperativa);
                promocion = em.merge(promocion);
            }
            em.remove(promocionCooperativa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PromocionCooperativa> findPromocionCooperativaEntities() {
        return findPromocionCooperativaEntities(true, -1, -1);
    }

    public List<PromocionCooperativa> findPromocionCooperativaEntities(int maxResults, int firstResult) {
        return findPromocionCooperativaEntities(false, maxResults, firstResult);
    }

    private List<PromocionCooperativa> findPromocionCooperativaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PromocionCooperativa.class));
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

    public PromocionCooperativa findPromocionCooperativa(PromocionCooperativaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PromocionCooperativa.class, id);
        } finally {
            em.close();
        }
    }

    public int getPromocionCooperativaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PromocionCooperativa> rt = cq.from(PromocionCooperativa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

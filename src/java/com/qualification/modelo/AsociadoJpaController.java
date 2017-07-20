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
public class AsociadoJpaController implements Serializable {

    public AsociadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asociado asociado) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(asociado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAsociado(asociado.getNumeroTelefono()) != null) {
                throw new PreexistingEntityException("Asociado " + asociado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asociado asociado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            asociado = em.merge(asociado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = asociado.getNumeroTelefono();
                if (findAsociado(id) == null) {
                    throw new NonexistentEntityException("The asociado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asociado asociado;
            try {
                asociado = em.getReference(Asociado.class, id);
                asociado.getNumeroTelefono();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asociado with id " + id + " no longer exists.", enfe);
            }
            em.remove(asociado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asociado> findAsociadoEntities() {
        return findAsociadoEntities(true, -1, -1);
    }

    public List<Asociado> findAsociadoEntities(int maxResults, int firstResult) {
        return findAsociadoEntities(false, maxResults, firstResult);
    }

    private List<Asociado> findAsociadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asociado.class));
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

    public Asociado findAsociado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asociado.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsociadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asociado> rt = cq.from(Asociado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

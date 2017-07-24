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
public class CooperativaJpaController implements Serializable {

    public CooperativaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cooperativa cooperativa) {
        if (cooperativa.getPromocionCooperativaList() == null) {
            cooperativa.setPromocionCooperativaList(new ArrayList<PromocionCooperativa>());
        }
        if (cooperativa.getAgenciaList() == null) {
            cooperativa.setAgenciaList(new ArrayList<Agencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PromocionCooperativa> attachedPromocionCooperativaList = new ArrayList<PromocionCooperativa>();
            for (PromocionCooperativa promocionCooperativaListPromocionCooperativaToAttach : cooperativa.getPromocionCooperativaList()) {
                promocionCooperativaListPromocionCooperativaToAttach = em.getReference(promocionCooperativaListPromocionCooperativaToAttach.getClass(), promocionCooperativaListPromocionCooperativaToAttach.getPromocionCooperativaPK());
                attachedPromocionCooperativaList.add(promocionCooperativaListPromocionCooperativaToAttach);
            }
            cooperativa.setPromocionCooperativaList(attachedPromocionCooperativaList);
            List<Agencia> attachedAgenciaList = new ArrayList<Agencia>();
            for (Agencia agenciaListAgenciaToAttach : cooperativa.getAgenciaList()) {
                agenciaListAgenciaToAttach = em.getReference(agenciaListAgenciaToAttach.getClass(), agenciaListAgenciaToAttach.getIdagencia());
                attachedAgenciaList.add(agenciaListAgenciaToAttach);
            }
            cooperativa.setAgenciaList(attachedAgenciaList);
            em.persist(cooperativa);
            for (PromocionCooperativa promocionCooperativaListPromocionCooperativa : cooperativa.getPromocionCooperativaList()) {
                Cooperativa oldCooperativaOfPromocionCooperativaListPromocionCooperativa = promocionCooperativaListPromocionCooperativa.getCooperativa();
                promocionCooperativaListPromocionCooperativa.setCooperativa(cooperativa);
                promocionCooperativaListPromocionCooperativa = em.merge(promocionCooperativaListPromocionCooperativa);
                if (oldCooperativaOfPromocionCooperativaListPromocionCooperativa != null) {
                    oldCooperativaOfPromocionCooperativaListPromocionCooperativa.getPromocionCooperativaList().remove(promocionCooperativaListPromocionCooperativa);
                    oldCooperativaOfPromocionCooperativaListPromocionCooperativa = em.merge(oldCooperativaOfPromocionCooperativaListPromocionCooperativa);
                }
            }
            for (Agencia agenciaListAgencia : cooperativa.getAgenciaList()) {
                Cooperativa oldCooperativaIdcooperativaOfAgenciaListAgencia = agenciaListAgencia.getCooperativaIdcooperativa();
                agenciaListAgencia.setCooperativaIdcooperativa(cooperativa);
                agenciaListAgencia = em.merge(agenciaListAgencia);
                if (oldCooperativaIdcooperativaOfAgenciaListAgencia != null) {
                    oldCooperativaIdcooperativaOfAgenciaListAgencia.getAgenciaList().remove(agenciaListAgencia);
                    oldCooperativaIdcooperativaOfAgenciaListAgencia = em.merge(oldCooperativaIdcooperativaOfAgenciaListAgencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cooperativa cooperativa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cooperativa persistentCooperativa = em.find(Cooperativa.class, cooperativa.getIdcooperativa());
            List<PromocionCooperativa> promocionCooperativaListOld = persistentCooperativa.getPromocionCooperativaList();
            List<PromocionCooperativa> promocionCooperativaListNew = cooperativa.getPromocionCooperativaList();
            List<Agencia> agenciaListOld = persistentCooperativa.getAgenciaList();
            List<Agencia> agenciaListNew = cooperativa.getAgenciaList();
            List<String> illegalOrphanMessages = null;
            for (PromocionCooperativa promocionCooperativaListOldPromocionCooperativa : promocionCooperativaListOld) {
                if (!promocionCooperativaListNew.contains(promocionCooperativaListOldPromocionCooperativa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PromocionCooperativa " + promocionCooperativaListOldPromocionCooperativa + " since its cooperativa field is not nullable.");
                }
            }
            for (Agencia agenciaListOldAgencia : agenciaListOld) {
                if (!agenciaListNew.contains(agenciaListOldAgencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Agencia " + agenciaListOldAgencia + " since its cooperativaIdcooperativa field is not nullable.");
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
            cooperativa.setPromocionCooperativaList(promocionCooperativaListNew);
            List<Agencia> attachedAgenciaListNew = new ArrayList<Agencia>();
            for (Agencia agenciaListNewAgenciaToAttach : agenciaListNew) {
                agenciaListNewAgenciaToAttach = em.getReference(agenciaListNewAgenciaToAttach.getClass(), agenciaListNewAgenciaToAttach.getIdagencia());
                attachedAgenciaListNew.add(agenciaListNewAgenciaToAttach);
            }
            agenciaListNew = attachedAgenciaListNew;
            cooperativa.setAgenciaList(agenciaListNew);
            cooperativa = em.merge(cooperativa);
            for (PromocionCooperativa promocionCooperativaListNewPromocionCooperativa : promocionCooperativaListNew) {
                if (!promocionCooperativaListOld.contains(promocionCooperativaListNewPromocionCooperativa)) {
                    Cooperativa oldCooperativaOfPromocionCooperativaListNewPromocionCooperativa = promocionCooperativaListNewPromocionCooperativa.getCooperativa();
                    promocionCooperativaListNewPromocionCooperativa.setCooperativa(cooperativa);
                    promocionCooperativaListNewPromocionCooperativa = em.merge(promocionCooperativaListNewPromocionCooperativa);
                    if (oldCooperativaOfPromocionCooperativaListNewPromocionCooperativa != null && !oldCooperativaOfPromocionCooperativaListNewPromocionCooperativa.equals(cooperativa)) {
                        oldCooperativaOfPromocionCooperativaListNewPromocionCooperativa.getPromocionCooperativaList().remove(promocionCooperativaListNewPromocionCooperativa);
                        oldCooperativaOfPromocionCooperativaListNewPromocionCooperativa = em.merge(oldCooperativaOfPromocionCooperativaListNewPromocionCooperativa);
                    }
                }
            }
            for (Agencia agenciaListNewAgencia : agenciaListNew) {
                if (!agenciaListOld.contains(agenciaListNewAgencia)) {
                    Cooperativa oldCooperativaIdcooperativaOfAgenciaListNewAgencia = agenciaListNewAgencia.getCooperativaIdcooperativa();
                    agenciaListNewAgencia.setCooperativaIdcooperativa(cooperativa);
                    agenciaListNewAgencia = em.merge(agenciaListNewAgencia);
                    if (oldCooperativaIdcooperativaOfAgenciaListNewAgencia != null && !oldCooperativaIdcooperativaOfAgenciaListNewAgencia.equals(cooperativa)) {
                        oldCooperativaIdcooperativaOfAgenciaListNewAgencia.getAgenciaList().remove(agenciaListNewAgencia);
                        oldCooperativaIdcooperativaOfAgenciaListNewAgencia = em.merge(oldCooperativaIdcooperativaOfAgenciaListNewAgencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cooperativa.getIdcooperativa();
                if (findCooperativa(id) == null) {
                    throw new NonexistentEntityException("The cooperativa with id " + id + " no longer exists.");
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
            Cooperativa cooperativa;
            try {
                cooperativa = em.getReference(Cooperativa.class, id);
                cooperativa.getIdcooperativa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cooperativa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PromocionCooperativa> promocionCooperativaListOrphanCheck = cooperativa.getPromocionCooperativaList();
            for (PromocionCooperativa promocionCooperativaListOrphanCheckPromocionCooperativa : promocionCooperativaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cooperativa (" + cooperativa + ") cannot be destroyed since the PromocionCooperativa " + promocionCooperativaListOrphanCheckPromocionCooperativa + " in its promocionCooperativaList field has a non-nullable cooperativa field.");
            }
            List<Agencia> agenciaListOrphanCheck = cooperativa.getAgenciaList();
            for (Agencia agenciaListOrphanCheckAgencia : agenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cooperativa (" + cooperativa + ") cannot be destroyed since the Agencia " + agenciaListOrphanCheckAgencia + " in its agenciaList field has a non-nullable cooperativaIdcooperativa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cooperativa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cooperativa> findCooperativaEntities() {
        return findCooperativaEntities(true, -1, -1);
    }

    public List<Cooperativa> findCooperativaEntities(int maxResults, int firstResult) {
        return findCooperativaEntities(false, maxResults, firstResult);
    }

    private List<Cooperativa> findCooperativaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cooperativa.class));
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

    public Cooperativa findCooperativa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cooperativa.class, id);
        } finally {
            em.close();
        }
    }

    public int getCooperativaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cooperativa> rt = cq.from(Cooperativa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

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
public class AgenciaJpaController implements Serializable {

    public AgenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Agencia agencia) {
        if (agencia.getUsuarioList() == null) {
            agencia.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cooperativa cooperativaIdcooperativa = agencia.getCooperativaIdcooperativa();
            if (cooperativaIdcooperativa != null) {
                cooperativaIdcooperativa = em.getReference(cooperativaIdcooperativa.getClass(), cooperativaIdcooperativa.getIdcooperativa());
                agencia.setCooperativaIdcooperativa(cooperativaIdcooperativa);
            }
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : agencia.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getUser());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            agencia.setUsuarioList(attachedUsuarioList);
            em.persist(agencia);
            if (cooperativaIdcooperativa != null) {
                cooperativaIdcooperativa.getAgenciaList().add(agencia);
                cooperativaIdcooperativa = em.merge(cooperativaIdcooperativa);
            }
            for (Usuario usuarioListUsuario : agencia.getUsuarioList()) {
                Agencia oldAgenciaIdagenciaOfUsuarioListUsuario = usuarioListUsuario.getAgenciaIdagencia();
                usuarioListUsuario.setAgenciaIdagencia(agencia);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldAgenciaIdagenciaOfUsuarioListUsuario != null) {
                    oldAgenciaIdagenciaOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldAgenciaIdagenciaOfUsuarioListUsuario = em.merge(oldAgenciaIdagenciaOfUsuarioListUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Agencia agencia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agencia persistentAgencia = em.find(Agencia.class, agencia.getIdagencia());
            Cooperativa cooperativaIdcooperativaOld = persistentAgencia.getCooperativaIdcooperativa();
            Cooperativa cooperativaIdcooperativaNew = agencia.getCooperativaIdcooperativa();
            List<Usuario> usuarioListOld = persistentAgencia.getUsuarioList();
            List<Usuario> usuarioListNew = agencia.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its agenciaIdagencia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cooperativaIdcooperativaNew != null) {
                cooperativaIdcooperativaNew = em.getReference(cooperativaIdcooperativaNew.getClass(), cooperativaIdcooperativaNew.getIdcooperativa());
                agencia.setCooperativaIdcooperativa(cooperativaIdcooperativaNew);
            }
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getUser());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            agencia.setUsuarioList(usuarioListNew);
            agencia = em.merge(agencia);
            if (cooperativaIdcooperativaOld != null && !cooperativaIdcooperativaOld.equals(cooperativaIdcooperativaNew)) {
                cooperativaIdcooperativaOld.getAgenciaList().remove(agencia);
                cooperativaIdcooperativaOld = em.merge(cooperativaIdcooperativaOld);
            }
            if (cooperativaIdcooperativaNew != null && !cooperativaIdcooperativaNew.equals(cooperativaIdcooperativaOld)) {
                cooperativaIdcooperativaNew.getAgenciaList().add(agencia);
                cooperativaIdcooperativaNew = em.merge(cooperativaIdcooperativaNew);
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Agencia oldAgenciaIdagenciaOfUsuarioListNewUsuario = usuarioListNewUsuario.getAgenciaIdagencia();
                    usuarioListNewUsuario.setAgenciaIdagencia(agencia);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldAgenciaIdagenciaOfUsuarioListNewUsuario != null && !oldAgenciaIdagenciaOfUsuarioListNewUsuario.equals(agencia)) {
                        oldAgenciaIdagenciaOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldAgenciaIdagenciaOfUsuarioListNewUsuario = em.merge(oldAgenciaIdagenciaOfUsuarioListNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = agencia.getIdagencia();
                if (findAgencia(id) == null) {
                    throw new NonexistentEntityException("The agencia with id " + id + " no longer exists.");
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
            Agencia agencia;
            try {
                agencia = em.getReference(Agencia.class, id);
                agencia.getIdagencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The agencia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuario> usuarioListOrphanCheck = agencia.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Agencia (" + agencia + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable agenciaIdagencia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cooperativa cooperativaIdcooperativa = agencia.getCooperativaIdcooperativa();
            if (cooperativaIdcooperativa != null) {
                cooperativaIdcooperativa.getAgenciaList().remove(agencia);
                cooperativaIdcooperativa = em.merge(cooperativaIdcooperativa);
            }
            em.remove(agencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Agencia> findAgenciaEntities() {
        return findAgenciaEntities(true, -1, -1);
    }

    public List<Agencia> findAgenciaEntities(int maxResults, int firstResult) {
        return findAgenciaEntities(false, maxResults, firstResult);
    }

    private List<Agencia> findAgenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Agencia.class));
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

    public Agencia findAgencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Agencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getAgenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Agencia> rt = cq.from(Agencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

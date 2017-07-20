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
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agencia agenciaIdagencia = usuario.getAgenciaIdagencia();
            if (agenciaIdagencia != null) {
                agenciaIdagencia = em.getReference(agenciaIdagencia.getClass(), agenciaIdagencia.getIdagencia());
                usuario.setAgenciaIdagencia(agenciaIdagencia);
            }
            em.persist(usuario);
            if (agenciaIdagencia != null) {
                agenciaIdagencia.getUsuarioList().add(usuario);
                agenciaIdagencia = em.merge(agenciaIdagencia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getUser()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUser());
            Agencia agenciaIdagenciaOld = persistentUsuario.getAgenciaIdagencia();
            Agencia agenciaIdagenciaNew = usuario.getAgenciaIdagencia();
            if (agenciaIdagenciaNew != null) {
                agenciaIdagenciaNew = em.getReference(agenciaIdagenciaNew.getClass(), agenciaIdagenciaNew.getIdagencia());
                usuario.setAgenciaIdagencia(agenciaIdagenciaNew);
            }
            usuario = em.merge(usuario);
            if (agenciaIdagenciaOld != null && !agenciaIdagenciaOld.equals(agenciaIdagenciaNew)) {
                agenciaIdagenciaOld.getUsuarioList().remove(usuario);
                agenciaIdagenciaOld = em.merge(agenciaIdagenciaOld);
            }
            if (agenciaIdagenciaNew != null && !agenciaIdagenciaNew.equals(agenciaIdagenciaOld)) {
                agenciaIdagenciaNew.getUsuarioList().add(usuario);
                agenciaIdagenciaNew = em.merge(agenciaIdagenciaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getUser();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            Agencia agenciaIdagencia = usuario.getAgenciaIdagencia();
            if (agenciaIdagencia != null) {
                agenciaIdagencia.getUsuarioList().remove(usuario);
                agenciaIdagencia = em.merge(agenciaIdagencia);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

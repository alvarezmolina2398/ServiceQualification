/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualification.controlador;

import com.opensymphony.xwork2.ActionSupport;
import com.qualification.modelo.Departamento;
import com.qualification.modelo.Interrogante;
import com.qualification.modelo.ListQuestion;
import com.qualification.modelo.Parametro;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Desarrollo
 */
public class principal extends ActionSupport{
    //declaracion de los cambios
    private ArrayList<Departamento> Listdepartamentos;
    private ArrayList<ListQuestion> ListQuestions;
    private int totalpreguntas;
    private String Error;
    //metodoss para establecer y obtener
    public ArrayList<Departamento> getListdepartamentos(){
        return Listdepartamentos;
    }
   
    public void setListdepartamentos(ArrayList<Departamento> Listdepartamentos) {
        this.Listdepartamentos = Listdepartamentos;
    }

    public ArrayList<ListQuestion> getListQuestions() {
        return ListQuestions;
    }

    public void setListQuestions(ArrayList<ListQuestion> ListQuestions) {
        this.ListQuestions = ListQuestions;
    }

    public String getError() {
        return Error;
    }

    public void setError(String Error) {
        this.Error = Error;
    }

    public int getTotalpreguntas() {
        return totalpreguntas;
    }

    public void setTotalpreguntas(int totalpreguntas) {
        this.totalpreguntas = totalpreguntas;
    }
    
    
    /*
    Descripcion: consulta para obtener los departamentos a calificar
    Autor: Jorge Manuel Alvarez Molina
    Fecha: 20 de Julio de 2017
     */
    private void getDepartamentos() {
        try{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ServiceQualificationPU");
            EntityManager em = emf.createEntityManager();


            String jpql ="SELECT d FROM Departamento d "
                    + "WHERE d.estado = 'a'";

            Query query = em.createQuery(jpql);
            List<Departamento>  listDepartamentos = query.getResultList();
            ArrayList<Departamento> arrayListDepartamentos = new ArrayList<>();
            for(Departamento d: listDepartamentos){
                arrayListDepartamentos.add(d);
            }
            this.Listdepartamentos = arrayListDepartamentos;
            em.close();
            emf.close();
        }
        catch(Exception e){
            System.out.println("ERROR: "+e);
            this.Error = "ERROR: "+e;
        }
    }
    
     /*
    Descripcion: consulta para obtener los parametros
    Autor: Jorge Manuel Alvarez Molina
    Fecha: 21 de Julio de 2017
     */
    
    private ArrayList<Parametro> getParametros(int pregunta){
        ArrayList<Parametro> arrayListParametros = new ArrayList<>();
        try{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ServiceQualificationPU");
            EntityManager em = emf.createEntityManager();

            String jpql ="SELECT p FROM ParametroInterrogante pi "
                    + "JOIN pi.interrogante i "
                    + "JOIN pi.parametro p  "
                    + "WHERE p.estado='a' AND i.estado='a' "
                    + "AND i.idinterrogante = :pregunta";

            Query query = em.createQuery(jpql);
            query.setParameter("pregunta", pregunta);
            List<Parametro> list = query.getResultList();
            for(Parametro p : list){
                arrayListParametros.add(p);
            }

            em.close();
            emf.close();
        }catch(Exception e){
            System.out.println("ERROR: "+e);
            this.Error = "ERROR: "+e;
        }
        return arrayListParametros;
    }
    
    /*
    Descripcion: Metodos para obtener las interrogantes
    Autor: Jorge Manuel Alvarez Molina
    Fecha: 21 de Julio de 2017
     */
    
    private void getinterrogantes(){
        try{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ServiceQualificationPU");
            EntityManager em = emf.createEntityManager();

            String jpql = "SELECT i FROM Interrogante i "
                    + "WHERE i.estado='a' ";

            Query query = em.createQuery(jpql);
            List<Interrogante> listInterrogantes = query.getResultList();

            ArrayList<ListQuestion> arrayListQuestions = new ArrayList<>();
            for (Interrogante i : listInterrogantes){
                ListQuestion lq = new ListQuestion();
                lq.setIdinterrogante(i.getIdinterrogante());
                lq.setQuestion(i.getDescripcion());
                lq.setListParametros(getParametros(i.getIdinterrogante()));
                arrayListQuestions.add(lq);
            }

            this.ListQuestions = arrayListQuestions;
            em.close();
            emf.close();
        }catch(Exception e){
            System.out.println("ERROR: "+e);
            this.Error = "ERROR: "+e;
        }    
        
    };
    
    /*
    Descripcion:    METODO PARA OBTENER EL TOTAL DE PREGUNTAS
    AUTOR: JORGE MANUEL ALVAREZ MOLINA
    24 DE JULIO DE 2017
    */
    private void getTotalPreguntas(){
        try{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ServiceQualificationPU");
            EntityManager em = emf.createEntityManager();
            
            String jpql = "SELECT COUNT(i) FROM Interrogante i WHERE i.estado='a' ";
            
            
            Query query = em.createQuery(jpql);
            this.totalpreguntas = Integer.parseInt(query.getSingleResult().toString());
            //agregamos la pregunta de seleccion de departamento
            this.totalpreguntas +=1;
            em.close();
            emf.close();
        }catch(Exception e ){
            System.out.println("ERROR: "+e);
            this.Error = "ERROR: "+e;
        }    
    }
    
    @Override
    public String execute() throws Exception {
        getTotalPreguntas();
        getinterrogantes();
        getDepartamentos();
        return SUCCESS;
    }
    
    
}

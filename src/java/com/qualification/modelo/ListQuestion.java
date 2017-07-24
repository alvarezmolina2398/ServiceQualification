/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualification.modelo;

import java.util.ArrayList;

/**
 *
 * @author Desarrollo
 */
public class ListQuestion  {
    //declaracion de los campos
    private int idinterrogante;
    private String question;
    private ArrayList<Parametro> listParametros;

    public int getIdinterrogante() {
        return idinterrogante;
    }

    public void setIdinterrogante(int idinterrogante) {
        this.idinterrogante = idinterrogante;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String quaestion) {
        this.question = quaestion;
    }

    public ArrayList<Parametro> getListParametros() {
        return listParametros;
    }

    public void setListParametros(ArrayList<Parametro> listParametros) {
        this.listParametros = listParametros;
    }
    
    
}

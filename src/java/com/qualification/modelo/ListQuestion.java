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
public class ListQuestion {
    //declaracion de los campos
    private int idinterrogante;
    private String question;
    private ArrayList<Parametro> ListParametros;

    public int getIdinterrogante() {
        return idinterrogante;
    }

    public void setIdinterrogante(int idinterrogante) {
        this.idinterrogante = idinterrogante;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<Parametro> getListParametros() {
        return ListParametros;
    }

    public void setListParametros(ArrayList<Parametro> ListParametros) {
        this.ListParametros = ListParametros;
    }

    
    
    
}

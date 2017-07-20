package com.qualification.modelo;

import com.qualification.modelo.Parametro;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-20T12:22:42")
@StaticMetamodel(Interrogante.class)
public class Interrogante_ { 

    public static volatile SingularAttribute<Interrogante, String> descripcion;
    public static volatile SingularAttribute<Interrogante, Character> estado;
    public static volatile ListAttribute<Interrogante, Parametro> parametroList;
    public static volatile SingularAttribute<Interrogante, Integer> idinterrogante;

}
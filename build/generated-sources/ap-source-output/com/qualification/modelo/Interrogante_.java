package com.qualification.modelo;

import com.qualification.modelo.ParametroInterrogante;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-24T15:28:06")
@StaticMetamodel(Interrogante.class)
public class Interrogante_ { 

    public static volatile SingularAttribute<Interrogante, String> descripcion;
    public static volatile SingularAttribute<Interrogante, Character> estado;
    public static volatile ListAttribute<Interrogante, ParametroInterrogante> parametroInterroganteList;
    public static volatile SingularAttribute<Interrogante, Integer> idinterrogante;

}
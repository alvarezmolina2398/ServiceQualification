package com.qualification.modelo;

import com.qualification.modelo.Agencia;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-20T12:22:42")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, Date> fecha;
    public static volatile SingularAttribute<Usuario, Character> tipo;
    public static volatile SingularAttribute<Usuario, Character> estado;
    public static volatile SingularAttribute<Usuario, String> pass;
    public static volatile SingularAttribute<Usuario, Agencia> agenciaIdagencia;
    public static volatile SingularAttribute<Usuario, String> user;
    public static volatile SingularAttribute<Usuario, String> nombre;

}
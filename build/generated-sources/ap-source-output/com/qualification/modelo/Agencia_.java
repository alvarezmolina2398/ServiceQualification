package com.qualification.modelo;

import com.qualification.modelo.Cooperativa;
import com.qualification.modelo.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-24T15:28:06")
@StaticMetamodel(Agencia.class)
public class Agencia_ { 

    public static volatile SingularAttribute<Agencia, Character> estado;
    public static volatile ListAttribute<Agencia, Usuario> usuarioList;
    public static volatile SingularAttribute<Agencia, Cooperativa> cooperativaIdcooperativa;
    public static volatile SingularAttribute<Agencia, Integer> idagencia;
    public static volatile SingularAttribute<Agencia, String> nombreAgencia;

}
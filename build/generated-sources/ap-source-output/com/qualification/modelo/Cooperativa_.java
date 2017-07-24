package com.qualification.modelo;

import com.qualification.modelo.Agencia;
import com.qualification.modelo.PromocionCooperativa;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-24T15:28:06")
@StaticMetamodel(Cooperativa.class)
public class Cooperativa_ { 

    public static volatile SingularAttribute<Cooperativa, String> nombreCooperativa;
    public static volatile SingularAttribute<Cooperativa, Character> estado;
    public static volatile ListAttribute<Cooperativa, PromocionCooperativa> promocionCooperativaList;
    public static volatile SingularAttribute<Cooperativa, Integer> idcooperativa;
    public static volatile ListAttribute<Cooperativa, Agencia> agenciaList;

}
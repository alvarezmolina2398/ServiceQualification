package com.qualification.modelo;

import com.qualification.modelo.Cooperativa;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-20T12:22:42")
@StaticMetamodel(Promocion.class)
public class Promocion_ { 

    public static volatile SingularAttribute<Promocion, Character> estado;
    public static volatile ListAttribute<Promocion, Cooperativa> cooperativaList;
    public static volatile SingularAttribute<Promocion, Date> fechaInicio;
    public static volatile SingularAttribute<Promocion, Integer> idpromocion;
    public static volatile SingularAttribute<Promocion, Date> fechaFin;
    public static volatile SingularAttribute<Promocion, String> nombrePromocion;

}
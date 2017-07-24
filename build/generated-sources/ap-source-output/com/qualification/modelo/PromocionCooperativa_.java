package com.qualification.modelo;

import com.qualification.modelo.Cooperativa;
import com.qualification.modelo.Promocion;
import com.qualification.modelo.PromocionCooperativaPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-24T15:28:06")
@StaticMetamodel(PromocionCooperativa.class)
public class PromocionCooperativa_ { 

    public static volatile SingularAttribute<PromocionCooperativa, Date> fecha;
    public static volatile SingularAttribute<PromocionCooperativa, Promocion> promocion;
    public static volatile SingularAttribute<PromocionCooperativa, Cooperativa> cooperativa;
    public static volatile SingularAttribute<PromocionCooperativa, PromocionCooperativaPK> promocionCooperativaPK;

}
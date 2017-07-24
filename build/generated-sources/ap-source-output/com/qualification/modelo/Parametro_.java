package com.qualification.modelo;

import com.qualification.modelo.ParametroInterrogante;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-24T15:28:06")
@StaticMetamodel(Parametro.class)
public class Parametro_ { 

    public static volatile SingularAttribute<Parametro, String> descripcion;
    public static volatile SingularAttribute<Parametro, BigDecimal> rangoMinimo;
    public static volatile SingularAttribute<Parametro, String> idparametro;
    public static volatile SingularAttribute<Parametro, Character> estado;
    public static volatile ListAttribute<Parametro, ParametroInterrogante> parametroInterroganteList;
    public static volatile SingularAttribute<Parametro, String> dirImg;
    public static volatile SingularAttribute<Parametro, BigDecimal> rangoMaximo;

}
package com.qualification.modelo;

import com.qualification.modelo.Interrogante;
import com.qualification.modelo.Parametro;
import com.qualification.modelo.ParametroInterrogantePK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-24T15:28:06")
@StaticMetamodel(ParametroInterrogante.class)
public class ParametroInterrogante_ { 

    public static volatile SingularAttribute<ParametroInterrogante, Date> fecha;
    public static volatile SingularAttribute<ParametroInterrogante, Interrogante> interrogante;
    public static volatile SingularAttribute<ParametroInterrogante, Parametro> parametro;
    public static volatile SingularAttribute<ParametroInterrogante, ParametroInterrogantePK> parametroInterrogantePK;

}
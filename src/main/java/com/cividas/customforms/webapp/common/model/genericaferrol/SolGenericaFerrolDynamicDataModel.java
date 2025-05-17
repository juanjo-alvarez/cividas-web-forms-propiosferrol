package com.cividas.customforms.webapp.common.model.genericaferrol;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class SolGenericaFerrolDynamicDataModel implements Serializable {

    private static final long serialVersionUID = -4073174398473406899L;

    private String expongo;
    private String solicito;
    private Number actuaenrepresentacion;


    public String getExpongo() {
        return expongo;
    }

    public void setExpongo(String expongo) {
        this.expongo = expongo;
    }

    public String getSolicito() {
        return solicito;
    }

    public void setSolicito(String solicito) {
        this.solicito = solicito;
    }

    public Number getActuaenrepresentacion() {
        return actuaenrepresentacion;
    }

    public void setActuaenrepresentacion(Number actuaenrepresentacion) {
        this.actuaenrepresentacion = actuaenrepresentacion;
    }
}

package com.cividas.customforms.webapp.common.model.subsanaciondocferrol;

import java.io.Serializable;

public class SubsanacionDocFerrolDynamicDataModel implements Serializable {

    private static final long serialVersionUID = -571877189666706454L;

    private String numexpediente;
    private Number actuaenrepresentacion;

    public String getNumexpediente() {
        return numexpediente;
    }

    public void setNumexpediente(String numexpediente) {
        this.numexpediente = numexpediente;
    }

    public Number getActuaenrepresentacion() {
        return actuaenrepresentacion;
    }

    public void setActuaenrepresentacion(Number actuaenrepresentacion) {
        this.actuaenrepresentacion = actuaenrepresentacion;
    }
}

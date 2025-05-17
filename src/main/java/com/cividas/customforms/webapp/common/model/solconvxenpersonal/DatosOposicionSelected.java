package com.cividas.customforms.webapp.common.model.solconvxenpersonal;

import java.io.Serializable;

public class DatosOposicionSelected implements Serializable {
    private static final long serialVersionUID = -5970036785880033250L;

    private String datosoposicion;
    private int datosoposicioncode;

    public DatosOposicionSelected(String datosoposicion, int datosoposicioncode) {
        this.datosoposicion = datosoposicion;
        this.datosoposicioncode = datosoposicioncode;
    }
    public String getDatosoposicion() { return datosoposicion;  }

    public void setDatosoposicion(String datosoposicion) { this.datosoposicion = datosoposicion; }

    public int getDatosoposicioncode() { return datosoposicioncode; }

    public void setDatosoposicioncode(int datosoposicioncode) { this.datosoposicioncode = datosoposicioncode; }
}

package com.cividas.customforms.webapp.controller.solconvxenpersonal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DatosOposicion {
    private String datosoposicioncode;
    private String datosoposicion;

    @JsonIgnore
    private boolean checked;

    public String getDatosoposicioncode() {
        return datosoposicioncode;
    }

    public void setDatosoposicioncode(String datosoposicioncode) {
        this.datosoposicioncode = datosoposicioncode;
    }

    public String getDatosoposicion() {
        return datosoposicion;
    }

    public void setDatosoposicion(String datosoposicion) {
        this.datosoposicion = datosoposicion;
    }

    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}

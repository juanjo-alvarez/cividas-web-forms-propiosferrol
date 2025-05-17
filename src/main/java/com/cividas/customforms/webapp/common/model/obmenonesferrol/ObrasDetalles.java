package com.cividas.customforms.webapp.common.model.obmenonesferrol;

import java.io.Serializable;

public class ObrasDetalles implements Serializable {
    private static final long serialVersionUID = 1133666335817080571L;
    private String unidad;
    private String uso;
    private String medicion;
    private String referenciaorzamento;

    public ObrasDetalles() {
        super();
    }

    public ObrasDetalles(String unidad, String uso, String medicion, String referenciaorzamento) {
        super();
        this.unidad = unidad;
        this.uso = uso;
        this.medicion = medicion;
        this.referenciaorzamento = referenciaorzamento;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getMedicion() {
        return medicion;
    }

    public void setMedicion(String medicion) {
        this.medicion = medicion;
    }

    public String getReferenciaorzamento() {
        return referenciaorzamento;
    }

    public void setReferenciaorzamento(String referenciaorzamento) {
        this.referenciaorzamento = referenciaorzamento;
    }
}

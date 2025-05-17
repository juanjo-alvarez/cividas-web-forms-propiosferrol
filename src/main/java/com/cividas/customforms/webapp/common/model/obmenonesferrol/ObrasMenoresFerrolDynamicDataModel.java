package com.cividas.customforms.webapp.common.model.obmenonesferrol;

import com.cividas.customforms.webapp.common.model.base.JsonMultipleData;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class ObrasMenoresFerrolDynamicDataModel implements Serializable {

    private static final long serialVersionUID = -6976481066476566203L;
    private String situacion;
    private String referencia;
    private String orzamento;
    private String inicio;
    private String plazo;
    private JsonMultipleData jsonMultipleData;
    private Number actuaenrepresentacion;

    public Number getActuaenrepresentacion() {
        return actuaenrepresentacion;
    }

    public void setActuaenrepresentacion(Number actuaenrepresentacion) {
        this.actuaenrepresentacion = actuaenrepresentacion;
    }

    public ObrasMenoresFerrolDynamicDataModel() {
        super();
    }

    public JsonMultipleData getJsonMultipleData() {
        return jsonMultipleData;
    }

    public void setJsonMultipleData(JsonMultipleData jsonMultipleData) {
        this.jsonMultipleData = jsonMultipleData;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getOrzamento() {
        return orzamento;
    }

    public void setOrzamento(String orzamento) {
        this.orzamento = orzamento;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }
}

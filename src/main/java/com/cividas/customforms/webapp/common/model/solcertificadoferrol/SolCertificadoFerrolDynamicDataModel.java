package com.cividas.customforms.webapp.common.model.solcertificadoferrol;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class SolCertificadoFerrolDynamicDataModel implements Serializable {

    private static final long serialVersionUID = -8928536200573883921L;
    private String expongo;
    private int bienes;
    private int vehiculos;
    private int poseevehiculos;
    private int poseebienes;
    private int negativobienes;
    private int negativovehiculos;
    private Number actuaenrepresentacion;

    public Number getActuaenrepresentacion() {
        return actuaenrepresentacion;
    }

    public void setActuaenrepresentacion(Number actuaenrepresentacion) {
        this.actuaenrepresentacion = actuaenrepresentacion;
    }

    public String getExpongo() {
        return expongo;
    }

    public void setExpongo(String expongo) {
        this.expongo = expongo;
    }

    public int getBienes() {
        return bienes;
    }

    public void setBienes(int bienes) {
        this.bienes = bienes;
    }

    public int getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(int vehiculos) {
        this.vehiculos = vehiculos;
    }

    public int getPoseevehiculos() {
        return poseevehiculos;
    }

    public void setPoseevehiculos(int poseevehiculos) {
        this.poseevehiculos = poseevehiculos;
    }

    public int getPoseebienes() {
        return poseebienes;
    }

    public void setPoseebienes(int poseebienes) {
        this.poseebienes = poseebienes;
    }

    public int getNegativobienes() {
        return negativobienes;
    }

    public void setNegativobienes(int negativobienes) {
        this.negativobienes = negativobienes;
    }

    public int getNegativovehiculos() {
        return negativovehiculos;
    }

    public void setNegativovehiculos(int negativovehiculos) {
        this.negativovehiculos = negativovehiculos;
    }
}

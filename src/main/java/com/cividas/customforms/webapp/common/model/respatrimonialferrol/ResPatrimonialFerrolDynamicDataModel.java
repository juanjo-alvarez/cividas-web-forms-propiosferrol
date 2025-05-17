package com.cividas.customforms.webapp.common.model.respatrimonialferrol;

import java.io.Serializable;

public class ResPatrimonialFerrolDynamicDataModel implements Serializable {
    private static final long serialVersionUID = -5133349652717760096L;

    private int caida;
    private int danosvehiculo;
    private int outros;
    private String lugar;
    private String fecha;
    private String hechos;
    private String valoraciondanos;
    private String pruebas;
    private Number actuaenrepresentacion;

    public Number getActuaenrepresentacion() {
        return actuaenrepresentacion;
    }

    public void setActuaenrepresentacion(Number actuaenrepresentacion) {
        this.actuaenrepresentacion = actuaenrepresentacion;
    }

    public int getCaida() {
        return caida;
    }

    public void setCaida(int caida) {
        this.caida = caida;
    }

    public int getDanosvehiculo() {
        return danosvehiculo;
    }

    public void setDanosvehiculo(int danosvehiculo) {
        this.danosvehiculo = danosvehiculo;
    }

    public int getOutros() {
        return outros;
    }

    public void setOutros(int outros) {
        this.outros = outros;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHechos() {
        return hechos;
    }

    public void setHechos(String hechos) {
        this.hechos = hechos;
    }

    public String getValoraciondanos() {
        return valoraciondanos;
    }

    public void setValoraciondanos(String valoraciondanos) {
        this.valoraciondanos = valoraciondanos;
    }

    public String getPruebas() {
        return pruebas;
    }

    public void setPruebas(String pruebas) {
        this.pruebas = pruebas;
    }
}

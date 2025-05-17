package com.cividas.customforms.webapp.common.model.usoviaferrol;

import java.io.Serializable;

public class UsoViaFerrolDynamicDataModel implements Serializable {
    private static final long serialVersionUID = 6632538204125952554L;

    private int barrera;
    private String barreralabel;
    private int cortecirculacion;
    private int accesovehiculo;
    private int accesovehiculogrande;
    private int otro;
    private String otrolabel;
    private String fecha;
    private String horario;
    private String lugar;
    private String clase;
    private String motivo;
    private Number actuaenrepresentacion;

    public int getBarrera() {
        return barrera;
    }

    public void setBarrera(int barrera) {
        this.barrera = barrera;
    }

    public String getBarreralabel() {
        return barreralabel;
    }

    public void setBarreralabel(String barreralabel) {
        this.barreralabel = barreralabel;
    }

    public int getCortecirculacion() {
        return cortecirculacion;
    }

    public void setCortecirculacion(int cortecirculacion) {
        this.cortecirculacion = cortecirculacion;
    }

    public int getAccesovehiculo() {
        return accesovehiculo;
    }

    public void setAccesovehiculo(int accesovehiculo) {
        this.accesovehiculo = accesovehiculo;
    }

    public int getAccesovehiculogrande() {
        return accesovehiculogrande;
    }

    public void setAccesovehiculogrande(int accesovehiculogrande) {
        this.accesovehiculogrande = accesovehiculogrande;
    }

    public int getOtro() {
        return otro;
    }

    public void setOtro(int otro) {
        this.otro = otro;
    }

    public String getOtrolabel() {
        return otrolabel;
    }

    public void setOtrolabel(String otrolabel) {
        this.otrolabel = otrolabel;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Number getActuaenrepresentacion() {
        return actuaenrepresentacion;
    }

    public void setActuaenrepresentacion(Number actuaenrepresentacion) {
        this.actuaenrepresentacion = actuaenrepresentacion;
    }
}

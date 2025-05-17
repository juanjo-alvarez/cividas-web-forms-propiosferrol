package com.cividas.customforms.webapp.common.model.certempadronamientoferrol;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class CertEmpadronamientoFerrolDynamicDataModel implements Serializable {

    private static final long serialVersionUID = 8284373885090153860L;

    private int obtencionayuda;
    private String obtencionayudalabel;
    private int matrimonio;
    private int impuesto;
    private int otrosfines;
    private String otrosfineslabel;
    private int defuncion;
    private int historicodefuncion;
    private String historicodefuncionlabel;
    private int historicoconvivencia;
    private String historicoconvivencialabel;
    private int otros;
    private String otroslabel;
    private Number actuaenrepresentacion;


    public String getObtencionayudalabel() {
        return obtencionayudalabel;
    }

    public void setObtencionayudalabel(String obtencionayudalabel) {
        this.obtencionayudalabel = obtencionayudalabel;
    }

    public String getOtrosfineslabel() {
        return otrosfineslabel;
    }

    public void setOtrosfineslabel(String otrosfineslabel) {
        this.otrosfineslabel = otrosfineslabel;
    }

    public String getHistoricodefuncionlabel() {
        return historicodefuncionlabel;
    }

    public void setHistoricodefuncionlabel(String historicodefuncionlabel) {
        this.historicodefuncionlabel = historicodefuncionlabel;
    }

    public String getHistoricoconvivencialabel() {
        return historicoconvivencialabel;
    }

    public void setHistoricoconvivencialabel(String historicoconvivencialabel) {
        this.historicoconvivencialabel = historicoconvivencialabel;
    }

    public String getOtroslabel() {
        return otroslabel;
    }

    public void setOtroslabel(String otroslabel) {
        this.otroslabel = otroslabel;
    }

    public int getObtencionayuda() {
        return obtencionayuda;
    }

    public void setObtencionayuda(int obtencionayuda) {
        this.obtencionayuda = obtencionayuda;
    }

    public int getMatrimonio() {
        return matrimonio;
    }

    public void setMatrimonio(int matrimonio) {
        this.matrimonio = matrimonio;
    }

    public int getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(int impuesto) {
        this.impuesto = impuesto;
    }

    public int getOtrosfines() {
        return otrosfines;
    }

    public void setOtrosfines(int otrosfines) {
        this.otrosfines = otrosfines;
    }

    public int getDefuncion() {
        return defuncion;
    }

    public void setDefuncion(int defuncion) {
        this.defuncion = defuncion;
    }

    public int getHistoricodefuncion() {
        return historicodefuncion;
    }

    public void setHistoricodefuncion(int historicodefuncion) {
        this.historicodefuncion = historicodefuncion;
    }

    public int getHistoricoconvivencia() {
        return historicoconvivencia;
    }

    public void setHistoricoconvivencia(int historicoconvivencia) {
        this.historicoconvivencia = historicoconvivencia;
    }

    public int getOtros() {
        return otros;
    }

    public void setOtros(int otros) {
        this.otros = otros;
    }

    public Number getActuaenrepresentacion() {
        return actuaenrepresentacion;
    }

    public void setActuaenrepresentacion(Number actuaenrepresentacion) {
        this.actuaenrepresentacion = actuaenrepresentacion;
    }
}

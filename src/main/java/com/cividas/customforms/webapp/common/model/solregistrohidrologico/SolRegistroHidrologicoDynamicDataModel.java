package com.cividas.customforms.webapp.common.model.solregistrohidrologico;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class SolRegistroHidrologicoDynamicDataModel implements Serializable {
    private static final long serialVersionUID = -3764219077124079404L;

    private String anhohidrologico;
    private String cif;
    private String nombre;
    private String apellidos;
    private String email;
    private String movil;
    private String isubsanacion;
    private String numregistro;
    private String year;
    private String superficie;
    private String expongo;
    private String solicito;

    public String getAnhohidrologico() {
        return anhohidrologico;
    }

    public void setAnhohidrologico(String anhohidrologico) {
        this.anhohidrologico = anhohidrologico;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getIsubsanacion() {
        return isubsanacion;
    }

    public void setIsubsanacion(String isubsanacion) {
        this.isubsanacion = isubsanacion;
    }

    public String getNumregistro() {
        return numregistro;
    }

    public void setNumregistro(String numregistro) {
        this.numregistro = numregistro;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSuperficie() {
        return superficie;
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
    }
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
}

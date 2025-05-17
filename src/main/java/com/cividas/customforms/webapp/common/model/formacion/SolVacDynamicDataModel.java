package com.cividas.customforms.webapp.common.model.formacion;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class SolVacDynamicDataModel implements Serializable {

    private static final long serialVersionUID = -4073174398473406899L;

    private Number nrodiassolicitados;
    private Object coddestino;
    private String motivovacaciones;
    private String vacacionessolicitadas;
    
	public Number getNrodiassolicitados() {
		return nrodiassolicitados;
	}
	public void setNrodiassolicitados(Number nrodiassolicitados) {
		this.nrodiassolicitados = nrodiassolicitados;
	}
	public Object getCoddestino() {
		return coddestino;
	}
	public void setCoddestino(Object coddestino) {
		this.coddestino = coddestino;
	}
	public String getMotivovacaciones() {
		return motivovacaciones;
	}
	public void setMotivovacaciones(String motivovacaciones) {
		this.motivovacaciones = motivovacaciones;
	}
	public String getVacacionessolicitadas() {
		return vacacionessolicitadas;
	}
	public void setVacacionessolicitadas(String vacacionessolicitadas) {
		this.vacacionessolicitadas = vacacionessolicitadas;
	}
}

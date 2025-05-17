package com.cividas.customforms.webapp.common.model.inc;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IncidenciasDynamicDataModel implements Serializable {

	private static final long serialVersionUID = 700040052695090153L;
	
	private String descincidencia;
	private Float latitud;
	private Float longitud;
	public String getDescincidencia() {
		return descincidencia;
	}
	public void setDescincidencia(String descincidencia) {
		this.descincidencia = descincidencia;
	}
	public Float getLatitud() {
		return latitud;
	}
	public void setLatitud(Float latitud) {
		this.latitud = latitud;
	}
	public Float getLongitud() {
		return longitud;
	}
	public void setLongitud(Float longitud) {
		this.longitud = longitud;
	}
	
	
	

	
	
	
}

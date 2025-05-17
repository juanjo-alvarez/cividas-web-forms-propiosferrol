package com.cividas.customforms.webapp.common.model.generica;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class SolGenericaRegantesDynamicDataModel implements Serializable {

	private static final long serialVersionUID = -3032438389083041528L;
	
	private String expongo;
	private String solicito;
	private String comunidad;
	
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
	public String getComunidad() {
		return comunidad;
	}
	public void setComunidad(String comunidad) {
		this.comunidad = comunidad;
	}

}

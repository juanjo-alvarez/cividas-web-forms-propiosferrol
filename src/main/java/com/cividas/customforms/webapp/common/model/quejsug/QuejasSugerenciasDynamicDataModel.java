package com.cividas.customforms.webapp.common.model.quejsug;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QuejasSugerenciasDynamicDataModel implements Serializable {

	private static final long serialVersionUID = 700040052695090153L;
	
	private String expongo;
	private String solicito;
	private Number idtipoquejsug;
	
	
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
	public Number getIdtipoquejsug() {
		return idtipoquejsug;
	}
	public void setIdtipoquejsug(Number idtipoquejsug) {
		this.idtipoquejsug = idtipoquejsug;
	}
	
	
	
	
}

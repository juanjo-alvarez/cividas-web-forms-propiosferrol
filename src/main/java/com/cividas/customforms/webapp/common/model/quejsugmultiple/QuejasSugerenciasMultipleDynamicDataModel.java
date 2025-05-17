package com.cividas.customforms.webapp.common.model.quejsugmultiple;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QuejasSugerenciasMultipleDynamicDataModel implements Serializable {

	private static final long serialVersionUID = 700040052695090153L;
	
	private String expongo;
	private String solicito;
	private Number idtipoquejsug;
	private Number numregistros;

	public Number getNumregistros() {
		return numregistros;
	}
	public void setNumregistros(Number numregistros) {
		this.numregistros = numregistros;
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
	public Number getIdtipoquejsug() {
		return idtipoquejsug;
	}
	public void setIdtipoquejsug(Number idtipoquejsug) {
		this.idtipoquejsug = idtipoquejsug;
	}
	
	
	
	
}

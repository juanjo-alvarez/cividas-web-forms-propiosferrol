package com.cividas.customforms.webapp.common.model.generica;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class SolGenericaDynamicDataModel implements Serializable {

	private static final long serialVersionUID = 700048852695090153L;
	
	private String expongo;
	private String solicito;
	
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

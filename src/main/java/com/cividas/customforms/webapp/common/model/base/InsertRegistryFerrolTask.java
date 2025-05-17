package com.cividas.customforms.webapp.common.model.base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class InsertRegistryFerrolTask extends InsertRegistryFerrol implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private Number idtasktype;
    
    @XmlElement
    private Number idprocedure;


	public Number getIdtasktype() {
		return idtasktype;
	}

	public void setIdtasktype(Number idtasktype) {
		this.idtasktype = idtasktype;
	}

	public Number getIdprocedure() {
		return idprocedure;
	}

	public void setIdprocedure(Number idprocedure) {
		this.idprocedure = idprocedure;
	}
	
}
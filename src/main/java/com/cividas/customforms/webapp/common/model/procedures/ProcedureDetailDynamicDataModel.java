package com.cividas.customforms.webapp.common.model.procedures;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
* [CE-1024] IFrame base para el detalle de los expedientes
* 
* @author juanjo.alvarez
* @since 28/11/2019
*/

@XmlRootElement
public class ProcedureDetailDynamicDataModel implements Serializable {

	private static final long serialVersionUID = 700040052695090153L;
	
	private Number idProcedure;
	private String procedurecode;
	
	public Number getIdProcedure() {
		return idProcedure;
	}
	
	public void setIdProcedure(Number idProcedure) {
		this.idProcedure = idProcedure;
	}
	
	public String getProcedurecode() {
		return procedurecode;
	}

	public void setProcedurecode(String procedurecode) {
		this.procedurecode = procedurecode;
	}
	
	
	
}

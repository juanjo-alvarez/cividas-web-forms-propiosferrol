package com.cividas.customforms.webapp.common.model.procedures;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cividas.customforms.webapp.common.model.base.InsertProcedure;

/**
* [CE-1024] IFrame base para el detalle de los expedientes
* 
* @author juanjo.alvarez
* @since 28/11/2019
*/


@XmlRootElement
@ManagedBean(name="procedureDetailModel")
@ViewScoped
public class ProcedureDetailModel extends InsertProcedure {

	private static final long serialVersionUID = -5899222873458684038L;

	@XmlElement
	ProcedureDetailDynamicDataModel procedureDetail;
	

	public ProcedureDetailDynamicDataModel getRegistrytypedata() {
		return this.procedureDetail;
	}
	
	public void setRegistrytypedata(ProcedureDetailDynamicDataModel procedureDetail) {
		this.procedureDetail = procedureDetail;
	}
	
}

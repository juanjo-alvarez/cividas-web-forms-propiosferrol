package com.cividas.customforms.webapp.common.model.procedures;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cividas.customforms.webapp.common.model.base.InsertRegistryTask;

@XmlRootElement
@ManagedBean(name="procedureDocContributionDataModel")
@ViewScoped
public class ProcedureDocContributionDataModel extends InsertRegistryTask {

	private static final long serialVersionUID = -5899222873458684038L;

	@XmlElement
	ProcedureDocContributionDynamicDataModel registrytypedata;
	

	public ProcedureDocContributionDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}
	
	public void setRegistrytypedata(ProcedureDocContributionDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}
	
}

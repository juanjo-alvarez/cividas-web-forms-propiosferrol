package com.cividas.customforms.webapp.common.model.solexc;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name="solicitudExcelenciaModel")
@ViewScoped
public class SolExcModel extends InsertRegistry {

	private static final long serialVersionUID = 4109585718572663750L;

	@XmlElement
	SolExcDynamicDataModel registrytypedata;


	public SolExcDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}

	public void setRegistrytypedata(SolExcDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}

}

package com.cividas.customforms.webapp.common.model.generica;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name="solicitudGenericaRegantesModel")
@ViewScoped
public class SolGenericaRegantesModel extends InsertRegistry {

	private static final long serialVersionUID = -6013560992923893507L;
	
	@XmlElement
	SolGenericaRegantesDynamicDataModel registrytypedata;


	public SolGenericaRegantesDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}

	public void setRegistrytypedata(SolGenericaRegantesDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}

}

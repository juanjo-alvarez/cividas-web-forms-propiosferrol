package com.cividas.customforms.webapp.common.model.generica;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name="solicitudGenericaModel")
@ViewScoped
public class SolGenericaModel extends InsertRegistry {

	private static final long serialVersionUID = 4109585718572663750L;

	@XmlElement
	SolGenericaDynamicDataModel registrytypedata;


	public SolGenericaDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}

	public void setRegistrytypedata(SolGenericaDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}

}

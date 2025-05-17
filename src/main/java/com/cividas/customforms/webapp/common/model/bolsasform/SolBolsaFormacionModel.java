package com.cividas.customforms.webapp.common.model.bolsasform;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name="solBolsaFormacionModel")
@ViewScoped
public class SolBolsaFormacionModel extends InsertRegistry {

	private static final long serialVersionUID = 4109585718572663750L;

	@XmlElement
	SolBolsaFormacionDynamicDataModel registrytypedata;


	public SolBolsaFormacionDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}

	public void setRegistrytypedata(SolBolsaFormacionDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}

}

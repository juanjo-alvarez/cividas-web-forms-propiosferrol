package com.cividas.customforms.webapp.common.model.licurb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;

@XmlRootElement
@ManagedBean(name="licUrbanisticaModel")
@ViewScoped
public class LicUrbanisticaModel extends InsertRegistry {

	private static final long serialVersionUID = -5899222873458684038L;

	@XmlElement
	LicUrbanisticaDynamicDataModel registrytypedata;
	

	public LicUrbanisticaDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}
	
	public void setRegistrytypedata(LicUrbanisticaDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}
	
}

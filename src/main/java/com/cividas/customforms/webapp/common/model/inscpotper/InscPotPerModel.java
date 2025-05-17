package com.cividas.customforms.webapp.common.model.inscpotper;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;

@XmlRootElement
@ManagedBean(name="inscPotPerModel")
@ViewScoped
public class InscPotPerModel extends InsertRegistry {

	private static final long serialVersionUID = -5899222873458684038L;

	@XmlElement
	InscPotPerDynamicDataModel registrytypedata;
	

	public InscPotPerDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}
	
	public void setRegistrytypedata(InscPotPerDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}
	
}

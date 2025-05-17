package com.cividas.customforms.webapp.common.model.quinquenios;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name="quinqueniosModel")
@ViewScoped
public class QuinqueniosModel extends InsertRegistry {

	private static final long serialVersionUID = -5869242173458684038L;

	@XmlElement
	QuinqueniosDynamicDataModel registrytypedata;
		
	public QuinqueniosModel() {
		super();		
	}
	
	public QuinqueniosDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}
	
	public void setRegistrytypedata(QuinqueniosDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}
}

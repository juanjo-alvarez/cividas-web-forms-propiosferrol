package com.cividas.customforms.webapp.common.model.quejsugmultiple;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;

@XmlRootElement
@ManagedBean(name="quejasSugerenciasMultipleModel")
@ViewScoped
public class QuejasSugerenciasMultipleModel extends InsertRegistry {

	private static final long serialVersionUID = -5899222873458684038L;
	
	@XmlElement
	QuejasSugerenciasMultipleDynamicDataModel registrytypedata;
	

	public QuejasSugerenciasMultipleDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}
	
	public void setRegistrytypedata(QuejasSugerenciasMultipleDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}
	
}

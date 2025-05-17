package com.cividas.customforms.webapp.common.model.quejsug;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;

@XmlRootElement
@ManagedBean(name="quejasSugerenciasModel")
@ViewScoped
public class QuejasSugerenciasModel extends InsertRegistry {

	private static final long serialVersionUID = -5899222873458684038L;

	@XmlElement
	QuejasSugerenciasDynamicDataModel registrytypedata;
	

	public QuejasSugerenciasDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}
	
	public void setRegistrytypedata(QuejasSugerenciasDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}
	
}

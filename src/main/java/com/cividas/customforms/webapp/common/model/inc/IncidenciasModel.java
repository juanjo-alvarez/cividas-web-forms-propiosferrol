package com.cividas.customforms.webapp.common.model.inc;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;

@XmlRootElement
@ManagedBean(name="incidenciasModel")
@ViewScoped
public class IncidenciasModel extends InsertRegistry {

	private static final long serialVersionUID = -5899222873458684038L;

	@XmlElement
	IncidenciasDynamicDataModel registrytypedata;
	

	public IncidenciasDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}
	
	public void setRegistrytypedata(IncidenciasDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}
	
}

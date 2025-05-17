package com.cividas.customforms.webapp.common.model.derpd;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name="derProtDatosModel")
@ViewScoped
public class DerProtDatosModel extends InsertRegistry {

	private static final long serialVersionUID = 4109585718572663750L;

	@XmlElement
	DerProtDatosDynamicDataModel registrytypedata;


	public DerProtDatosDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}

	public void setRegistrytypedata(DerProtDatosDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}

}

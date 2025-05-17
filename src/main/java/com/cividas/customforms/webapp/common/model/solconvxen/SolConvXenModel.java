package com.cividas.customforms.webapp.common.model.solconvxen;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name="solConvXenModel")
@ViewScoped
public class SolConvXenModel extends InsertRegistry {

	private static final long serialVersionUID = 4109585718572663750L;

	@XmlElement
	SolConvXenDynamicDataModel registrytypedata;


	public SolConvXenDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}

	public void setRegistrytypedata(SolConvXenDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}

}

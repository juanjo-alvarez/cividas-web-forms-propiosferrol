package com.cividas.customforms.webapp.common.model.solconvxenexen;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name="solConvXenExenModel")
@ViewScoped
public class SolConvXenExenModel extends InsertRegistry {

	private static final long serialVersionUID = 4109444418572663750L;

	@XmlElement
	SolConvXenExenDynamicDataModel registrytypedata;


	public SolConvXenExenDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}

	public void setRegistrytypedata(SolConvXenExenDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}

}

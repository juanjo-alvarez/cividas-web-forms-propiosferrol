package com.cividas.customforms.webapp.common.model.solgratuidademat;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name="solGratMatModel")
@ViewScoped
public class SolGratMatModel extends InsertRegistry {

	private static final long serialVersionUID = 4109585718572663750L;

	@XmlElement
	SolGratMatDynamicDataModel registrytypedata;


	public SolGratMatDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}

	public void setRegistrytypedata(SolGratMatDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}

}

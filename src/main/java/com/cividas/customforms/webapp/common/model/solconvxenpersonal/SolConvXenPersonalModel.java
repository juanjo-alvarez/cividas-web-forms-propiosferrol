package com.cividas.customforms.webapp.common.model.solconvxenpersonal;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name="solConvXenPersonalModel")
@ViewScoped
public class SolConvXenPersonalModel extends InsertRegistry {

	private static final long serialVersionUID = -6270933292983082843L;

	@XmlElement
	SolConvXenPersonalDynamicDataModel registrytypedata;


	public SolConvXenPersonalDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}

	public void setRegistrytypedata(SolConvXenPersonalDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}

}

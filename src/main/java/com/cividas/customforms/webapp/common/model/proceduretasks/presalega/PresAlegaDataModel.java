package com.cividas.customforms.webapp.common.model.proceduretasks.presalega;

import com.cividas.customforms.webapp.common.model.base.InsertRegistryTask;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name="presAlegaDataModel")
@ViewScoped
public class PresAlegaDataModel extends InsertRegistryTask {

	private static final long serialVersionUID = -5899222873458684038L;

	@XmlElement
	PresAlegaDynamicDataModel registrytypedata;
	

	public PresAlegaDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}
	
	public void setRegistrytypedata(PresAlegaDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}
	
}

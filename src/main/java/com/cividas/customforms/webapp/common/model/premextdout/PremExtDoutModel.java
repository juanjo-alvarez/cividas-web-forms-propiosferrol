package com.cividas.customforms.webapp.common.model.premextdout;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name="premExtDoutModel")
@ViewScoped
public class PremExtDoutModel extends InsertRegistry {

	private static final long serialVersionUID = -5869242173458684038L;

	@XmlElement
	PremExtDoutDynamicDataModel registrytypedata;
		
	public PremExtDoutModel() {
		super();		
	}
	
	public PremExtDoutDynamicDataModel getRegistrytypedata() {
		return this.registrytypedata;
	}
	
	public void setRegistrytypedata(PremExtDoutDynamicDataModel registrytypedata) {
		this.registrytypedata = registrytypedata;
	}
}

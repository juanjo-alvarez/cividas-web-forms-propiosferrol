package com.cividas.customforms.webapp.common.model.base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegistryPermission {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement
	private Number idservicegroup;
	
	@XmlElement
	private Number permission;

	public Number getIdservicegroup() {
		return idservicegroup;
	}

	public void setIdservicegroup(Number idservicegroup) {
		this.idservicegroup = idservicegroup;
	}

	public Number getPermission() {
		return permission;
	}

	public void setPermission(Number permission) {
		this.permission = permission;
	}
	
	
}

package com.cividas.customforms.webapp.common.model.proceduretasks.presalega;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class PresAlegaDynamicDataModel implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8984361238256494782L;

	private String				alegacions;

	public String getAlegacions() {
		return alegacions;
	}

	public void setAlegacions(String alegacions) {
		this.alegacions = alegacions;
	}
}

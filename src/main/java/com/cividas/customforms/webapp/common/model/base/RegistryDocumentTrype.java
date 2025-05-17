package com.cividas.customforms.webapp.common.model.base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegistryDocumentTrype {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement
	private Integer iddocumentmastertype;
	
	@XmlElement
	private String documentTypeName;

	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

	public Integer getIddocumentmastertype() {
		return iddocumentmastertype;
	}

	public void setIddocumentmastertype(Integer iddocumentmastertype) {
		this.iddocumentmastertype = iddocumentmastertype;
	}

}

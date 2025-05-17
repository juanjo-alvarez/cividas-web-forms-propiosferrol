package com.cividas.customforms.webapp.common.model.base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class RegistryDocumentTypeTax {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private Integer iddocumentmastertype;

	@XmlElement
	private String documentTypeName;

	@XmlElement
	private List<RegistryTaxReport> listReports;

	@XmlElement
	private Integer idattachmentdata;

	@XmlElement
	private Integer listReportsSize;

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

	public List<RegistryTaxReport> getListReports() {
		return listReports;
	}

	public void setListReports(List<RegistryTaxReport> listReports) {
		this.listReports = listReports;
	}

	public Integer getListReportsSize() {
		return listReports == null ? 0 : listReports.size();
	}

	public Integer getIdattachmentdata() {
		return idattachmentdata;
	}

	public void setIdattachmentdata(Integer idattachmentdata) {
		this.idattachmentdata = idattachmentdata;
	}
}

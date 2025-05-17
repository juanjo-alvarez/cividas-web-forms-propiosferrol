package com.cividas.customforms.webapp.common.model.base;

import java.util.Date;
import java.io.Serializable;
import java.text.DecimalFormat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegistryAttachment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement
	private String attachmentfilename;
	
	@XmlElement
	private String attachmentbytearray;
	
	@XmlElement
	private String attachmentdatadesc;

	@XmlElement
	private Number iddocumentmastertype;
	
	@XmlElement
	private String observations;
	
	@XmlElement
	private Number webpublishabledoc;
	
	@XmlElement
	private Number blocktaskending;
	
	@XmlElement
	private Number origin;
	
	@XmlElement
	private Number iddocumentalnature;
	
	@XmlElement
	private Number iddocumentaltype;
	
	@XmlElement
	private Date documentationdate;
	
	@XmlElement
	private Date expirydate;
	
	@XmlElement
	protected Object otherAttachmentdata;
	
	 //[INI][CE-1024] IFrame base para el detalle de los expedientes
	@XmlElement
	protected Number attachmentfilesize;
	
	@XmlElement
	protected String csv;
	 //[FIN][CE-1024] IFrame base para el detalle de los expedientes
	
	//[INI][CE-650] Documentación aportada en los registros telemáticos
	@XmlElement
	private Number idindividualsmaster;
	//[FIN][CE-650] Documentación aportada en los registros telemáticos
	
	public String getAttachmentfilename() {
		return attachmentfilename;
	}

	public void setAttachmentfilename(String attachmentfilename) {
		this.attachmentfilename = attachmentfilename;
	}

	public String getAttachmentbytearray() {
		return attachmentbytearray;
	}

	public void setAttachmentbytearray(String attachmentbytearray) {
		this.attachmentbytearray = attachmentbytearray;
	}

	public String getAttachmentdatadesc() {
		return attachmentdatadesc;
	}

	public void setAttachmentdatadesc(String attachmentdatadesc) {
		this.attachmentdatadesc = attachmentdatadesc;
	}

	public Number getIddocumentmastertype() {
		return iddocumentmastertype;
	}

	public void setIddocumentmastertype(Number iddocumentmastertype) {
		this.iddocumentmastertype = iddocumentmastertype;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Number getWebpublishabledoc() {
		return webpublishabledoc;
	}

	public void setWebpublishabledoc(Number webpublishabledoc) {
		this.webpublishabledoc = webpublishabledoc;
	}

	public Number getBlocktaskending() {
		return blocktaskending;
	}

	public void setBlocktaskending(Number blocktaskending) {
		this.blocktaskending = blocktaskending;
	}

	public Number getOrigin() {
		return origin;
	}

	public void setOrigin(Number origin) {
		this.origin = origin;
	}

	public Number getIddocumentalnature() {
		return iddocumentalnature;
	}

	public void setIddocumentalnature(Number iddocumentalnature) {
		this.iddocumentalnature = iddocumentalnature;
	}

	public Number getIddocumentaltype() {
		return iddocumentaltype;
	}

	public void setIddocumentaltype(Number iddocumentaltype) {
		this.iddocumentaltype = iddocumentaltype;
	}

	public Date getDocumentationdate() {
		return documentationdate;
	}

	public void setDocumentationdate(Date documentationdate) {
		this.documentationdate = documentationdate;
	}

	public Date getExpirydate() {
		return expirydate;
	}

	public void setExpirydate(Date expirydate) {
		this.expirydate = expirydate;
	}

	 //[INI][CE-1024] IFrame base para el detalle de los expedientes
	public Number getIdindividualsmaster() {
		return idindividualsmaster;
	}

	public void setIdindividualsmaster(Number idindividualsmaster) {
		this.idindividualsmaster = idindividualsmaster;
	}
	 //[FIN][CE-1024] IFrame base para el detalle de los expedientes

	public Object getOtherAttachmentdata() {
		return otherAttachmentdata;
	}

	public void setOtherAttachmentdata(Object otherAttachmentdata) {
		this.otherAttachmentdata = otherAttachmentdata;
	}
	 //[INI][CE-1024] IFrame base para el detalle de los expedientes
	public String getAttachmentfilesize() {
		
		if(attachmentfilesize == null) {
			return "0";
		}
		
		Long size = attachmentfilesize.longValue();
		
		if(size <= 0) {
		    	return "0";
	    }
	    final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
	    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
	    return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

	public void setAttachmentfilesize(Number attachmentfilesize) {
		this.attachmentfilesize = attachmentfilesize;
	}

	public String getCsv() {
		return csv;
	}

	public void setCsv(String csv) {
		this.csv = csv;
	}
	 //[FIN][CE-1024] IFrame base para el detalle de los expedientes
	
}

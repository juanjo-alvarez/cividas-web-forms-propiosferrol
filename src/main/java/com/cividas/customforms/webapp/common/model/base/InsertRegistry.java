package com.cividas.customforms.webapp.common.model.base;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@XmlRootElement
public class InsertRegistry implements Serializable, IRegistryData {
    private static final long serialVersionUID = 1L;

    @XmlElement
    protected int sessionid;
    
    @XmlElement
    protected String username;

    @XmlElement
    protected RegistryInterested [] interesteddata;

    @XmlElement
    protected RegistryAttachment [] attachments;
    
    @XmlElement
    protected RegistryPermission [] permissions;

	@XmlElement
	protected RegistryTaxReport[] taxreports;
    
    @XmlElement
    protected String registrytypecode;
    
    @XmlElement
    protected String subject;
    
    @XmlElement
    protected String observations;

    @XmlElement
    protected Number idproceduretype;
    
    @XmlElement
	protected Number idbduacproceduretype;
    
    @XmlElement
    protected String siacode;
    
    @XmlElement
    protected String locale;
    
    
    
	public int getSessionid() {
		return sessionid;
	}

	public void setSessionid(int sessionid) {
		this.sessionid = sessionid;
	}

	public RegistryInterested[] getInteresteddata() {
		return interesteddata;
	}

	public void setInteresteddata(RegistryInterested[] interesteddata) {
		this.interesteddata = interesteddata;
	}

	public RegistryAttachment[] getAttachments() {
		return attachments;
	}

	public void setAttachments(RegistryAttachment[] attachments) {
		this.attachments = attachments;
	}
	
	public RegistryPermission[] getPermissions() {
		return permissions;
	}

	public void setPermissions(RegistryPermission[] permissions) {
		this.permissions = permissions;
	}

	public String getRegistrytypecode() {
		return registrytypecode;
	}

	public void setRegistrytypecode(String registrytypecode) {
		this.registrytypecode = registrytypecode;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}


    public Number getIdproceduretype() {
		return idproceduretype;
	}

	public void setIdproceduretype(Number idproceduretype) {
		this.idproceduretype = idproceduretype;
	}

	public Number getIdbduacproceduretype() {
		return idbduacproceduretype;
	}

	public void setIdbduacproceduretype(Number idbduacproceduretype) {
		this.idbduacproceduretype = idbduacproceduretype;
	}

	public String getSiacode() {
		return siacode;
	}

	public void setSiacode(String siacode) {
		this.siacode = siacode;
	}
	
	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public RegistryTaxReport[] getTaxreports() {
		return taxreports;
	}

	public void setTaxreports(RegistryTaxReport[] taxreports) {
		this.taxreports = taxreports;
	}

	public String toJSON() throws JsonProcessingException {
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.setSerializationInclusion(Include.NON_NULL);
    	DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    	mapper.setDateFormat(df);
    	return mapper.writeValueAsString(this);
    }

}
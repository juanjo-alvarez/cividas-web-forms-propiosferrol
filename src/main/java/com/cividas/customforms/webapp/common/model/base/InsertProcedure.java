package com.cividas.customforms.webapp.common.model.base;

import java.io.Serializable;
import java.util.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
* [CE-1024] IFrame base para el detalle de los expedientes
* 
* @author juanjo.alvarez
* @since 28/11/2019
*/

@XmlRootElement
public class InsertProcedure implements Serializable, IRegistryData {
    private static final long serialVersionUID = 1L;

    @XmlElement
    protected int sessionid;
    
    @XmlElement
    protected String username;

    @XmlElement
    protected RegistryInterested [] interesteddata;

    @XmlElement
    protected Vector<RegistryAttachment> attachments;
    
    @XmlElement
    protected Vector<RegistryTask> tasks;
    
    @XmlElement
    protected Number idprocedure;
    
    @XmlElement
    protected String procedurecode;
    
    @XmlElement
    protected String proceduretypeframeurl;
    
    @XmlElement
    protected Number proceduretypeframeentity;
    
    @XmlElement
    protected String proceduretypeframeentityname;
    
    @XmlElement
    protected String proceduretypecode;
    
    @XmlElement
    protected String creationdate;
    
    @XmlElement
    protected String applicantsubject;
    
    @XmlElement
    protected String servicegroupcode;
    
    @XmlElement
    protected String proceduretypedesc;
    
    @XmlElement
    protected Number idservicegroup;
    
    @XmlElement
    protected String servicegroupname;
    
    @XmlElement
    protected String statedescription;
    
    @XmlElement
    protected Number idservicegroupresponsible;
    
    @XmlElement
    protected String proceduretypedescweb_lg1;
    
    @XmlElement
    protected String proceduretypedescweb_lg2;
    
    @XmlElement
    protected String language;
    
    @XmlElement
    protected String dossierstatusdescweb_lg1;
    
    @XmlElement
    protected String dossierstatusdescweb_lg2;
    
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

	public Vector<RegistryAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Vector<RegistryAttachment> attachments) {
		this.attachments = attachments;
	}
	
	public Vector<RegistryTask> getTasks() {
		return tasks;
	}

	public void setTasks(Vector<RegistryTask> tasks) {
		this.tasks = tasks;
	}

	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String toJSON() throws JsonProcessingException {
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.setSerializationInclusion(Include.NON_NULL);
    	return mapper.writeValueAsString(this);
    }

	public Number getIdprocedure() {
		return idprocedure;
	}

	public void setIdprocedure(Number idprocedure) {
		this.idprocedure = idprocedure;
	}

	public String getProcedurecode() {
		return procedurecode;
	}

	public void setProcedurecode(String procedurecode) {
		this.procedurecode = procedurecode;
	}

	public String getProceduretypeframeurl() {
		return proceduretypeframeurl;
	}

	public void setProceduretypeframeurl(String proceduretypeframeurl) {
		this.proceduretypeframeurl = proceduretypeframeurl;
	}

	public Number getProceduretypeframeentity() {
		return proceduretypeframeentity;
	}

	public void setProceduretypeframeentity(Number proceduretypeframeentity) {
		this.proceduretypeframeentity = proceduretypeframeentity;
	}

	public String getProceduretypecode() {
		return proceduretypecode;
	}

	public void setProceduretypecode(String proceduretypecode) {
		this.proceduretypecode = proceduretypecode;
	}

	public String getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(String creationdate) {
		this.creationdate = creationdate;
	}

	public String getApplicantsubject() {
		return applicantsubject;
	}

	public void setApplicantsubject(String applicantsubject) {
		this.applicantsubject = applicantsubject;
	}

	public String getServicegroupcode() {
		return servicegroupcode;
	}

	public void setServicegroupcode(String servicegroupcode) {
		this.servicegroupcode = servicegroupcode;
	}

	public String getProceduretypedesc() {
		return proceduretypedesc;
	}

	public void setProceduretypedesc(String proceduretypedesc) {
		this.proceduretypedesc = proceduretypedesc;
	}

	public Number getIdservicegroup() {
		return idservicegroup;
	}

	public void setIdservicegroup(Number idservicegroup) {
		this.idservicegroup = idservicegroup;
	}

	public String getServicegroupname() {
		return servicegroupname;
	}

	public void setServicegroupname(String servicegroupname) {
		this.servicegroupname = servicegroupname;
	}

	public String getStatedescription() {
		return statedescription;
	}

	public void setStatedescription(String statedescription) {
		this.statedescription = statedescription;
	}

	public Number getIdservicegroupresponsible() {
		return idservicegroupresponsible;
	}

	public void setIdservicegroupresponsible(Number idservicegroupresponsible) {
		this.idservicegroupresponsible = idservicegroupresponsible;
	}

	public String getProceduretypedescweb_lg1() {
		return proceduretypedescweb_lg1;
	}

	public void setProceduretypedescweb_lg1(String proceduretypedescweb_lg1) {
		this.proceduretypedescweb_lg1 = proceduretypedescweb_lg1;
	}

	public String getProceduretypedescweb_lg2() {
		return proceduretypedescweb_lg2;
	}

	public void setProceduretypedescweb_lg2(String proceduretypedescweb_lg2) {
		this.proceduretypedescweb_lg2 = proceduretypedescweb_lg2;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDossierstatusdescweb_lg1() {
		if(dossierstatusdescweb_lg1!=null && !dossierstatusdescweb_lg1.isEmpty()) {
			return dossierstatusdescweb_lg1;
		}
		return dossierstatusdescweb_lg2;
	}

	public void setDossierstatusdescweb_lg1(String dossierstatusdescweb_lg1) {
		this.dossierstatusdescweb_lg1 = dossierstatusdescweb_lg1;
	}

	public String getDossierstatusdescweb_lg2() {
		if(dossierstatusdescweb_lg2!=null && !dossierstatusdescweb_lg2.isEmpty()) {
			return dossierstatusdescweb_lg2;
		}
		return dossierstatusdescweb_lg1;
	}

	public void setDossierstatusdescweb_lg2(String dossierstatusdescweb_lg2) {
		this.dossierstatusdescweb_lg2 = dossierstatusdescweb_lg2;
	}

	public String getProceduretypeframeentityname() {
		return proceduretypeframeentityname;
	}

	public void setProceduretypeframeentityname(String proceduretypeframeentityname) {
		this.proceduretypeframeentityname = proceduretypeframeentityname;
	}
	
}
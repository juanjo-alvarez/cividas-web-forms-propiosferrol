package com.cividas.customforms.webapp.common.model.licurb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LicUrbanisticaDynamicDataModel implements Serializable {

	private static final long serialVersionUID = 700040052695090153L;
	
	private String promotorname;
	private String promotorsurname1;
	private String promotorsurname2;
	private String promotoridentificationnumber;
	private String descobras;
	private String situacion;
	private String refcatastral;
	private Float orzamento;
	
	public String getPromotorname() {
		return promotorname;
	}
	public void setPromotorname(String promotorname) {
		this.promotorname = promotorname;
	}
	public String getPromotorsurname1() {
		return promotorsurname1;
	}
	public void setPromotorsurname1(String promotorsurname1) {
		this.promotorsurname1 = promotorsurname1;
	}
	public String getPromotorsurname2() {
		return promotorsurname2;
	}
	public void setPromotorsurname2(String promotorsurname2) {
		this.promotorsurname2 = promotorsurname2;
	}
	public String getPromotoridentificationnumber() {
		return promotoridentificationnumber;
	}
	public void setPromotoridentificationnumber(String promotoridentificationnumber) {
		this.promotoridentificationnumber = promotoridentificationnumber;
	}
	public String getDescobras() {
		return descobras;
	}
	public void setDescobras(String descobras) {
		this.descobras = descobras;
	}
	public String getSituacion() {
		return situacion;
	}
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	public String getRefcatastral() {
		return refcatastral;
	}
	public void setRefcatastral(String refcatastral) {
		this.refcatastral = refcatastral;
	}
	public Float getOrzamento() {
		return orzamento;
	}
	public void setOrzamento(Float orzamento) {
		this.orzamento = orzamento;
	}
	
	

	
	
	
}

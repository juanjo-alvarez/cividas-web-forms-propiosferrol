package com.cividas.customforms.webapp.common.model.quinquenios;

import com.cividas.customforms.webapp.common.model.base.JsonMultipleData;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@XmlRootElement
public class QuinqueniosDynamicDataModel implements Serializable {

	private static final long serialVersionUID = 702240053395090153L;
	
	private String departamento;
	private String centro;
	private String campus;
	private String corpoprofesorado;
	private String quinqrecrenunc;
	private String sexeniorenunc;
	private String observaciones;
	
	
	private String codigoambito;
	private String nombreambito;
	
	private Short solicita7quinq=0;
	private Boolean solicita7quinqbool;
	
	private JsonMultipleData jsonMultipleData;
	
	public QuinqueniosDynamicDataModel() {
		super();
	}

	
	public Short getSolicita7quinq() {
		return solicita7quinq;
	}

	public void setSolicita7quinq(Boolean solicita7quinqbool) {
		this.solicita7quinq = solicita7quinqbool ? new Short("1") : new Short("0");
	}

	
	public JsonMultipleData getJsonMultipleData() {
		return jsonMultipleData;
	}

	public void setJsonMultipleData(JsonMultipleData jsonMultipleData) {
		this.jsonMultipleData = jsonMultipleData;
	}


	public String getDepartamento() {
		return departamento;
	}


	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}


	public String getCentro() {
		return centro;
	}


	public void setCentro(String centro) {
		this.centro = centro;
	}


	public String getCampus() {
		return campus;
	}


	public void setCampus(String campus) {
		this.campus = campus;
	}


	public String getCorpoprofesorado() {
		return corpoprofesorado;
	}


	public void setCorpoprofesorado(String corpoprofesorado) {
		this.corpoprofesorado = corpoprofesorado;
	}


	public String getQuinqrecrenunc() {
		return quinqrecrenunc;
	}


	public void setQuinqrecrenunc(String quinqrecrenunc) {
		this.quinqrecrenunc = quinqrecrenunc;
	}


	public String getSexeniorenunc() {
		return sexeniorenunc;
	}


	public void setSexeniorenunc(String sexeniorenunc) {
		this.sexeniorenunc = sexeniorenunc;
	}


	public String getObservaciones() {
		return observaciones;
	}


	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}


	public Boolean getSolicita7quinqbool() {
		return solicita7quinqbool;
	}


	public void setSolicita7quinqbool(Boolean solicita7quinqbool) {
		this.solicita7quinqbool = solicita7quinqbool;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public void setSolicita7quinq(Short solicita7quinq) {
		this.solicita7quinq = solicita7quinq;
	}




	public String getNombreambito() {
		return nombreambito;
	}


	public void setNombreambito(String nombreambito) {
		this.nombreambito = nombreambito;
	}


	public String getCodigoambito() {
		return codigoambito;
	}


	public void setCodigoambito(String codigoambito) {
		this.codigoambito = codigoambito;
	}

}
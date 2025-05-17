package com.cividas.customforms.webapp.common.model.premextdout;

import com.cividas.customforms.webapp.common.model.base.JsonMultipleData;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@XmlRootElement
public class PremExtDoutDynamicDataModel implements Serializable {

	private static final long serialVersionUID = 702240053395090153L;
	
	private Number idambito;
	private Number idcneai;
	
	private String nombreambito;
	private String nombrecneai;
	
	private String titulotesis;
	private String dirigida;
	private String fechadeftesis;
	
	private Short mencionint=0;
	private Boolean mencionintbool;
	
	private String menciontext;
	
	private JsonMultipleData jsonMultipleData;
	
	public PremExtDoutDynamicDataModel() {
		super();
	}

	public String getTitulotesis() {
		return titulotesis;
	}

	public void setTitulotesis(String titulotesis) {
		this.titulotesis = titulotesis;
	}

	public String getDirigida() {
		return dirigida;
	}

	public void setDirigida(String dirigida) {
		this.dirigida = dirigida;
	}

	public String getFechadeftesis() {
		return fechadeftesis;
	}

	public void setFechadeftesis(String fechadeftesis) {
		this.fechadeftesis = fechadeftesis;
	}

	public Short getMencionint() {
		return mencionint;
	}

	public void setMencionint(Boolean mencionintbool) {
		this.mencionint = mencionintbool ? new Short("1") : new Short("0");
	}

	public Boolean getMencionintbool() {
		return mencionintbool;
	}

	public void setMencionintbool(Boolean mencionintbool) {
		this.mencionintbool = mencionintbool;
	}

	public Number getIdambito() {
		return idambito;
	}

	public void setIdambito(Number idambito) {
		this.idambito = idambito;
	}

	public Number getIdcneai() {
		return idcneai;
	}

	public void setIdcneai(Number idcneai) {
		this.idcneai = idcneai;
	}

	public JsonMultipleData getJsonMultipleData() {
		return jsonMultipleData;
	}

	public void setJsonMultipleData(JsonMultipleData jsonMultipleData) {
		this.jsonMultipleData = jsonMultipleData;
	}

	public String getNombreambito() {
		return nombreambito;
	}

	public void setNombreambito(String nombreambito) {
		this.nombreambito = nombreambito;
	}

	public String getNombrecneai() {
		return nombrecneai;
	}

	public void setNombrecneai(String nombrecneai) {
		this.nombrecneai = nombrecneai;
	}

	public String getMenciontext() {
		return menciontext;
	}

	public void setMenciontext(String menciontext) {
		this.menciontext = menciontext;
	}

}
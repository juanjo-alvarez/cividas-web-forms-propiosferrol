package com.cividas.customforms.webapp.common.model.inscpotper;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InscPotPerDynamicDataModel implements Serializable {

	private static final long serialVersionUID = 700040052695090153L;
	
	private String raza;
	private String especie;
	private String sexo;
	private String nomeanimal;
	private String nummicrochip;
	private String lugarresidenciaanimal;
	public String getRaza() {
		return raza;
	}
	public void setRaza(String raza) {
		this.raza = raza;
	}
	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getNomeanimal() {
		return nomeanimal;
	}
	public void setNomeanimal(String nomeanimal) {
		this.nomeanimal = nomeanimal;
	}
	public String getNummicrochip() {
		return nummicrochip;
	}
	public void setNummicrochip(String nummicrochip) {
		this.nummicrochip = nummicrochip;
	}
	public String getLugarresidenciaanimal() {
		return lugarresidenciaanimal;
	}
	public void setLugarresidenciaanimal(String lugarresidenciaanimal) {
		this.lugarresidenciaanimal = lugarresidenciaanimal;
	}
	
	

	
	
	
}

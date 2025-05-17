package com.cividas.customforms.webapp.common.model.derpd;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class DerProtDatosDynamicDataModel implements Serializable {

	private static final long serialVersionUID = -9157306028877789284L;

	private Short oposiciondni=0;
	private Boolean boposiciondni;
	private String motivosoposicion;
	private String motivosderecho;
	private String tipoderpddesc;
	private Short deracceso=0;
	private Short derrectificacion=0;
	private Short dersupresion=0;
	private Short derlimitacion=0;
	private Short deroposicion=0;
	private Short derportabilidad=0;


	public Short getOposiciondni() {
		return oposiciondni;
	}
	public void setOposiciondni(Boolean boposiciondni) {
		this.oposiciondni = boposiciondni?new Short("1"):new Short("0");
	}
	public String getMotivosoposicion() {
		return motivosoposicion;
	}
	public void setMotivosoposicion(String motivosoposicion) {
		this.motivosoposicion = motivosoposicion;
	}
	public String getMotivosderecho() {
		return motivosderecho;
	}
	public void setMotivosderecho(String motivosderecho) {
		this.motivosderecho = motivosderecho;
	}
	public String getTipoderpd() {
		String valor="";
		if(getDeracceso()>0)
			valor="deracceso";
		else if(getDerlimitacion()>0)
			valor="derlimitacion";
		else if(getDerrectificacion()>0)
			valor="derrectificacion";
		else if(getDersupresion()>0)
			valor="dersupresion";
		else if(getDerlimitacion()>0)
			valor="derlimitacion";
		else if(getDeroposicion()>0)
			valor="deroposicion";
		else if(getDerportabilidad()>0)
			valor="derportabilidad";
		return valor;
	}
	public void setTipoderpd(String tipoderpd) {
		//this.tipoderpd = tipoderpd;
		if(tipoderpd.equalsIgnoreCase("deracceso"))
			setDeracceso(new Short("1"));
		else if(tipoderpd.equalsIgnoreCase("derrectificacion"))
			setDerrectificacion(new Short("1"));
		else if(tipoderpd.equalsIgnoreCase("dersupresion"))
			setDersupresion(new Short("1"));
		else if(tipoderpd.equalsIgnoreCase("derlimitacion"))
			setDerlimitacion(new Short("1"));
		else if(tipoderpd.equalsIgnoreCase("deroposicion"))
			setDeroposicion(new Short("1"));
		else if(tipoderpd.equalsIgnoreCase("derportabilidad"))
			setDerportabilidad(new Short("1"));
	}
	public Short getDeracceso() {
		return deracceso;
	}
	public void setDeracceso(Short deracceso) {
		this.deracceso = deracceso;
	}
	public Short getDerrectificacion() {
		return derrectificacion;
	}
	public void setDerrectificacion(Short derrectificacion) {
		this.derrectificacion = derrectificacion;
	}
	public Short getDersupresion() {
		return dersupresion;
	}
	public void setDersupresion(Short dersupresion) {
		this.dersupresion = dersupresion;
	}
	public Short getDerlimitacion() {
		return derlimitacion;
	}
	public void setDerlimitacion(Short derlimitacion) {
		this.derlimitacion = derlimitacion;
	}
	public Short getDeroposicion() {
		return deroposicion;
	}
	public void setDeroposicion(Short deroposicion) {
		this.deroposicion = deroposicion;
	}
	public Short getDerportabilidad() {
		return derportabilidad;
	}
	public void setDerportabilidad(Short derportabilidad) {
		this.derportabilidad = derportabilidad;
	}

	public Boolean getBoposiciondni() {
		return boposiciondni;
	}

	public void setBoposiciondni(Boolean boposiciondni) {
		this.boposiciondni = boposiciondni;
	}
	public String getTipoderpddesc() {
		return tipoderpddesc;
	}
	public void setTipoderpddesc(String tipo) {
		if(getTipoderpd().equalsIgnoreCase("deracceso"))
			this.tipoderpddesc = "Acceso";
		else if(getTipoderpd().equalsIgnoreCase("derrectificacion"))
			this.tipoderpddesc = "Rectificación";
		else if(getTipoderpd().equalsIgnoreCase("dersupresion"))
			this.tipoderpddesc = "Supresión";
		else if(getTipoderpd().equalsIgnoreCase("derlimitacion"))
			this.tipoderpddesc = "Limitación";
		else if(getTipoderpd().equalsIgnoreCase("deroposicion"))
			this.tipoderpddesc = "Oposición";
		else if(getTipoderpd().equalsIgnoreCase("derportabilidad"))
			this.tipoderpddesc = "Portabilidade";
	}





}

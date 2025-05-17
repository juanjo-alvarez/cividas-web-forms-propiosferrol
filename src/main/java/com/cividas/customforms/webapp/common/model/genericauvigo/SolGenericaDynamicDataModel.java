package com.cividas.customforms.webapp.common.model.genericauvigo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class SolGenericaDynamicDataModel implements Serializable {

	private static final long serialVersionUID = -9157306028877789284L;

	
	public Number getDestino() {
		return destino;
	}
	public void setDestino(Number destino) {
		this.destino = destino;
	}
	public String getExpone() {
		return expone;
	}
	public void setExpone(String expone) {
		this.expone = expone;
	}
	public String getSolicita() {
		return solicita;
	}
	public void setSolicita(String solicita) {
		this.solicita = solicita;
	}
	public Number getActuaenrepresentacion() {
		return actuaenrepresentacion;
	}
	public void setActuaenrepresentacion(Number actuaenrepresentacion) {
		this.actuaenrepresentacion = actuaenrepresentacion;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private Number destino;
	private String expone;
	private String solicita;
	private Number actuaenrepresentacion;
	private String destinodesc;


	public String getDestinodesc() {
		return destinodesc;
	}
	public void setDestinodesc(String destinodesc) {
		this.destinodesc = destinodesc;
	}



	

}

package com.cividas.customforms.webapp.common.model.premextdout;

import java.io.Serializable;
import java.util.Date;

public class EstanciaBean implements Serializable {

	private static final long serialVersionUID = -5333242173458684038L;

	private Date fechainidate;
	private Date fechafindate;
	private String fechaini;
	private String fechafin;
	private String centrodestino;
	
	public EstanciaBean() {
		super();
	}

	public EstanciaBean(Date fechaIni, Date fechaFin, String centroDestino) {
		super();
		this.fechainidate = fechaIni;
		this.fechafindate = fechaFin;
		this.centrodestino = centroDestino;
	}

	public Date getFechainidate() {
		return fechainidate;
	}

	public void setFechainidate(Date fechainidate) {
		this.fechainidate = fechainidate;
	}

	public Date getFechafindate() {
		return fechafindate;
	}

	public void setFechafindate(Date fechafindate) {
		this.fechafindate = fechafindate;
	}

	public String getFechaini() {
		return fechaini;
	}

	public void setFechaini(String fechaini) {
		this.fechaini = fechaini;
	}

	public String getFechafin() {
		return fechafin;
	}

	public void setFechafin(String fechafin) {
		this.fechafin = fechafin;
	}

	public String getCentrodestino() {
		return centrodestino;
	}

	public void setCentrodestino(String centrodestino) {
		this.centrodestino = centrodestino;
	}
}

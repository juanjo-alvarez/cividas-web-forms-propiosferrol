package com.cividas.customforms.webapp.common.model.quinquenios;

import java.io.Serializable;
import java.util.Date;

public class PeriodoAvaliacionBean implements Serializable {

	private static final long serialVersionUID = -5333242173458684038L;

	private Date fechainidate;
	private Date fechafindate;
	private String fechainiaval;
	private String fechafinaval;
	private Long idperiodoaval;
	
	
	public PeriodoAvaliacionBean(String fechaini, String fechafin) {
		super();
		this.fechainiaval = fechaini;
		this.fechafinaval = fechafin;
	}

	public PeriodoAvaliacionBean() {
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
		return fechainiaval;
	}
	public void setFechaini(String fechaini) {
		this.fechainiaval = fechaini;
	}
	public String getFechafin() {
		return fechafinaval;
	}
	public void setFechafin(String fechafin) {
		this.fechafinaval = fechafin;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getIdperiodoaval() {
		return idperiodoaval;
	}

	public void setIdperiodoaval(Long idperiodoaval) {
		this.idperiodoaval = idperiodoaval;
	}
	
}

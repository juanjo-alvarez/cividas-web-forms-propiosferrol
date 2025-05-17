package com.cividas.customforms.webapp.common.model.quinquenios;

import java.io.Serializable;
import java.util.Date;

public class ServicioDocenteBean implements Serializable {

	private static final long serialVersionUID = -5333242173458684038L;

	private Long idserviciodocente;
	private String categoria;
	private String reximededicacion;
	private String rexime;
	private String organismo;
	private Date fechainidate;
	private Date fechafindate;
	private String fechaini;
	private String fechafin;
	
	public ServicioDocenteBean(String categoria, String reximededicacion, String rexime, String organismo,
			String fechaini, String fechafin) {
		super();
		this.categoria = categoria;
		this.reximededicacion = reximededicacion;
		this.rexime = rexime;
		this.organismo = organismo;
		this.fechaini = fechaini;
		this.fechafin = fechafin;
		this.idserviciodocente=null;
	}
	public ServicioDocenteBean() {
	}

	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getReximededicacion() {
		return reximededicacion;
	}
	public void setReximededicacion(String reximededicacion) {
		this.reximededicacion = reximededicacion;
	}
	public String getRexime() {
		return rexime;
	}
	public void setRexime(String rexime) {
		this.rexime = rexime;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getOrganismo() {
		return organismo;
	}
	public void setOrganismo(String organismo) {
		this.organismo = organismo;
	}
	public Long getIdserviciodocente() {
		return idserviciodocente;
	}
	public void setIdserviciodocente(Long idserviciodocente) {
		this.idserviciodocente = idserviciodocente;
	}

	

	
}

package com.cividas.customforms.webapp.common.model.premextdout;

import java.io.Serializable;

public class ContribucionBean implements Serializable {

	private static final long serialVersionUID = -5333242173458684038L;

	private String titulo;
	private String publicacion;
	private String descricion;
	private String coautores;
	
	public ContribucionBean() {
		super();
	}

	public ContribucionBean(String titulo, String publicacion, String descricion, String coautores) {
		super();
		this.titulo = titulo;
		this.publicacion = publicacion;
		this.descricion = descricion;
		this.coautores = coautores;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getPublicacion() {
		return publicacion;
	}
	public void setPublicacion(String publicacion) {
		this.publicacion = publicacion;
	}
	public String getDescricion() {
		return descricion;
	}
	public void setDescricion(String descricion) {
		this.descricion = descricion;
	}
	public String getCoautores() {
		return coautores;
	}
	public void setCoautores(String coautores) {
		this.coautores = coautores;
	}
	
}

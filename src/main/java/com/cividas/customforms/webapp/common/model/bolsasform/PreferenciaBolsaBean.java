package com.cividas.customforms.webapp.common.model.bolsasform;

import java.io.Serializable;

public class PreferenciaBolsaBean implements Serializable {

	private static final long serialVersionUID = -5333242173458684038L;

	private String descbolsa;
	private String codbolsa;
	private Long idpreferencia;
	
	
	public PreferenciaBolsaBean(String codBolsa, String descBolsa) {
		super();
		this.codbolsa = codBolsa;
		this.descbolsa = descBolsa;
	}

	public PreferenciaBolsaBean() {
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDescbolsa() {
		return descbolsa;
	}

	public void setDescbolsa(String descBolsa) {
		this.descbolsa = descBolsa;
	}

	public String getCodbolsa() {
		return codbolsa;
	}

	public void setCodbolsa(String codBolsa) {
		this.codbolsa = codBolsa;
	}

	public Long getIdpreferencia() {
		return idpreferencia;
	}

	public void setIdpreferencia(Long idpreferencia) {
		this.idpreferencia = idpreferencia;
	}

	
}

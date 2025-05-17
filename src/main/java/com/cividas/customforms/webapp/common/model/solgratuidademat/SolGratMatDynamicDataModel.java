package com.cividas.customforms.webapp.common.model.solgratuidademat;

import com.cividas.customforms.webapp.common.model.base.JsonMultipleData;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class SolGratMatDynamicDataModel implements Serializable {

	private static final long serialVersionUID = -9157306028877789284L;

	private Number consenttribute;
	private Number declararesponsablesok;
	private JsonMultipleData jsonMultipleData;
	private String coduniversidadesol;
	private String coduniversidadeben;
	private String codparentesco;
	
	public JsonMultipleData getJsonMultipleData() {
		return jsonMultipleData;
	}

	public void setJsonMultipleData(JsonMultipleData jsonMultipleData) {
		this.jsonMultipleData = jsonMultipleData;
	}

	public Number getConsenttribute() {
		return consenttribute;
	}
	public void setConsenttribute(Number consenttribute) {
		this.consenttribute = consenttribute;
	}
	public Number getDeclararesponsablesok() {
		return declararesponsablesok;
	}
	public void setDeclararesponsablesok(Number declararesponsablesok) {
		this.declararesponsablesok = declararesponsablesok;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCoduniversidadesol() {
		return coduniversidadesol;
	}

	public void setCoduniversidadesol(String coduniversidadesol) {
		this.coduniversidadesol = coduniversidadesol;
	}

	public String getCoduniversidadeben() {
		return coduniversidadeben;
	}

	public void setCoduniversidadeben(String coduniversidadeben) {
		this.coduniversidadeben = coduniversidadeben;
	}

	public String getCodparentesco() {
		return codparentesco;
	}

	public void setCodparentesco(String codparentesco) {
		this.codparentesco = codparentesco;
	}

	
}

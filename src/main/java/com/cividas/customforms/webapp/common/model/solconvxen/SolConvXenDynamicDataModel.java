package com.cividas.customforms.webapp.common.model.solconvxen;

import com.cividas.customforms.webapp.common.model.base.JsonMultipleData;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class SolConvXenDynamicDataModel implements Serializable {

	private static final long serialVersionUID = -9157306028877789284L;

	private Number consenttribute;
	private Number declararesponsablesok;
	private JsonMultipleData jsonMultipleData;

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
	
	
	
}

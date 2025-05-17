package com.cividas.customforms.webapp.common.model.bolsasform;

import com.cividas.customforms.webapp.common.model.base.JsonMultipleData;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class SolBolsaFormacionDynamicDataModel implements Serializable {

	private static final long serialVersionUID = -9157306028877789284L;

	

	private String iban;
	private Number datatrue;
	private Number termscall;
	private Number maintainenrollment;
	private Number obligationtax;

	private JsonMultipleData jsonMultipleData;

	public JsonMultipleData getJsonMultipleData() {
		return jsonMultipleData;
	}

	public void setJsonMultipleData(JsonMultipleData jsonMultipleData) {
		this.jsonMultipleData = jsonMultipleData;
	}

	
	public String getiban() {
		return iban;
	}
	
	public void setiban(String iban) {
		this.iban = iban;
	}


	
	//Declaracion responsable
	
	public Number getdatatrue() {
		return datatrue;
	}
	
	public void setdatatrue(Number datatrue) {
		this.datatrue = datatrue;
	}

	public Number gettermscall() {
		return termscall;
	}
	
	public void settermscall(Number termscall) {
		this.termscall = termscall;
	}
	
	
	public Number getmaintainenrollment() {
		return maintainenrollment;
	}
	
	public void setmaintainenrollment(Number maintainenrollment) {
		this.maintainenrollment = maintainenrollment;
	}
	
	public Number getobligationtax() {
		return obligationtax;
	}
	
	public void setobligationtax(Number obligationtax) {
		this.obligationtax = obligationtax;
	}
	

}

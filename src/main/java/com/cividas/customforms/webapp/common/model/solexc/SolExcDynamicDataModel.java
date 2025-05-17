package com.cividas.customforms.webapp.common.model.solexc;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class SolExcDynamicDataModel implements Serializable {

	private static final long serialVersionUID = -9157306028877789284L;

	
	private Number idhelprequest;
	private String helprequestdesc;
	private String iban;
	private Number oppositionverificationss;
	private String oppositionmotive;
	private Number consenttribute;
	private Number datatrue;
	private Number termscall;
	private Number maintainenrollment;
	private Number obligationtax;


	public Number getIdhelprequest() {
		return idhelprequest;
	}
	
	public void setidhelprequest(Number idhelprequest) {
		this.idhelprequest = idhelprequest;
	}
	
	public String gethelprequestdesc() {
		return helprequestdesc;
	}
	
	public void sethelprequestdesc(String helprequestdesc) {
		this.helprequestdesc = helprequestdesc;
	}
	
	public String getiban() {
		return iban;
	}
	
	public void setiban(String iban) {
		this.iban = iban;
	}
	
	public Number getoppositionverificationss() {
		return oppositionverificationss;
	}
	
	public void setoppositionverificationss(Number oppositionverificationss) {
		this.oppositionverificationss = oppositionverificationss;
	}
	
	public String getoppositionmotive() {
		return oppositionmotive;
	}
	
	public void setoppositionmotive(String oppositionmotive) {
		this.oppositionmotive = oppositionmotive;
	}
	
	public void valueoppositionverificationssChanged() {
		if ((int)getoppositionverificationss() == 0) {
			setoppositionmotive(null);
		}
	}	
	
	public void setconsenttribute(Number consenttribute) {
		this.consenttribute = consenttribute;
	}
	public Number getconsenttribute() {
		return consenttribute;
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

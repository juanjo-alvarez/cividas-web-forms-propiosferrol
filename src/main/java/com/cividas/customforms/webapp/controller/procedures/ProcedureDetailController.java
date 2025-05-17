package com.cividas.customforms.webapp.controller.procedures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.cividas.customforms.webapp.common.model.base.InsertProcedure;
import com.cividas.customforms.webapp.controller.base.BaseProcedureController;
import com.cividas.web.common.FieldNamesWeb;

/**
* [CE-1024] IFrame base para el detalle de los expedientes
* 
* @author juanjo.alvarez
* @since 28/11/2019
*/

@ManagedBean(name="procedureDetailController")
@ViewScoped
public class ProcedureDetailController extends BaseProcedureController {

	private static final long serialVersionUID = 3041301592924597211L;
	
	@ManagedProperty("#{procedureDetailModel}")
	InsertProcedure model;
	
	private Map<String, String> tiposExpedientes = new HashMap<String, String>();
	private String procedurecode;
	private String proceduretypedesc;
	private String statuscitizenportal;
	private String applicantsubject;
	private String creationdate;
	private Integer attachcount;
	private Integer taskcount;
	
	private HashMap procData;
	
	

	public HashMap getProcData() {
		return procData;
	}

	public void setProcData(HashMap procData) {
		this.procData = procData;
	}

	@Override
	public void init (){
		log.info("Inicializando el controlador: Procedure detail ..." );
	
		// Cargamos este modelo como el modelo base (esto es importante ya que el BaseController debe saber 
		// cual es el modelo con el que trabajará para invocar la llamada al servidor)
		this.baseModel = model;
		
		super.init();
	}
	
	@Override
	public String receiveParentData() {
		log.info("Recibiendo los parámetros de sessión de la sede ..." );
		super.receiveParentData();
		if(receivedParameters.containsKey("entitydata")) {
			setProcData((HashMap) receivedParameters.get("entitydata"));
		}
		return null;
	}
	
	private HashMap<String, String> getFilterFromPrameter(){
		//TODO
		HashMap<String, String> filter = new HashMap<>();
		return filter;
	}
	
	public void loadDetail() {

	}
	
	@Override
	public String sendRequest() {
		return "";
	}

	public String getProcedurecode() {
		return procedurecode;
	}

	public void setProcedurecode(String procedurecode) {
		this.procedurecode = procedurecode;
	}

	public Map<String, String> getTiposExpedientes() {
		return tiposExpedientes;
	}

	public void setTiposExpedientes(Map<String, String> tiposExpedientes) {
		this.tiposExpedientes = tiposExpedientes;
	}

	public String getProceduretypedesc() {
		String localeLanguage = getLanguageBean().getLang();
		if (localeLanguage.equals(FieldNamesWeb.LOCALE_LANGUAGE_LG2)){
			proceduretypedesc = model.getProceduretypedescweb_lg2();
		} else {
			proceduretypedesc = model.getProceduretypedescweb_lg1();
		}
		return proceduretypedesc;
	}

	
	public void setProceduretypedesc(String proceduretypedesc) {
		this.proceduretypedesc = proceduretypedesc;
	}

	public String getStatuscitizenportal() {
		String localeLanguage = getLanguageBean().getLang();
		if (localeLanguage.equals(FieldNamesWeb.LOCALE_LANGUAGE_LG2)){
			statuscitizenportal = model.getDossierstatusdescweb_lg2();
		} else {
			statuscitizenportal = model.getDossierstatusdescweb_lg1();
		}
		return statuscitizenportal;
	}

	public void setStatuscitizenportal(String statuscitizenportal) {
		this.statuscitizenportal = statuscitizenportal;
	}
	
	public InsertProcedure getModel() {
		return model;
	}
	public void setModel(InsertProcedure model) {
		this.model = model;
	}

	public String getApplicantsubject() {
		return applicantsubject;
	}

	public void setApplicantsubject(String applicantsubject) {
		this.applicantsubject = applicantsubject;
	}

	public String getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(String creationdate) {
		this.creationdate = creationdate;
	}

	public Integer getAttachcount() {
		if(model.getAttachments()==null) {
			return 0;
		}
		attachcount=model.getAttachments().size();
		return attachcount;
	}
	
	public Integer getTaskcount() {
		if(model.getTasks()==null) {
			return 0;
		}
		taskcount=model.getTasks().size();
		return taskcount;
	}
	
	public static String getFileNameExtension(String fileName){
		
		if (fileName == null){
			return "";
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		Application app = context.getApplication();
		ArrayList<String> iconFileNames = (ArrayList<String>)app.evaluateExpressionGet(context, "#{appConfiguration.iconFileNames}", Object.class);
		String[] parts = fileName.split("\\.");
		String extensionPng = parts[parts.length-1]+".png";
		
		return iconFileNames.contains(extensionPng)?extensionPng:"file.png";
		
	}
}

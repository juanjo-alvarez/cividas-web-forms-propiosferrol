package com.cividas.customforms.webapp.controller.solexc;

import com.cividas.customforms.webapp.common.exceptions.CividasQueryDataException;
import com.cividas.customforms.webapp.common.model.base.RegistryAttachment;
import com.cividas.customforms.webapp.common.model.restresponse.RestResponseModel;
import com.cividas.customforms.webapp.controller.base.BaseControllerUvigo;
import com.cividas.customforms.webapp.utils.CollectionsUtils;
import com.cividas.web.common.FieldNamesWeb;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean(name="requestRegistryTypeCodeError")
@ViewScoped
public class RequestRegistryTypeCodeErrorController extends BaseControllerUvigo {

	private static final long serialVersionUID = 3041301592924597211L;
	
	
	protected RegistryAttachment report;
	protected String reportCSV;
	protected String registrycode;
	protected String baseurl;
		@Override
	public void init (){
		log.info("Inicializando el controlador: requestRegistryTypeCodeError ..." );
	    // respuesta
		if(FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("respuesta")!=null){
			respuesta=(RestResponseModel) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("respuesta");
		}
		
		if(FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("queryParameters")!=null){
			queryParameters=(Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("queryParameters");
		}
		 
		 loadCividasParameters ();
		 loadRegistryParameters();
	}
	
	
	protected void loadCividasParameters (){
		// Carga de parametros de Cividas que necesitamos
		try{
			
				report=new RegistryAttachment();
				Map<String, Object> attachmentQueryResult = queryCividasMaster(FieldNamesWeb.ECIVIDASPARAMETERS, CollectionsUtils.buildMap(FieldNamesWeb.PARAMETER,"Web.General.URLBase"), new ArrayList<Object>());
				if(attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)!=null && attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE) instanceof List 
						&& ((List)attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).size()==1 && ((List)attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).get(0)!=null){
					baseurl=(((List)attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).get(0).toString());
				}
				
			
		}catch (CividasQueryDataException cqe){
			log.error("Se ha producido un error al cargar datos del interesado. A continuación se mostrará la traza del error", cqe);
		}
	}
	
	protected void loadRegistryParameters (){
		// Carga de los datos de registro
		try{
			if(respuesta.getIdRegistry()!=null){
				Map<String, Object> registryQueryResult = queryCividasMaster(FieldNamesWeb.EREGISTRYINPUT, CollectionsUtils.buildMap(FieldNamesWeb.IDREGISTRY,respuesta.getIdRegistry()), new ArrayList<Object>());
				if(registryQueryResult.get(FieldNamesWeb.REGISTRYCODE)!=null && registryQueryResult.get(FieldNamesWeb.REGISTRYCODE) instanceof List 
						&& ((List)registryQueryResult.get(FieldNamesWeb.REGISTRYCODE)).size()==1 && ((List)registryQueryResult.get(FieldNamesWeb.REGISTRYCODE)).get(0)!=null){
					registrycode=(((List)registryQueryResult.get(FieldNamesWeb.REGISTRYCODE)).get(0).toString());
				}
			}

		}catch (CividasQueryDataException cqe){
			log.error("Se ha producido un error al cargar datos del interesado. A continuación se mostrará la traza del error", cqe);
		}
	}
	
	
	public RegistryAttachment getReport() {
		return report;
	}


	public void setReport(RegistryAttachment report) {
		this.report = report;
	}


	@Override
	public String receiveParentData() {

		return null;
	}

	@Override
	protected void loadDataMasters(){
	}
	
	
	
	@Override
	public String sendRequest() {
		String response = "success";
		// Envio de la solicitud. Aqui se incluirá la lógica propia del envío de cada formulario, en caso de que exista
		if("".equalsIgnoreCase(getMandatoryFilesUploaded())) {
			log.error("No se adjuntaron todos los documentos.");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("mandatorydocumentsmiss"), ""));
			return "error";
		}
		// En caso de exito, redirigir a una página de éxito.
		return response;
	}
	

	public String getRegistrycode() {
		return registrycode;
	}


	public void setRegistrycode(String registrycode) {
		this.registrycode = registrycode;
	}

	public String getBaseurl() {
		return baseurl;
	}


	public void setBaseurl(String baseurl) {
		this.baseurl = baseurl;
	}


	public String getReportCSV() {
		return reportCSV;
	}


	public void setReportCSV(String reportCSV) {
		this.reportCSV = reportCSV;
	}

}

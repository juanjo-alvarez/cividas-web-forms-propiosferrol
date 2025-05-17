package com.cividas.customforms.webapp.controller.procedures;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import com.cividas.customforms.webapp.common.model.procedures.ProcedureDocContributionDataModel;
import com.cividas.customforms.webapp.common.model.procedures.ProcedureDocContributionDynamicDataModel;
import com.cividas.customforms.webapp.controller.base.BaseController;

@ManagedBean(name="procedureDocContributionController")
@ViewScoped
public class ProcedureDocContributionController extends BaseController {

	private static final long serialVersionUID = 3041301592924597211L;
	
	@ManagedProperty("#{procedureDocContributionDataModel}")
	ProcedureDocContributionDataModel model;
	
	private ResourceBundle i18n;
	
	@Override
	public void init (){
		log.info("Inicializando el controlador: ProcedureDocContributionController ..." );
	
		FacesContext context = FacesContext.getCurrentInstance();
		i18n = context.getApplication().evaluateExpressionGet(context, "#{i18n}", ResourceBundle.class);
	    
		// Cargamos este modelo como el modelo base (esto es importante ya que el BaseController debe saber 
		// cual es el modelo con el que trabajará para invocar la llamada al servidor)
		this.baseModel = model;
		
		super.init();
		
		// Inicializando el modelo particular de datos
		model.setRegistrytypedata(new ProcedureDocContributionDynamicDataModel());
		model.setSubject("Sede electrónica - Aportacion documentación");
	}
	
	@Override
	public String receiveParentData() {
		log.info("Recibiendo los parámetros de sessión de la sede ..." );
		super.receiveParentData();
		
		return null;
	}

	@Override
	protected void loadDataMasters(){
		super.loadDataMasters();
	}
	
	public void validateAttachment(ComponentSystemEvent event) {
		
		if (this.uploadedFiles.isEmpty()) {
			String message = "";
			FacesContext fc = FacesContext.getCurrentInstance();
			try {
				i18n = fc.getApplication().evaluateExpressionGet(fc, "#{i18n}", ResourceBundle.class);
				message = i18n.getString("attachmentrequired");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			FacesMessage msg = new FacesMessage(message);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(null, msg);
			fc.renderResponse();
		}
	}
		
	@Override
	public String sendRequest() {
		String response = "success";
		try{
			// Envio de la solicitud. Aqui se incluirá la lógica propia del envío de cada formulario, en caso de que exista
			if (!areMadatoryTaxesComplete()) {
				log.error("No se ha seleccionado ninguna tasa necesaria.");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("mandatorytaxesdocumentsmessage"), ""));
				return "error";
			}
			if("".equalsIgnoreCase(getMandatoryFilesUploaded())) {
				log.error("No se adjuntaron todos los documentos.");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("mandatorydocumentsmiss"), ""));
				return "error";
			}

			// Alta del registro en Cividas
			respuesta = createNewRegistryInputTask();
			// Almacenamos la respuesta en el contexto
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters",queryParameters);
		}catch (Exception e) {
			// En caso de error, redirección a una página de error
			response = "error";
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters",queryParameters);
		}
		// En caso de exito, redirigir a una página de éxito.
		return response;
	}
	
	public ProcedureDocContributionDataModel getModel() {
		return model;
	}
	public void setModel(ProcedureDocContributionDataModel model) {
		this.model = model;
	}
}

package com.cividas.customforms.webapp.controller.derpd;

import com.cividas.customforms.webapp.common.model.derpd.DerProtDatosDynamicDataModel;
import com.cividas.customforms.webapp.common.model.derpd.DerProtDatosModel;
import com.cividas.customforms.webapp.controller.base.BaseControllerUvigo;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

@ManagedBean(name="derProtDatosController")
@ViewScoped
public class DerProtDatosController extends BaseControllerUvigo {

	private static final long serialVersionUID = 2148438200796166524L;

	@ManagedProperty("#{derProtDatosModel}")
	DerProtDatosModel model;

	private ResourceBundle i18n;

	@Override
	public void init (){
		log.info("Inicializando el controlador: DerProtDatosController ..." );

		// Cargamos este modelo como el modelo base (esto es importante ya que el BaseController debe saber
		// cual es el modelo con el que trabajará para invocar la llamada al servidor)
		this.baseModel = model;

		super.init();

		// Inicializando el modelo particular de datos
		model.setRegistrytypedata(new DerProtDatosDynamicDataModel());
		model.setSubject("Sede electrónica - Ejercicio derechos de protección de datos");
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


	@Override
	public String sendRequest() {
		String response = "success";
		try{
			// Envio de la solicitud. Aqui se incluirá la lógica propia del envío de cada formulario, en caso de que exista
			if("".equalsIgnoreCase(getMandatoryFilesUploaded())) {
				log.error("No se adjuntaron todos los documentos.");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("mandatorydocumentsmiss"), ""));
				return "error";
			}
			model.getRegistrytypedata().setOposiciondni(model.getRegistrytypedata().getBoposiciondni());
			model.getRegistrytypedata().setTipoderpddesc("");
			// Alta del registro en Cividas
			respuesta = createNewRegistryInput();
			if(respuesta.getIdRegistryReport() == null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, getResourceMessage("no_report"), ""));
			}
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

	public DerProtDatosModel getModel() {
		return model;
	}
	public void setModel(DerProtDatosModel model) {
		this.model = model;
	}



	public boolean isUserLogged() {

		return (queryParameters!=null && queryParameters.get("user")!=null)?true:false;
	}

	public void showDlg(String dlgName){
		PrimeFaces.current().executeScript(dlgName + ".show()");
	}

	public void validateAttachment() {
		if(model.getRegistrytypedata().getBoposiciondni()) {
			if (this.uploadedFiles.isEmpty()) {
				
				showError("requiredOposicionDNIMessage");
				FacesContext.getCurrentInstance().validationFailed();
			}
		}
	}

}

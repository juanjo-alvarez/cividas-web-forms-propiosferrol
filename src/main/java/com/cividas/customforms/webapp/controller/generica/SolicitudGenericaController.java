package com.cividas.customforms.webapp.controller.generica;

import com.cividas.customforms.webapp.common.model.generica.SolGenericaDynamicDataModel;
import com.cividas.customforms.webapp.common.model.generica.SolGenericaModel;
import com.cividas.customforms.webapp.controller.base.BaseController;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.LinkedHashMap;

@ManagedBean(name = "solicitudGenericaController")
@ViewScoped
public class SolicitudGenericaController extends BaseController {

	private static final long serialVersionUID = 2148438200796166524L;

	@ManagedProperty("#{solicitudGenericaModel}")
	SolGenericaModel model;
	
	
	private LinkedHashMap<String, String> destinos = new LinkedHashMap<String, String>();



	@Override
	public void init() {
		log.info("Inicializando el controlador: SolicitudGenericaController ...");

		// Cargamos este modelo como el modelo base (esto es importante ya que el
		// BaseController debe saber
		// cual es el modelo con el que trabajará para invocar la llamada al servidor)
		this.baseModel = model;

		super.init();

		// Inicializando el modelo particular de datos
		model.setRegistrytypedata(new SolGenericaDynamicDataModel());
//		model.setSubject("Sede electrónica - Solicitude de premio de excelencia");
	}

	@Override
	public String receiveParentData() {
		log.info("Recibiendo los parámetros de sessión de la sede ...");
		super.receiveParentData();

		return null;
	}

	@Override
	protected void loadDataMasters() {
		super.loadDataMasters();
		log.info("Cargando maestros propios de la solicitud generica");
	}

	@Override
	public String sendRequest() {
		// Envio de la solicitud. Aqui se incluirá la lógica propia del envío de cada
		// formulario, en caso de que exista

		String response = "success";

		try {
			// Envio de la solicitud. Aqui se incluirá la lógica propia del envío de cada formulario, en caso de que exista
			if("".equalsIgnoreCase(getMandatoryFilesUploaded())) {
				log.error("No se adjuntaron todos los documentos.");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("mandatorydocumentsmiss"), ""));
				return "error";
			}

			respuesta = createNewRegistryInput();
			if(respuesta.getIdRegistryReport() == null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, getResourceMessage("no_report"), ""));
			}
			// Almacenamos la respuesta en el contexto
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
		
			// Almacenamos la respuesta en el contexto
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters", queryParameters);
						
		} catch (Exception e) {
			// En caso de error, redirección a una página de error
			response = "error";
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters", queryParameters);
		}
		// En caso de exito, redirigir a una página de éxito.
		return response;
	}

	public SolGenericaModel getModel() {
		return model;
	}

	public void setModel(SolGenericaModel model) {
		this.model = model;
	}

	public boolean isUserLogged() {

		return (queryParameters != null && queryParameters.get("user") != null) ? true : false;
	}

}

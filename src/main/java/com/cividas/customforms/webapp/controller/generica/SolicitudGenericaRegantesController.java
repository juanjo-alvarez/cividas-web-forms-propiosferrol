package com.cividas.customforms.webapp.controller.generica;

import com.cividas.customforms.webapp.common.model.generica.SolGenericaRegantesDynamicDataModel;
import com.cividas.customforms.webapp.common.model.generica.SolGenericaRegantesModel;
import com.cividas.customforms.webapp.controller.base.BaseController;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "solicitudGenericaRegantesController")
@ViewScoped
public class SolicitudGenericaRegantesController extends BaseController {

	private static final long serialVersionUID = 2148438200796166524L;

	@ManagedProperty("#{solicitudGenericaRegantesModel}")
	SolGenericaRegantesModel model;
 	

	@Override
	public void init() {
		log.info("Inicializando el controlador: SolicitudGenericaRegantesController ...");

		this.baseModel = model;

		super.init();

		model.setRegistrytypedata(new SolGenericaRegantesDynamicDataModel());
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
		String response = "success";

		try {
			// Envio de la solicitud. Aqui se incluir? la l?gica propia del env?o de cada formulario, en caso de que exista
			// Alta del registro en Cividas
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

	public SolGenericaRegantesModel getModel() {
		return model;
	}

	public void setModel(SolGenericaRegantesModel model) {
		this.model = model;
	}

	public boolean isUserLogged() {

		return (queryParameters != null && queryParameters.get("user") != null) ? true : false;
	}

}

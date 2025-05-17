package com.cividas.customforms.webapp.controller.quejsug;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.cividas.customforms.webapp.common.exceptions.CividasQueryDataException;
import com.cividas.customforms.webapp.common.exceptions.CividasRegistrationException;
import com.cividas.customforms.webapp.common.model.quejsug.QuejasSugerenciasDynamicDataModel;
import com.cividas.customforms.webapp.common.model.quejsug.QuejasSugerenciasModel;
import com.cividas.customforms.webapp.controller.base.BaseController;
import com.cividas.customforms.webapp.utils.CollectionsUtils;
import com.cividas.customforms.webapp.utils.MastersUtils;

@ManagedBean(name="quejasSugerenciasController")
@ViewScoped
public class QuejasSugerenciasController extends BaseController {

	private static final long serialVersionUID = 3041301592924597211L;
	
	@ManagedProperty("#{quejasSugerenciasModel}")
	QuejasSugerenciasModel model;

	private Map<String, String> tiposQuejasSugerencias = new HashMap<String, String>();
	
	private boolean agreeWithPolicy = false;
	
	@Override
	public void init (){
		log.info("Inicializando el controlador: QuejasSugerenciasController ..." );
	
		// Cargamos este modelo como el modelo base (esto es importante ya que el BaseController debe saber 
		// cual es el modelo con el que trabajar� para invocar la llamada al servidor)
		this.baseModel = model;
		
		super.init();
		
		// Inicializando el modelo particular de datos
		model.setRegistrytypedata(new QuejasSugerenciasDynamicDataModel());
		model.setSubject("Sede electr�nica - Quejas y sugerencias");
	}
	
	@Override
	public String receiveParentData() {
		log.info("Recibiendo los par�metros de sessi�n de la sede ..." );
		super.receiveParentData();
		
		return null;
	}

	@Override
	protected void loadDataMasters(){
		super.loadDataMasters();
		log.info("Cargando maestros propios de la solicitud de quejas y sugerencias" );
		
		try{
			Map<String, Object> tiposQuejasSugerenciasQueryResult = queryCividasMaster("mquejsug", new HashMap<Object, Object>(), CollectionsUtils.buildList("idtipoquejsug", "desctipoquejsug"));
			this.tiposQuejasSugerencias = MastersUtils.parseMasterMap(tiposQuejasSugerenciasQueryResult, "idtipoquejsug", "desctipoquejsug");
		}catch (CividasQueryDataException cqe){
			log.error("Se ha producido un error al cargar el maestro de direcciones del interesado. A continuaci�n se mostrar� la traza del error", cqe);
		}
	}
	
	
	@Override
	public String sendRequest() {
		String response = "success";
		try{
			// Envio de la solicitud. Aqui se incluir� la l�gica propia del env�o de cada formulario, en caso de que exista
			// Alta del registro en Cividas
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

			respuesta = createNewRegistryInput();
			if(respuesta.getIdRegistryReport() == null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, getResourceMessage("no_report"), ""));
			}
			// Almacenamos la respuesta en el contexto
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters",queryParameters);
		}catch (Exception e) {
			// En caso de error, redirecci�n a una p�gina de error
			response = "error";
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters",queryParameters);
		}
		// En caso de exito, redirigir a una p�gina de �xito.
		return response;
	}
	
	public QuejasSugerenciasModel getModel() {
		return model;
	}
	public void setModel(QuejasSugerenciasModel model) {
		this.model = model;
	}
	
	public Map<String, String> getTiposQuejasSugerencias() {
		return tiposQuejasSugerencias;
	}
	
	public void setTiposQuejasSugerencias(Map<String, String> tiposQuejasSugerencias) {
		this.tiposQuejasSugerencias = tiposQuejasSugerencias;
	}
	
	public boolean isAgreeWithPolicy() {
		return agreeWithPolicy;
	}
	public void setAgreeWithPolicy(boolean agreeWithPolicy) {
		this.agreeWithPolicy = agreeWithPolicy;
	}
}

package com.cividas.customforms.webapp.controller.licurb;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.cividas.customforms.webapp.common.exceptions.CividasQueryDataException;
import com.cividas.customforms.webapp.common.model.licurb.LicUrbanisticaDynamicDataModel;
import com.cividas.customforms.webapp.common.model.licurb.LicUrbanisticaModel;
import com.cividas.customforms.webapp.common.model.quejsug.QuejasSugerenciasDynamicDataModel;
import com.cividas.customforms.webapp.common.model.quejsug.QuejasSugerenciasModel;
import com.cividas.customforms.webapp.controller.base.BaseController;
import com.cividas.customforms.webapp.utils.CollectionsUtils;
import com.cividas.customforms.webapp.utils.MastersUtils;

@ManagedBean(name="licUrbanisticaController")
@ViewScoped
public class LicUrbanisticaController extends BaseController {

	private static final long serialVersionUID = 3041301592924597211L;
	
	@ManagedProperty("#{licUrbanisticaModel}")
	LicUrbanisticaModel model;

	private boolean agreeWithPolicy = false;
	
	@Override
	public void init (){
		log.info("Inicializando el controlador: licUrbanisticaController ..." );
	
		// Cargamos este modelo como el modelo base (esto es importante ya que el BaseController debe saber 
		// cual es el modelo con el que trabajará para invocar la llamada al servidor)
		this.baseModel = model;
		
		super.init();
		
		// Inicializando el modelo particular de datos
		model.setRegistrytypedata(new LicUrbanisticaDynamicDataModel());
//		model.setSubject("Sede electrónica - Quejas y sugerencias");
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
//		log.info("Cargando maestros propios de la solicitud de quejas y sugerencias" );
//		
//		try{
//			Map<String, Object> tiposQuejasSugerenciasQueryResult = queryCividasMaster("mquejsug", new HashMap<Object, Object>(), CollectionsUtils.buildList("idtipoquejsug", "desctipoquejsug"));
//			this.tiposQuejasSugerencias = MastersUtils.parseMasterMap(tiposQuejasSugerenciasQueryResult, "idtipoquejsug", "desctipoquejsug");
//		}catch (CividasQueryDataException cqe){
//			log.error("Se ha producido un error al cargar el maestro de direcciones del interesado. A continuación se mostrará la traza del error", cqe);
//		}
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
	
	public LicUrbanisticaModel getModel() {
		return model;
	}
	public void setModel(LicUrbanisticaModel model) {
		this.model = model;
	}
	
	public boolean isAgreeWithPolicy() {
		return agreeWithPolicy;
	}
	public void setAgreeWithPolicy(boolean agreeWithPolicy) {
		this.agreeWithPolicy = agreeWithPolicy;
	}
}

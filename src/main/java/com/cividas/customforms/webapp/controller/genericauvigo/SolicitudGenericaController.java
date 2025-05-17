package com.cividas.customforms.webapp.controller.genericauvigo;

import com.cividas.customforms.webapp.common.exceptions.CividasQueryDataException;
import com.cividas.customforms.webapp.common.model.genericauvigo.SolGenericaDynamicDataModel;
import com.cividas.customforms.webapp.common.model.genericauvigo.SolGenericaModel;
import com.cividas.customforms.webapp.controller.base.BaseControllerUvigo;
import com.cividas.customforms.webapp.utils.CollectionsUtils;
import com.cividas.customforms.webapp.utils.MastersUtils;
import com.cividas.web.common.FieldNamesWeb;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ManagedBean(name = "solicitudGenericaUvigoController")
@ViewScoped
public class SolicitudGenericaController extends BaseControllerUvigo {

	private static final long serialVersionUID = 2148438200796166524L;

	@ManagedProperty("#{solicitudGenericaUvigoModel}")
	SolGenericaModel model;
	
	
	private LinkedHashMap<String, String> destinos = new LinkedHashMap<String, String>();
	private LinkedHashMap<String, String> destinoses = new LinkedHashMap<String, String>();
	
	private Map<String, Object> destinosMap = new HashMap<String, Object>();



	@Override
	public void init() {
		log.info("Inicializando el controlador: SolicitudGenericaUvigoController ...");

		this.baseModel = model;

		super.init();

		// Inicializando el modelo particular de datos
		model.setRegistrytypedata(new SolGenericaDynamicDataModel());
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
		log.info("Cargando maestros propios de la solicitud generica uvigo");
		try {	
			HashMap hkeysbduac=new HashMap<Object, Object>();
			Number idBduacProcedureType=null;
			if(getReceivedParameters().get(FieldNamesWeb.IDBDUACPROCEDURETYPE) instanceof String) {
				idBduacProcedureType=(Number)Integer.valueOf((String) getReceivedParameters().get(FieldNamesWeb.IDBDUACPROCEDURETYPE));
			}else {
				idBduacProcedureType=(Number) getReceivedParameters().get(FieldNamesWeb.IDBDUACPROCEDURETYPE);
			}

			if(idBduacProcedureType!=null) {
				hkeysbduac.put("idbduacproceduretype", idBduacProcedureType);
			}
			destinosMap = queryCividasMaster("easigdestinossede",hkeysbduac, CollectionsUtils.buildList("idservicegroup", "nombredestino", "nombredestino_es"));
			this.destinos = MastersUtils.parseMasterLinkedHashMap(destinosMap, "idservicegroup","nombredestino");			
			this.destinoses = MastersUtils.parseMasterLinkedHashMap(destinosMap, "idservicegroup","nombredestino_es");
			
		} catch (CividasQueryDataException cqe) {
			log.error("Se ha producido un error al cargar el maestro de los destinos de las solicitudes. A continuación se mostrará la traza del error",cqe);
		}

	}

	@Override
	public String sendRequest() {
		// Envio de la solicitud. Aqui se incluirá la lógica propia del envío de cada
		// formulario, en caso de que exista

		String response = "success";

		try {
			
			if("".equalsIgnoreCase(getMandatoryFilesUploaded())) {
				log.error("No se adjuntaron todos los documentos.");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("mandatorydocumentsmiss"), ""));
				return "error";
			}
			
			if(isRepresentadomanual()) {
				model.getRegistrytypedata().setActuaenrepresentacion(new Integer(1));
			} else {
				model.getRegistrytypedata().setActuaenrepresentacion(new Integer(0));
			}
			
			if(model.getRegistrytypedata().getDestino() != null) {
				if (this.getLanguageBean().getLang().equals(FieldNamesWeb.LOCALE_LANGUAGE_LG2)) {
					model.getRegistrytypedata().setDestinodesc(destinos.get(model.getRegistrytypedata().getDestino().toString()));
				} else {
					model.getRegistrytypedata().setDestinodesc(destinoses.get(model.getRegistrytypedata().getDestino().toString()));
				}							
			}			
			
			respuesta = createNewRegistryInput();
			if(respuesta.getIdRegistryReport() == null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, getResourceMessage("no_report"), ""));
			}
			// Almacenamos la respuesta en el contexto
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
				

			// Almacenamos la respuesta en el contexto
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters", queryParameters);

			
			// Actualizamos la localización del registro
			if(model.getRegistrytypedata().getDestino()!=null) {
				HashMap kv= new HashMap<Object, Object>();
				kv.put(FieldNamesWeb.IDREGISTRY, respuesta.getIdRegistry());
				HashMap av= new HashMap<Object, Object>();
				av.put(FieldNamesWeb.IDSERVICEGROUP, model.getRegistrytypedata().getDestino());
				Map<String, Object> rUpdate=updateEntity("registry.ERegistryInput", kv, av);
			}
			
			
		} catch (Exception e) {
			// En caso de error, redirección a una página de error
			response = "error";
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters", queryParameters);
		}
		// En caso de exito, redirigir a una página de éxito.
		return response;
	}



	public Map<String, String> getDestinos() {
		return destinos;
	}

	public void setDestinos(LinkedHashMap<String, String> destinos) {
		this.destinos = destinos;
	}

	public LinkedHashMap<String, String> getDestinoses() {
		return destinoses;
	}

	public void setDestinoses(LinkedHashMap<String, String> destinoses) {
		this.destinoses = destinoses;
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
package com.cividas.customforms.webapp.controller.inc;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.cividas.customforms.webapp.common.model.inc.IncidenciasDynamicDataModel;
import com.cividas.customforms.webapp.common.model.inc.IncidenciasModel;
import com.cividas.customforms.webapp.controller.base.BaseController;

@ManagedBean(name="incidenciasController")
@ViewScoped
public class IncidenciasController extends BaseController {

	private static final long serialVersionUID = 3041301592924597211L;
	
	@ManagedProperty("#{incidenciasModel}")
	IncidenciasModel model;

	 private String title;
	
	private MapModel emptyModel;
	
    private double lat=1000;
    
    private double lng=1000;
	
	private boolean agreeWithPolicy = false;
	
	@Override
	public void init (){
		log.info("Inicializando el controlador: incidenciasController ..." );
	
		// Cargamos este modelo como el modelo base (esto es importante ya que el BaseController debe saber 
		// cual es el modelo con el que trabajará para invocar la llamada al servidor)
		this.baseModel = model;
		
		super.init();
		
		// Inicializando el modelo particular de datos
		model.setRegistrytypedata(new IncidenciasDynamicDataModel());
		
		 emptyModel = new DefaultMapModel();
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
			if(getLat()!=1000){
				model.getRegistrytypedata().setLatitud(new Float(getLat()));
				
			}
			if(getLng()!=1000){
				model.getRegistrytypedata().setLongitud(new Float(getLng()));
				
			}
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
	
	public IncidenciasModel getModel() {
		return model;
	}
	public void setModel(IncidenciasModel model) {
		this.model = model;
	}
	
	public boolean isAgreeWithPolicy() {
		return agreeWithPolicy;
	}
	public void setAgreeWithPolicy(boolean agreeWithPolicy) {
		this.agreeWithPolicy = agreeWithPolicy;
	}
	
	
	 public MapModel getEmptyModel() {
	        return emptyModel;
	    }
	      
	    public String getTitle() {
	        return title;
	    }
	  
	    public void setTitle(String title) {
	        this.title = title;
	    }
	    
	    
	    public double getLat() {
	        return lat;
	    }
	  
	    public void setLat(double lat) {
	        this.lat = lat;
	    }
	  
	    public double getLng() {
	        return lng;
	    }
	  
	    public void setLng(double lng) {
	        this.lng = lng;
	    }
	    public void addMarker() {
	        Marker marker = new Marker(new LatLng(lat, lng), title);
	        emptyModel.addOverlay(marker);
	          
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));
	    }
}

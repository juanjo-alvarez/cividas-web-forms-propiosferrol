package com.cividas.customforms.webapp.controller.quejsugmultiple;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.cividas.customforms.webapp.common.exceptions.CividasQueryDataException;
import com.cividas.customforms.webapp.common.model.quejsugmultiple.QuejasSugerenciasMultipleDynamicDataModel;
import com.cividas.customforms.webapp.common.model.quejsugmultiple.QuejasSugerenciasMultipleModel;
import com.cividas.customforms.webapp.common.model.restresponse.RestResponseModel;
import com.cividas.customforms.webapp.controller.base.BaseController;
import com.cividas.customforms.webapp.utils.CollectionsUtils;
import com.cividas.customforms.webapp.utils.MastersUtils;

@ManagedBean(name="quejasSugerenciasMultipleController")
@ViewScoped
public class QuejasSugerenciasMultipleController extends BaseController {

	private static final long serialVersionUID = 3041301592924597211L;
	
	@ManagedProperty("#{quejasSugerenciasMultipleModel}")
	QuejasSugerenciasMultipleModel model;


	private Map<String, String> tiposQuejasSugerencias = new HashMap<String, String>();
	
	private boolean agreeWithPolicy = false;
	
	@Override
	public void init (){
		log.info("Inicializando el controlador: QuejasSugerenciasMultipleController ..." );
	
		// Cargamos este modelo como el modelo base (esto es importante ya que el BaseController debe saber 
		// cual es el modelo con el que trabajar� para invocar la llamada al servidor)
		this.baseModel = model;
		
		super.init();
		
		// Inicializando el modelo particular de datos
		model.setRegistrytypedata(new QuejasSugerenciasMultipleDynamicDataModel());
		model.setSubject("Sede electr�nica - Quejas y sugerencias M�LTIPLES REGISTROS");
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
		long time=0;
		try{
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

			int numeroregistros=1;
			if (((QuejasSugerenciasMultipleModel) this.baseModel).getRegistrytypedata()!=null && ((QuejasSugerenciasMultipleModel) this.baseModel).getRegistrytypedata() instanceof QuejasSugerenciasMultipleDynamicDataModel ){
				if(((QuejasSugerenciasMultipleModel) this.baseModel).getRegistrytypedata().getNumregistros()!=null
						&& ((QuejasSugerenciasMultipleModel) this.baseModel).getRegistrytypedata().getNumregistros().intValue()>0){
					numeroregistros=((QuejasSugerenciasMultipleModel) this.baseModel).getRegistrytypedata().getNumregistros().intValue();
				}
			}
			
			// Hacemos tantos registros como se ha pedido desde la sede
			for(int i=1;i<=numeroregistros;i++){
				// Envio de la solicitud. Aqui se incluir� la l�gica propia del env�o de cada formulario, en caso de que exista
				// Alta del registro en Cividas
				long initialTime = System.currentTimeMillis();
				log.debug("Creaci�n registro web m�ltiple n�mero: "+i);
				try{
					respuesta = createNewRegistryInput();
					if(respuesta.getIdRegistryReport() == null){
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, getResourceMessage("no_report"), ""));
					}
					// Almacenamos la respuesta en el contexto para mostrar la p�gina de �xito al acabar con los datos del �ltimo registro
					FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
					FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters",queryParameters);
					
				}catch (Exception e) {
					// En caso de error, redirecci�n a una p�gina de error
					response = "error";
					log.debug("ERROR registro web m�ltiple n�mero: "+i);
					FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
					FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters",queryParameters);
				}
				long endingTime =  System.currentTimeMillis();
				long timeElapsed = endingTime - initialTime;
				time=time+timeElapsed;
				String parsedTime = parseTime(timeElapsed);
				log.debug("Final    registro web m�ltiple n�mero: "+i+" Tiempo registro web:"+parsedTime);
			}
			long averagetime=Math.floorDiv(time,numeroregistros);
			log.debug("Tiempo total:" +parseTime(time)+" para el registro web m�ltiple de: "+numeroregistros+" Tiempo medio:"+parseTime(averagetime));
		}catch (Exception e) {
			// En caso de error, redirecci�n a una p�gina de error
			response = "error";
		}
		// En caso de exito, redirigir a una p�gina de �xito.
		return response;
	}
	
	public QuejasSugerenciasMultipleModel getModel() {
		return model;
	}
	public void setModel(QuejasSugerenciasMultipleModel model) {
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
	
	protected String parseTime (long sourceTime) {
		long hours = TimeUnit.MILLISECONDS.toHours(sourceTime);
		sourceTime -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(sourceTime);
		sourceTime -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(sourceTime);
		long miliseconds = sourceTime - TimeUnit.SECONDS.toMillis(seconds);
		String parsedTime = null;
		parsedTime = String.format ("%d %02d:%02d:%03d", hours, minutes, seconds, miliseconds);
		return parsedTime;
	}
	
}

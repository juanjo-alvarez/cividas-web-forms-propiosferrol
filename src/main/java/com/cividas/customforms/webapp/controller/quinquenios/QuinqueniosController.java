package com.cividas.customforms.webapp.controller.quinquenios;

import com.cividas.customforms.webapp.common.exceptions.CividasQueryDataException;
import com.cividas.customforms.webapp.common.model.base.EntityData;
import com.cividas.customforms.webapp.common.model.base.JsonMultipleData;
import com.cividas.customforms.webapp.common.model.quinquenios.PeriodoAvaliacionBean;
import com.cividas.customforms.webapp.common.model.quinquenios.QuinqueniosDynamicDataModel;
import com.cividas.customforms.webapp.common.model.quinquenios.QuinqueniosModel;
import com.cividas.customforms.webapp.common.model.quinquenios.ServicioDocenteBean;
import com.cividas.customforms.webapp.controller.base.BaseControllerUvigo;
import com.cividas.customforms.webapp.utils.CollectionsUtils;
import com.cividas.customforms.webapp.utils.MastersUtils;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@ManagedBean(name="quinqueniosController")
@ViewScoped
public class QuinqueniosController extends BaseControllerUvigo {

	private static final long serialVersionUID = 3041301592924597211L;
	
	@ManagedProperty("#{quinqueniosModel}")
	QuinqueniosModel model;
			
	//Servicios docentes datatable & dialog
	private List<ServicioDocenteBean> serviciosDocentes;
	private ServicioDocenteBean selectedServicioDocente;
	private ServicioDocenteBean deletedServicioDocente;
	
	//Servizos docentes programas e bolsas datatable & dialog
	private List<ServicioDocenteBean> programasBolsas;
	private ServicioDocenteBean selectedProgramaBolsa;
	private ServicioDocenteBean deletedProgramaBolsa;
		
	//Servizos docentes outros datatable & dialog
	private List<ServicioDocenteBean> outrosServizosDocentes;
	private ServicioDocenteBean selectedOutroServizoDocente;
	private ServicioDocenteBean deletedOutroServizoDocente;
	
	
	//Periodos de avaliación datatable & dialog
	private List<PeriodoAvaliacionBean> periodosAval;
	private PeriodoAvaliacionBean selectedPeriodoAval;
	private PeriodoAvaliacionBean deletedPeriodoAval;
	
	
	private boolean servprestadosforauvigobool=false;
	
	private String renunciaa;
	
	private Map<String, String> departamentosList = new HashMap<String, String>();
	private Map<String, String> centrosList = new HashMap<String, String>();
	
	//combo ambitos
	private Map<String, String> ambitos = new HashMap<String, String>();
	

	
	@Override
	public void init (){
		log.info("Inicializando el controlador: QuinqueniosController ..." );
	
		// Cargamos este modelo como el modelo base (esto es importante ya que el BaseController debe saber 
		// cual es el modelo con el que trabajará para invocar la llamada al servidor)
		this.baseModel = model;
		super.init();
		
		// Inicializando el modelo particular de datos
		model.setRegistrytypedata(new QuinqueniosDynamicDataModel());
		serviciosDocentes = new ArrayList<ServicioDocenteBean>();
		selectedServicioDocente = new ServicioDocenteBean();
		programasBolsas = new ArrayList<ServicioDocenteBean>();
		selectedProgramaBolsa = new ServicioDocenteBean();
		outrosServizosDocentes = new ArrayList<ServicioDocenteBean>();
		selectedOutroServizoDocente = new ServicioDocenteBean();
		periodosAval = new ArrayList<PeriodoAvaliacionBean>();
		selectedPeriodoAval = new PeriodoAvaliacionBean();
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
		
		try{
			Map<String, Object> queryResult = queryCividasMaster("efpremextdoutoambitos", new HashMap<Object, Object>(), CollectionsUtils.buildList("codigoambito", "nombreambito"));
			this.ambitos = MastersUtils.parseMasterMap(queryResult, "codigoambito", "nombreambito");
		} catch (CividasQueryDataException cqe){
			log.error("Se ha producido un error al cargar el maestro de ámbitos: ", cqe);
		}
		
		
		try {
			Map<String, Object> departamentosList = queryCividasMaster("edepartamentosuvigo",new HashMap<Object, Object>(), new ArrayList<Object>());
			this.departamentosList = MastersUtils.parseMasterLinkedHashMap(departamentosList,"iddepartamento","nombredepartamento");
			
		} catch (CividasQueryDataException cqe) {
			log.error("Se ha producido un error al cargar el maestro de los tipos de solicitudes. A continuación se mostrará la traza del error",cqe);
		}
		
		log.info("Cargando maestros propios de la solicitud ..." );
	}
	
	
	public void onCampusChange(){
		Map<String, Object> centrosQueryResult;
		try {
			if(model.getRegistrytypedata().getCampus()!=null ){
				List listaCampos=new ArrayList<Object>();
				listaCampos.add("idcentro");
				listaCampos.add("nombrecentro");
				centrosQueryResult = queryCividasMaster("ecentrosuvigo", CollectionsUtils.buildMap("campus", model.getRegistrytypedata().getCampus()), new ArrayList<Object>());
				this.centrosList = MastersUtils.parseMasterLinkedHashMap(centrosQueryResult,"idcentro", "nombrecentro");
			}else{
				this.centrosList=new LinkedHashMap<String,String>();
			}
		} catch (CividasQueryDataException e) {
			log.error("Se ha producido un error al cargar el maestro de centros del campus. A continuación se mostrará la traza del error", e);
		}	
	}
	
	public void onAmbitoChange(AjaxBehaviorEvent event){
		Map<String, Object> departamentosQueryResult;
		try {
			if(model.getRegistrytypedata().getCodigoambito()!=null ){
				List listaCampos=new ArrayList<Object>();
				listaCampos.add("iddepartamento");
				listaCampos.add("nombredepartamento");
				departamentosQueryResult = queryCividasMaster("edepartamentosuvigo", CollectionsUtils.buildMap("codigoambito", model.getRegistrytypedata().getCodigoambito()), listaCampos);
				this.departamentosList = MastersUtils.parseMasterLinkedHashMap(departamentosQueryResult,"iddepartamento", "nombredepartamento");
			}else{
				this.departamentosList=new LinkedHashMap<String,String>();
			}
		} catch (CividasQueryDataException e) {
			log.error("Se ha producido un error al cargar el maestro de departamentos del ámbito. A continuación se mostrará la traza del error", e);
		}	
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
			if(getAccumulativePeriodoAval()<365*5) {
//				menos de 5 años
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("avaliationperiodlessthan5years"),getResourceMessage("avaliationperiodlessthan5years")));
				return "";
			}
			
			
			if (model.getRegistrytypedata().getSolicita7quinqbool() != null) {
				model.getRegistrytypedata().setSolicita7quinq(model.getRegistrytypedata().getSolicita7quinqbool());
			}
			
			//informo descripcion ambito para justificante			
			if (model.getRegistrytypedata().getCodigoambito() != null) {
				model.getRegistrytypedata().setNombreambito(ambitos.get(model.getRegistrytypedata().getCodigoambito().toString()));
			}
						
			//construyo el modelo para la insercion multiple de las listas
			List<EntityData> entityDataList = new ArrayList<EntityData>();
			
			formatDateToStrServicioDoc(this.getServiciosDocentes());
			entityDataList.add(new EntityData("efdinservdocentes", this.getServiciosDocentes()));
			
			
			formatDateToStrServicioDoc(this.getProgramasBolsas());
			entityDataList.add(new EntityData("efdinservdocentesprgbolsas", this.getProgramasBolsas()));
			
			formatDateToStrServicioDoc(this.getOutrosServizosDocentes());
			entityDataList.add(new EntityData("efdinservdocentesoutros", this.getOutrosServizosDocentes()));
			
			formatDateToStrPeriodoAval(this.getPeriodosAval());
			entityDataList.add(new EntityData("efdinperavaliac", this.getPeriodosAval()));


			if (!entityDataList.isEmpty()) {
				model.getRegistrytypedata().setJsonMultipleData(new JsonMultipleData(entityDataList));
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
			log.error("Exception sendRequest(): ", e);
			// En caso de error, redirección a una página de error
			response = "error";
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters",queryParameters);
		}
		//----------------------------------------------------------------------------------------
		// Ponemos como expediente padre del preexpediente al expediente de convocatoria asociado
		// a su tipo de expediente mediante el registro genérico TIPOEXPCONVREL
		// de ese año (solo sirve en convocatorias anuales)
		//-
		try {
			Map<String, Object> res=updateProcedureParent();
		} catch (ParseException | CividasQueryDataException e) {
			log.error("No se pudo asocociar el registro: {} al expediente padre: {} ", respuesta.getIdRegistry(), idprocedureparent);
			throw new RuntimeException(e);
		}
		// En caso de exito, redirigir a una página de éxito.
		return response;
	}
	
	private void formatDateToStrServicioDoc(List<ServicioDocenteBean> lista){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		for (ServicioDocenteBean servicioDocente : lista) {
			servicioDocente.setFechaini(sdf.format(servicioDocente.getFechainidate()));
			servicioDocente.setFechafin(sdf.format(servicioDocente.getFechafindate()));
		}
	}
	
	private void formatDateToStrPeriodoAval(List<PeriodoAvaliacionBean> lista){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		for (PeriodoAvaliacionBean periodoAval : lista) {
			periodoAval.setFechaini(sdf.format(periodoAval.getFechainidate()));
			periodoAval.setFechafin(sdf.format(periodoAval.getFechafindate()));
		}
	}
	
	public QuinqueniosModel getModel() {
		return model;
	}
	public void setModel(QuinqueniosModel model) {
		this.model = model;
	}

	// ==============================================================
	//              Servicios docentes
	// ==============================================================
	public List<ServicioDocenteBean> getServiciosDocentes() {
		return serviciosDocentes;
	}

	public void setServiciosDocentes(List<ServicioDocenteBean> serviciosDocentes) {
		this.serviciosDocentes = serviciosDocentes;
	}


	public ServicioDocenteBean getSelectedServicioDocente() {
		return selectedServicioDocente;
	}

	public void setSelectedServicioDocente(ServicioDocenteBean selectedServicioDocente) {
		this.selectedServicioDocente = selectedServicioDocente;
	}
	
	public ServicioDocenteBean getDeletedServicioDocente() {
		return deletedServicioDocente;
	}

	public void setDeletedServicioDocente(ServicioDocenteBean deletedServicioDocente) {		
		this.deletedServicioDocente = deletedServicioDocente;		
		this.serviciosDocentes.remove(this.deletedServicioDocente);
	}

	public void saveServicioDocente() {
		if(selectedServicioDocente.getFechainidate().compareTo(selectedServicioDocente.getFechafindate())>=1) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("invaliddaterange"),getResourceMessage("invaliddaterange")));
			return;
		}
    	PrimeFaces.current().executeScript("PF('servicioDocenteDialog').hide();sendHeight();");
    }
		
	public void addServicioDocente() {
		this.selectedServicioDocente = new ServicioDocenteBean();		
	}
	
	public void newServicioDocente() {
		if(selectedServicioDocente.getFechainidate().compareTo(selectedServicioDocente.getFechafindate())>=1) {
			FacesContext.getCurrentInstance().addMessage(null /*"dlgServicioDocente:servdoc_rexime"*/,new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("invaliddaterange"),getResourceMessage("invaliddaterange")));
			return;
		}
		selectedServicioDocente.setIdserviciodocente(Calendar.getInstance().getTimeInMillis());
		this.serviciosDocentes.add(this.selectedServicioDocente);		
		PrimeFaces.current().executeScript("PF('servicioDocenteDialog').hide();sendHeight();");
	}
	// ==============================================================
	
	
	// ==============================================================
	//              Servicios docentes programas e bolsas
	// ==============================================================

	public List<ServicioDocenteBean> getProgramasBolsas() {
		return programasBolsas;
	}

	public void setProgramasBolsas(List<ServicioDocenteBean> programasBolsas) {
		this.programasBolsas = programasBolsas;
	}


	public ServicioDocenteBean getSelectedProgramaBolsa() {
		return selectedProgramaBolsa;
	}

	public void setSelectedProgramaBolsa(ServicioDocenteBean selectedProgramaBolsa) {
		this.selectedProgramaBolsa = selectedProgramaBolsa;
	}
	
	public ServicioDocenteBean getDeletedProgramaBolsa() {
		return deletedProgramaBolsa;
	}

	public void setDeletedProgramaBolsa(ServicioDocenteBean deletedProgramaBolsa) {		
		this.deletedProgramaBolsa = deletedProgramaBolsa;		
		this.programasBolsas.remove(this.deletedProgramaBolsa);
	}

	public void saveProgramaBolsa() {
		if(selectedProgramaBolsa.getFechainidate().compareTo(selectedProgramaBolsa.getFechafindate())>=1) {
			FacesContext.getCurrentInstance().addMessage(null /*"dlgServicioDocente:servdoc_rexime"*/,new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("invaliddaterange"),getResourceMessage("invaliddaterange")));
			return;
		}
    	PrimeFaces.current().executeScript("PF('programaBolsaDialog').hide();sendHeight();");
    }
		
	public void addProgramaBolsa() {
		this.selectedProgramaBolsa = new ServicioDocenteBean();		
	}
	
	public void newProgramaBolsa() {
		if(selectedProgramaBolsa.getFechainidate().compareTo(selectedProgramaBolsa.getFechafindate())>=1) {
			FacesContext.getCurrentInstance().addMessage(null /*"dlgServicioDocente:servdoc_rexime"*/,new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("invaliddaterange"),getResourceMessage("invaliddaterange")));
			return;
		}
		selectedProgramaBolsa.setIdserviciodocente(Calendar.getInstance().getTimeInMillis());
		this.programasBolsas.add(this.selectedProgramaBolsa);		
		PrimeFaces.current().executeScript("PF('programaBolsaDialog').hide();sendHeight();");
	}
	// ==============================================================
	
	
	
	// ==============================================================
	//              Servicios docentes Outros
	// ==============================================================
	
	public List<ServicioDocenteBean> getOutrosServizosDocentes() {
		return outrosServizosDocentes;
	}

	public void setOutrosServizosDocentes(List<ServicioDocenteBean> outrosServizosDocentes) {
		this.outrosServizosDocentes = outrosServizosDocentes;
	}


	public ServicioDocenteBean getSelectedOutroServizoDocente() {
		return selectedOutroServizoDocente;
	}

	public void setSelectedOutroServizoDocente(ServicioDocenteBean selectedOutroServizoDocente) {
		this.selectedOutroServizoDocente = selectedOutroServizoDocente;
	}
	
	public ServicioDocenteBean getDeletedOutroServizoDocente() {
		return deletedOutroServizoDocente;
	}

	public void setDeletedOutroServizoDocente(ServicioDocenteBean deletedOutroServizoDocente) {		
		this.deletedOutroServizoDocente = deletedOutroServizoDocente;		
		this.outrosServizosDocentes.remove(this.deletedOutroServizoDocente);
	}

	public void saveOutroServizoDocente() {
		if(selectedOutroServizoDocente.getFechainidate().compareTo(selectedOutroServizoDocente.getFechafindate())>=1) {
			FacesContext.getCurrentInstance().addMessage(null /*"dlgServicioDocente:servdoc_rexime"*/,new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("invaliddaterange"),getResourceMessage("invaliddaterange")));
			return;
		}
    	PrimeFaces.current().executeScript("PF('outroServicioDocenteDialog').hide();sendHeight();");
    }
		
	public void addOutroServizoDocente() {
		this.selectedOutroServizoDocente = new ServicioDocenteBean();		
	}
	
	public void newOutroServizoDocente() {
		if(selectedOutroServizoDocente.getFechainidate().compareTo(selectedOutroServizoDocente.getFechafindate())>=1) {
			FacesContext.getCurrentInstance().addMessage(null ,new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("invaliddaterange"),getResourceMessage("invaliddaterange")));
			return;
		}
		selectedOutroServizoDocente.setIdserviciodocente(Calendar.getInstance().getTimeInMillis());
		this.outrosServizosDocentes.add(this.selectedOutroServizoDocente);		
		PrimeFaces.current().executeScript("PF('outroServicioDocenteDialog').hide();sendHeight();");
	}
	// ==============================================================
	
	
	// ==============================================================
	//              Periodos de avaliación
	// ==============================================================
	
	public List<PeriodoAvaliacionBean> getPeriodosAval() {
		return periodosAval;
	}

	public void setPeriodosAval(List<PeriodoAvaliacionBean> periodosAvaliacion) {
		this.periodosAval = periodosAvaliacion;
	}


	public PeriodoAvaliacionBean getSelectedPeriodoAval() {
		return selectedPeriodoAval;
	}

	public void setSelectedPeriodoAval(PeriodoAvaliacionBean selectedPeriodoAval) {
		this.selectedPeriodoAval = selectedPeriodoAval;
	}
	
	public PeriodoAvaliacionBean getDeletedPeriodoAval() {
		return deletedPeriodoAval;
	}

	public void setDeletedPeriodoAval(PeriodoAvaliacionBean deletedPeriodoAval) {		
		this.deletedPeriodoAval = deletedPeriodoAval;		
		this.periodosAval.remove(this.deletedPeriodoAval);
	}

	public void savePeriodoAval() {
		if(selectedPeriodoAval.getFechainidate().compareTo(selectedPeriodoAval.getFechafindate())>=1) {
			FacesContext.getCurrentInstance().addMessage(null /*"dlgServicioDocente:servdoc_rexime"*/,new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("invaliddaterange"),getResourceMessage("invaliddaterange")));
			return;
		}

    	PrimeFaces.current().executeScript("PF('periodoAvalDialog').hide();sendHeight();");
    }
		
	public void addPeriodoAval() {
		this.selectedPeriodoAval = new PeriodoAvaliacionBean();		
	}
	
	public void newPeriodoAval() {
		if(selectedPeriodoAval.getFechainidate().compareTo(selectedPeriodoAval.getFechafindate())>=1) {
			FacesContext.getCurrentInstance().addMessage(null /*"dlgServicioDocente:servdoc_rexime"*/,new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("invaliddaterange"),getResourceMessage("invaliddaterange")));
			return;
		}
		
		
		if(isAlreadyInsideInterval(selectedPeriodoAval)) {	
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("daterangeisoverlapped"),getResourceMessage("daterangeisoverlapped")));
			return;
		}
		
		
		
		selectedPeriodoAval.setIdperiodoaval(Calendar.getInstance().getTimeInMillis());
		this.periodosAval.add(this.selectedPeriodoAval);		
		PrimeFaces.current().executeScript("PF('periodoAvalDialog').hide();sendHeight();");
	}
	
	
	
	public long getAccumulativePeriodoAval() {
		
		long accumulativeValue=0;
		for(PeriodoAvaliacionBean periodoAux: periodosAval) {
			long periodDurationInMilliseconds=periodoAux.getFechafindate().getTime()-periodoAux.getFechainidate().getTime();
			accumulativeValue=accumulativeValue+(periodDurationInMilliseconds/(3600*24*1000)+1);
		}
		// in days
		return accumulativeValue;
	}

	public boolean isAlreadyInsideInterval(PeriodoAvaliacionBean newInterval) {
		for(PeriodoAvaliacionBean periodoAux: periodosAval) {
			if(newInterval.getFechainidate().getTime()>=periodoAux.getFechainidate().getTime() && newInterval.getFechainidate().getTime()<=periodoAux.getFechafindate().getTime()) {
				return true;
			}
			if(newInterval.getFechafindate().getTime()>=periodoAux.getFechainidate().getTime() && newInterval.getFechafindate().getTime()<=periodoAux.getFechafindate().getTime()) {
				return true;
			}
			
			if(periodoAux.getFechainidate().getTime()>=newInterval.getFechainidate().getTime() && periodoAux.getFechainidate().getTime()<=newInterval.getFechafindate().getTime()) {
				return true;
			}
			if(periodoAux.getFechafindate().getTime()>=newInterval.getFechainidate().getTime() && periodoAux.getFechafindate().getTime()<=newInterval.getFechafindate().getTime()) {
				return true;
			}
			
			
		}
		return false;
	}
	
	
	// ==============================================================

	public boolean isServprestadosforauvigobool() {
		return servprestadosforauvigobool;
	}

	public void setServprestadosforauvigobool(boolean servprestadosforauvigobool) {
		this.servprestadosforauvigobool = servprestadosforauvigobool;
	}
	
	
	
	protected String getResourceMessage(String key) {
		String message = key;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			i18n = fc.getApplication().evaluateExpressionGet(fc, "#{i18n}", ResourceBundle.class);
			message = i18n.getString(key);
		} catch (Exception e) {			
			message = i18n.getString("NO_VALIDATION_DONE");
		}
		return message;
	}

	public Map<String, String> getDepartamentosList() {
		return departamentosList;
	}

	public void setDepartamentosList(Map<String, String> departamentosList) {
		this.departamentosList = departamentosList;
	}

	public Map<String, String> getCentrosList() {
		return centrosList;
	}

	public void setCentrosList(Map<String, String> centrosList) {
		this.centrosList = centrosList;
	}

	public String getRenunciaa() {
		return renunciaa;
	}

	public void setRenunciaa(String renunciaa) {
		this.renunciaa = renunciaa;
	}

	public Map<String, String> getAmbitos() {
		return ambitos;
	}

	public void setAmbitos(Map<String, String> ambitos) {
		this.ambitos = ambitos;
	}
	
}
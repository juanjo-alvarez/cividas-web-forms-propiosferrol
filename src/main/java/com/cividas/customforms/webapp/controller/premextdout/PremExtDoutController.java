package com.cividas.customforms.webapp.controller.premextdout;

import com.cividas.customforms.webapp.common.exceptions.CividasQueryDataException;
import com.cividas.customforms.webapp.common.model.base.EntityData;
import com.cividas.customforms.webapp.common.model.base.JsonMultipleData;
import com.cividas.customforms.webapp.common.model.premextdout.ContribucionBean;
import com.cividas.customforms.webapp.common.model.premextdout.EstanciaBean;
import com.cividas.customforms.webapp.common.model.premextdout.PremExtDoutDynamicDataModel;
import com.cividas.customforms.webapp.common.model.premextdout.PremExtDoutModel;
import com.cividas.customforms.webapp.controller.base.BaseControllerUvigo;
import com.cividas.customforms.webapp.utils.CollectionsUtils;
import com.cividas.customforms.webapp.utils.MastersUtils;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name="premExtDoutController")
@ViewScoped
public class PremExtDoutController extends BaseControllerUvigo {

	private static final long serialVersionUID = 3041301592924597211L;
	
	@ManagedProperty("#{premExtDoutModel}")
	PremExtDoutModel model;
			
	//contribucions datatable & dialog
	private List<ContribucionBean> contribuciones;
	private ContribucionBean selectedContribucion;
	private ContribucionBean deletedContribucion;
	
	//Outras estancias datatable & dialog
	private List<EstanciaBean> outEstancias;
	private EstanciaBean selectedOutEstancia;
	private EstanciaBean deletedOutEstancia;
		
	//Estancias datatable & dialog
	private List<EstanciaBean> estancias;
	private EstanciaBean selectedEstancia;
	private EstanciaBean deletedEstancia;
	
	//combo ambitos
	private Map<String, String> ambitos = new HashMap<String, String>();
	
	//combo cneai
	private Map<String, String> cneai = new HashMap<String, String>();
	
	private boolean candidate = true; 
	
	@Override
	public void init (){
		log.info("Inicializando el controlador: PremExtDoutController ..." );
	
		// Cargamos este modelo como el modelo base (esto es importante ya que el BaseController debe saber 
		// cual es el modelo con el que trabajará para invocar la llamada al servidor)
		this.baseModel = model;
		
		super.init();
		
		// Inicializando el modelo particular de datos
		model.setRegistrytypedata(new PremExtDoutDynamicDataModel());
		contribuciones = new ArrayList<ContribucionBean>();
		selectedContribucion = new ContribucionBean();
		estancias = new ArrayList<EstanciaBean>();
		selectedEstancia = new EstanciaBean();
		outEstancias = new ArrayList<EstanciaBean>();
		selectedOutEstancia = new EstanciaBean();
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
		
		log.info("Cargando maestros propios de la solicitud ..." );
		
		Map<String, Object> candidatosData = null;
		try {
			
			String identificationNumber=model.getInteresteddata()[0].getIdentificationnumber();
			candidatosData = queryCividasMaster("efmcandidatopremdoutora", CollectionsUtils.buildMap("dni", identificationNumber), new ArrayList<Object>());
			if (candidatosData == null || candidatosData != null && candidatosData.isEmpty()) {
				candidate = false;
				// Por si llegan candidatos logueados con sistema de autenticación europeo de Cl@ve hacemos otro intento quitando las /
				if(identificationNumber.lastIndexOf("/")!=-1) {
					identificationNumber=identificationNumber.substring(identificationNumber.lastIndexOf("/")+1);
					candidatosData = queryCividasMaster("efmcandidatopremdoutora", CollectionsUtils.buildMap("dni", identificationNumber), new ArrayList<Object>());

					// comprobacion tras segundo intento
					if (candidatosData != null && !candidatosData.isEmpty()) {
					   candidate= true;
					}
				}
				
			}
			
			
		} catch (CividasQueryDataException e){
			log.error("Se ha producido un error al cargar datos de los candidatos entidad efmcandidatopremdoutora: ", e);
		}
				
		if (candidate) {			
			try{
				Map<String, Object> queryResult = queryCividasMaster("efpremextdoutoambitos", new HashMap<Object, Object>(), CollectionsUtils.buildList("idambito", "nombreambito"));
				this.ambitos = MastersUtils.parseMasterMap(queryResult, "idambito", "nombreambito");
			} catch (CividasQueryDataException cqe){
				log.error("Se ha producido un error al cargar el maestro de ámbitos: ", cqe);
			}
			
			List<String> codigocneaiList = null;
			List<Integer> idcneaiList = null;
			try{
				Map<String, Object> queryResult = queryCividasMaster("efpremextdoucneai", new HashMap<Object, Object>(), new ArrayList<Object>());
				idcneaiList = (List<Integer>) queryResult.get("idcneai");
				List<String> nombrecneaiList = (List<String>) queryResult.get("nombrecneai");
				codigocneaiList = (List<String>) queryResult.get("codigocneai");
				Map<String, String> cneaiMap = null;
				if (!idcneaiList.isEmpty() && !codigocneaiList.isEmpty() && !nombrecneaiList.isEmpty()) {
					cneaiMap = new HashMap<String, String>();
					for (int i = 0; i < idcneaiList.size(); i++) {
						cneaiMap.put(String.valueOf(idcneaiList.get(i)), codigocneaiList.get(i) + " - " + nombrecneaiList.get(i));
					}
				}			
				this.cneai = cneaiMap;
			} catch (CividasQueryDataException cqe){
				log.error("Se ha producido un error al cargar el maestro de CNEAI: ", cqe);
			}
			
			try {
				//cargamos ambito 
				List<String> ambitoList = (List<String>) candidatosData.get("ambito");
				if (!ambitoList.isEmpty()) {					
					String ambito = ambitoList.get(0).trim().toUpperCase();
					for (String key : this.ambitos.keySet()) {
						if (ambito.equals(this.ambitos.get(key).trim().toUpperCase())) {
							model.getRegistrytypedata().setIdambito(Integer.valueOf(key));
							break;
						}
					}
				}
				//cargamos cneai
				if (codigocneaiList != null && idcneaiList != null) {
					List<String> cneaiList = (List<String>) candidatosData.get("codigocneai");
					if (!cneaiList.isEmpty()) {					
						String cneaicode = cneaiList.get(0).trim().toUpperCase();
						for (int i = 0; i < codigocneaiList.size(); i++) {
							if (cneaicode.equals(codigocneaiList.get(i).trim().toUpperCase())) {
								model.getRegistrytypedata().setIdcneai(idcneaiList.get(i));
								break;
							}
						}
					}
				}
				
				List<String> dataLecturaList = (List<String>) candidatosData.get("datalectura");
				if (!dataLecturaList.isEmpty()) {
					model.getRegistrytypedata().setFechadeftesis(dataLecturaList.get(0));
				}
				List<String> tituloList = (List<String>) candidatosData.get("titulotese");
				if (!tituloList.isEmpty()) {
					model.getRegistrytypedata().setTitulotesis(tituloList.get(0));
				}
				List<String> direccion1List = (List<String>) candidatosData.get("direccion1");
				List<String> direccion2List = (List<String>) candidatosData.get("direccion2");
				List<String> direccion3List = (List<String>) candidatosData.get("direccion3");
				if (!direccion1List.isEmpty()) {
					StringBuilder stb = new StringBuilder();
					String dir1 = direccion1List.get(0);
					String dir2 = direccion2List.get(0);
					String dir3 = direccion3List.get(0);
					if (dir1 != null) {
						stb.append(dir1.trim());
					}
					if (dir2 != null) {
						stb.append(", ");
						stb.append(dir2.trim());
					}
					if (dir3 != null) {
						stb.append(", ");
						stb.append(dir3.trim());
					}				
					model.getRegistrytypedata().setDirigida(stb.toString());
				}
				List<String> mencionIntList = (List<String>) candidatosData.get("doutorinternacional");
				if (!mencionIntList.isEmpty()) {
					// Si es Non desmarcamos el check y en cualquier otro caso lo marcamos
					Boolean mencionbol=parseStringToBoolean(mencionIntList.get(0));
					model.getRegistrytypedata().setMencionintbool(mencionbol);
					if(mencionbol) {
						model.getRegistrytypedata().setMenciontext(mencionIntList.get(0));
					}
				}
			
			} catch (Exception ex){
				log.error("Se ha producido un error cargando los datos del interesado: ", ex);
			}
		}
	}
	
	
	@Override
	public String sendRequest() {
		String response = "success";
		try{
			if("".equalsIgnoreCase(getMandatoryFilesUploaded())) {
				log.error("No se adjuntaron todos los documentos.");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("mandatorydocumentsmiss"), ""));
				return "error";
			}
			//informo descripcion ambito y cneai para justificante			
			if (model.getRegistrytypedata().getIdambito() != null) {
				model.getRegistrytypedata().setNombreambito(ambitos.get(model.getRegistrytypedata().getIdambito().toString()));
			}
			if (model.getRegistrytypedata().getIdcneai() != null) {
				model.getRegistrytypedata().setNombrecneai(cneai.get(model.getRegistrytypedata().getIdcneai().toString()));
			}
			
			// Envio de la solicitud. Aqui se incluirá la lógica propia del envío de cada formulario, en caso de que exista
			if (model.getRegistrytypedata().getMencionintbool() != null) {
				model.getRegistrytypedata().setMencionint(model.getRegistrytypedata().getMencionintbool());
				
				//si no hemos marcado mencion internacional eliminamos el contenido de las estancias
				if (!model.getRegistrytypedata().getMencionintbool()) {
					this.getEstancias().clear();
				}
			}
						
			//construyo el modelo para la insercion multiple de las listas
			List<EntityData> entityDataList = new ArrayList<EntityData>();
			entityDataList.add(new EntityData("efpremextdoucont", this.getContribuciones()));
			formatDateToStr(this.getEstancias());
			entityDataList.add(new EntityData("efpremextdouest", this.getEstancias()));
			formatDateToStr(this.getOutEstancias());
			entityDataList.add(new EntityData("efpremextdououtest", this.getOutEstancias()));
				
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
	
	private void formatDateToStr(List<EstanciaBean> lista){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		for (EstanciaBean estancia : lista) {
			estancia.setFechaini(sdf.format(estancia.getFechainidate()));
			estancia.setFechafin(sdf.format(estancia.getFechafindate()));
		}
	}
	
	public PremExtDoutModel getModel() {
		return model;
	}
	public void setModel(PremExtDoutModel model) {
		this.model = model;
	}

	public Map<String, String> getAmbitos() {
		return ambitos;
	}

	public void setAmbitos(Map<String, String> ambitos) {
		this.ambitos = ambitos;
	}

	public Map<String, String> getCneai() {
		return cneai;
	}

	public void setCneai(Map<String, String> cneai) {
		this.cneai = cneai;
	}

	public List<ContribucionBean> getContribuciones() {
		return contribuciones;
	}

	public void setContribuciones(List<ContribucionBean> contribuciones) {
		this.contribuciones = contribuciones;
	}

	public ContribucionBean getSelectedContribucion() {
		return selectedContribucion;
	}

	public void setSelectedContribucion(ContribucionBean selectedContribucion) {
		this.selectedContribucion = selectedContribucion;
	}
	
	public ContribucionBean getDeletedContribucion() {
		return deletedContribucion;
	}

	public void setDeletedContribucion(ContribucionBean deletedContribucion) {		
		this.deletedContribucion = deletedContribucion;		
		this.contribuciones.remove(this.deletedContribucion);
	}

	public void saveContrib() {
    	PrimeFaces.current().executeScript("PF('contribucionDialog').hide();sendHeight();");
    }
		
	public void addContrib() {
		this.selectedContribucion = new ContribucionBean();		
	}
	
	public void newContrib() {		
		this.contribuciones.add(this.selectedContribucion);		
		PrimeFaces.current().executeScript("PF('contribucionDialog').hide();sendHeight();");
	}

	public List<EstanciaBean> getOutEstancias() {
		return outEstancias;
	}

	public void setOutEstancias(List<EstanciaBean> outEstancias) {
		this.outEstancias = outEstancias;
	}

	public EstanciaBean getSelectedOutEstancia() {
		return selectedOutEstancia;
	}

	public void setSelectedOutEstancia(EstanciaBean selectedOutEstancia) {
		this.selectedOutEstancia = selectedOutEstancia;
	}

	public EstanciaBean getDeletedOutEstancia() {
		return deletedOutEstancia;
	}

	public void setDeletedOutEstancia(EstanciaBean deletedOutEstancia) {
		this.deletedOutEstancia = deletedOutEstancia;	
		this.outEstancias.remove(this.deletedOutEstancia);
	}

	public List<EstanciaBean> getEstancias() {
		return estancias;
	}

	public void setEstancias(List<EstanciaBean> estancias) {
		this.estancias = estancias;
	}

	public EstanciaBean getSelectedEstancia() {
		return selectedEstancia;
	}

	public void setSelectedEstancia(EstanciaBean selectedEstancia) {
		this.selectedEstancia = selectedEstancia;
	}

	public EstanciaBean getDeletedEstancia() {
		return deletedEstancia;
	}

	public void setDeletedEstancia(EstanciaBean deletedEstancia) {
		this.deletedEstancia = deletedEstancia;
		this.estancias.remove(this.deletedEstancia);
	}
	
	public void saveEstancia() {
    	PrimeFaces.current().executeScript("PF('estanciaDialog').hide();sendHeight();");
    }
		
	public void addEstancia() {
		this.selectedEstancia = new EstanciaBean();		
	}
	
	public void newEstancia() {		
		this.estancias.add(this.selectedEstancia);		
		PrimeFaces.current().executeScript("PF('estanciaDialog').hide();sendHeight();");
	}
	
	public void saveOutEstancia() {
    	PrimeFaces.current().executeScript("PF('outEstanciaDialog').hide();sendHeight();");
    }
		
	public void addOutEstancia() {
		this.selectedOutEstancia = new EstanciaBean();		
	}
	
	public void newOutEstancia() {		
		this.outEstancias.add(this.selectedOutEstancia);		
		PrimeFaces.current().executeScript("PF('outEstanciaDialog').hide();sendHeight();");
	}
	
	private Boolean parseStringToBoolean (String value) {
		if("NON".equalsIgnoreCase(value) || "NO".equalsIgnoreCase(value)) {
			return false;
		}
		return true;
	}

	public boolean isCandidate() {
		return candidate;
	}

	public void setCandidate(boolean candidate) {
		this.candidate = candidate;
	}
	
}
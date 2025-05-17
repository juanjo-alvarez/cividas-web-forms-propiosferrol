package com.cividas.customforms.webapp.controller.bolsasform;

import com.cividas.customforms.webapp.common.exceptions.CividasQueryDataException;
import com.cividas.customforms.webapp.common.model.base.EntityData;
import com.cividas.customforms.webapp.common.model.base.JsonMultipleData;
import com.cividas.customforms.webapp.common.model.bolsasform.PreferenciaBolsaBean;
import com.cividas.customforms.webapp.common.model.bolsasform.SolBolsaFormacionDynamicDataModel;
import com.cividas.customforms.webapp.common.model.bolsasform.SolBolsaFormacionModel;
import com.cividas.customforms.webapp.controller.base.BaseControllerUvigo;
import com.cividas.customforms.webapp.utils.MastersUtils;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;

@ManagedBean(name = "solBolsaFormacionController")
@ViewScoped
public class SolBolsaFormacionController extends BaseControllerUvigo {

	private static final long serialVersionUID = 2148438200796166524L;

	@ManagedProperty("#{solBolsaFormacionModel}")
	SolBolsaFormacionModel model;
	
	private boolean oppositionverificationss;
	private boolean consenttribute;
	private boolean datatrue;
	private boolean termscall;
	private boolean maintainenrollment;
	private boolean obligationtax;

	private boolean preferencesHasValue;
	
	//Preferencias bolsa datatable & dialog
	private List<PreferenciaBolsaBean> preferenciasBolsas;
	private PreferenciaBolsaBean selectedPreferenciaBolsa;
	private PreferenciaBolsaBean deletedPreferenciaBolsa;
	
	private Map<String, String> bolsaFormacionList = new HashMap<String, String>();


	
	private Number idRegistry;
	
	private ResourceBundle i18n;

	@Override
	public void init() {
		log.info("Inicializando el controlador: SolBolsaFormacionController ...");

		// Cargamos este modelo como el modelo base (esto es importante ya que el
		// BaseController debe saber
		// cual es el modelo con el que trabajará para invocar la llamada al servidor)
		this.baseModel = model;

		super.init();

		// Inicializando el modelo particular de datos
		model.setRegistrytypedata(new SolBolsaFormacionDynamicDataModel());
		model.setSubject("Sede electrónica - Solicitude bolsa de formación");
		preferenciasBolsas = new ArrayList<PreferenciaBolsaBean>();
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
		log.info("Cargando maestros propios de la solicitud de excelencia");
		String localeLanguage = getLanguageBean().getLang();
		

		// Consultamos la lista de bolsas para esa convocatoria
		try {
			HashMap hkeys=new HashMap<Object, Object>();
			if(idprocedureparent!=null) {
				hkeys.put("idprocedure", idprocedureparent);
			}
			Map<String, Object> helpRequestTypes = queryCividasMaster("efbolsasformacion",hkeys, new ArrayList<Object>());
			this.bolsaFormacionList = MastersUtils.parseMasterMap(helpRequestTypes, "codigobolsa","nombrebolsa");

		} catch (CividasQueryDataException cqe) {
			log.error(
					"Se ha producido un error al cargar el maestro de los tipos de solicitudes. A continuación se mostrará la traza del error",
					cqe);
		}

	}

	
	
	
	
	
	
	
	
	@Override
	public String sendRequest() {
		// Envio de la solicitud. Aqui se incluirá la lógica propia del envío de cada
		// formulario, en caso de que exista

		String response = "success";
		
		boolean firstregistry = checkInterestedRegistry();
		
		
		try {
			
			if("".equalsIgnoreCase(getMandatoryFilesUploaded())) {
				log.error("No se adjuntaron todos los documentos.");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("mandatorydocumentsmiss"), ""));
				return "error";
			}
			
			if (firstregistry) {
				if(getdatatruebool()) {
					model.getRegistrytypedata().setdatatrue(new Integer(1));
				} else {
					model.getRegistrytypedata().setdatatrue(new Integer(0));
				}

				if(gettermscallbool()) {
					model.getRegistrytypedata().settermscall(new Integer(1));
				} else {
					model.getRegistrytypedata().settermscall(new Integer(0));
				}

				if(getmaintainenrollmentbool()) {
					model.getRegistrytypedata().setmaintainenrollment(new Integer(1));
				} else {
					model.getRegistrytypedata().setmaintainenrollment(new Integer(0));
				}

				if(getobligationtaxbool()) {
					model.getRegistrytypedata().setobligationtax(new Integer(1));
				} else {
					model.getRegistrytypedata().setobligationtax(new Integer(0));
				}


				//construyo el modelo para la insercion multiple de las listas
				List<EntityData> entityDataList = new ArrayList<EntityData>();
				entityDataList.add(new EntityData("efdinpreferenciasbolsas", this.getPreferenciasBolsas()));

				if (!entityDataList.isEmpty()) {
					model.getRegistrytypedata().setJsonMultipleData(new JsonMultipleData(entityDataList));
				}


				// Alta del registro en Cividas
				respuesta = createNewRegistryInput();
				// Almacenamos la respuesta en el contexto
				FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);


				// Almacenamos la respuesta en el contexto
				FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters", queryParameters);
			} else {
				//Redirigimos a una pagina informando de que ya hay un registro existente para esa convocatoria
				response = "onlyOneRegistryError";
				respuesta = requestRegistryTypeCodeError(this.idRegistry);
				// Almacenamos la respuesta en el contexto
				FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
				log.info("El interesado ya tiene una solicitud para este tipo de registro");
				
				
			}
			if(respuesta.getIdRegistryReport() == null && firstregistry){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, getResourceMessage("no_report"), ""));
			}
			// Almacenamos la respuesta en el contexto
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters", queryParameters);
			

		} catch (Exception e) {
			// En caso de error, redirección a una página de error
			response = "error";
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters", queryParameters);
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

	
	
	
	
	
	
	public SolBolsaFormacionModel getModel() {
		return model;
	}

	public void setModel(SolBolsaFormacionModel model) {
		this.model = model;
	}
	
	public boolean getoppositionverificationssbool() {
		return oppositionverificationss;
	}
	
	public void setoppositionverificationssbool(boolean oppositionverificationss) {
		this.oppositionverificationss = oppositionverificationss;
	}
	
	public void setconsenttributebool(boolean consenttribute) {
		this.consenttribute = consenttribute;
	}
	public boolean getconsenttributebool() {
		return consenttribute;
	}
	
	//Declaracion responsable
	
	public boolean getdatatruebool() {
		return datatrue;
	}
	
	public void setdatatruebool(boolean datatrue) {
		this.datatrue = datatrue;
	}
	
	public boolean gettermscallbool() {
		return termscall;
	}
	
	public void settermscallbool(boolean termscall) {
		this.termscall = termscall;
	}

	public boolean getmaintainenrollmentbool() {
		return maintainenrollment;
	}
	
	public void setmaintainenrollmentbool(boolean maintainenrollment) {
		this.maintainenrollment = maintainenrollment;
	}
	
	public boolean getobligationtaxbool() {
		return obligationtax;
	}
	
	public void setobligationtaxbool(boolean obligationtax) {
		this.obligationtax = obligationtax;
	}
	
	public boolean isUserLogged() {

		return (queryParameters != null && queryParameters.get("user") != null) ? true : false;
	}

	public void showDlg(String dlgName) {
		PrimeFaces.current().executeScript(dlgName + ".show()");
	}


	/**
	 * Comprueba si se ha adjuntado un documento que justifique el motivo
	 * de oposicion a la comprobación de obligaciones con la SS
	 */
	public void validateAttachment() {
		if(getoppositionverificationssbool()) {
			if(this.uploadedFiles.isEmpty()) {
				showError("mandatory_oppositionmotive_attach");
				FacesContext.getCurrentInstance().validationFailed();
			}
		}
	}
	
	
	
		// ==============================================================
		//              Lista preferencia de bolsas
		// ==============================================================
	
	public boolean isPreferencesHasValue() {
		if(preferenciasBolsas==null || preferenciasBolsas.size()==0) {
			this.preferencesHasValue=false;
		}else {
			this.preferencesHasValue=true;
		}
		return preferencesHasValue;
	}
	
	
	public void setPreferencesHasValue(boolean preferencesHasValue) {
		this.preferencesHasValue = preferencesHasValue;
	}
	
		public List<PreferenciaBolsaBean> getPreferenciasBolsas() {
			return preferenciasBolsas;
		}

		public void setPreferenciasBolsas(List<PreferenciaBolsaBean> preferenciasBolsas) {
			this.preferenciasBolsas = preferenciasBolsas;
		}


		public PreferenciaBolsaBean getSelectedPreferenciaBolsa() {
			return selectedPreferenciaBolsa;
		}

		public void setSelectedPreferenciaBolsa(PreferenciaBolsaBean selectedPreferenciaBolsa) {
			this.selectedPreferenciaBolsa = selectedPreferenciaBolsa;
		}
		
		public PreferenciaBolsaBean getDeletedPreferenciaBolsa() {
			return deletedPreferenciaBolsa;
		}

		public void setDeletedPreferenciaBolsa(PreferenciaBolsaBean deletedPreferenciaBolsa) {		
			this.deletedPreferenciaBolsa = deletedPreferenciaBolsa;
			
			// reordenamos los que tienen orden superior
			long deletedorder=deletedPreferenciaBolsa.getIdpreferencia();
			for (PreferenciaBolsaBean preferencia : preferenciasBolsas) {
				if(preferencia.getIdpreferencia()>deletedorder) {
					preferencia.setIdpreferencia(preferencia.getIdpreferencia()-1);
				}
			}
			
			this.preferenciasBolsas.remove(this.deletedPreferenciaBolsa);
			
			
			
		}

		public void savePreferenciaBolsa() {
			if(selectedPreferenciaBolsa.getCodbolsa()!=null && bolsaFormacionList.get(selectedPreferenciaBolsa.getCodbolsa())!=null) {
				selectedPreferenciaBolsa.setDescbolsa(bolsaFormacionList.get(selectedPreferenciaBolsa.getCodbolsa()));
			}
	    	PrimeFaces.current().executeScript("PF('preferenciaBolsaDialog').hide();sendHeight();");
	    }
			
		public void addPreferenciaBolsa() {
			this.selectedPreferenciaBolsa = new PreferenciaBolsaBean();		
		}
		
		public void newPreferenciaBolsa() {
			selectedPreferenciaBolsa.setIdpreferencia(Long.valueOf(preferenciasBolsas.size()+1));
			
			if(selectedPreferenciaBolsa.getCodbolsa()!=null && bolsaFormacionList.get(selectedPreferenciaBolsa.getCodbolsa())!=null) {
				selectedPreferenciaBolsa.setDescbolsa(bolsaFormacionList.get(selectedPreferenciaBolsa.getCodbolsa()));
			}
			
			
			
			
			if(isAlreadyInside(selectedPreferenciaBolsa)) {	
				selectedPreferenciaBolsa.setCodbolsa(null);
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("bolsaxaincluida"),getResourceMessage("bolsaxaincluida")));
				return;
			}
			
			
			
			
			this.preferenciasBolsas.add(this.selectedPreferenciaBolsa);		
			PrimeFaces.current().executeScript("PF('preferenciaBolsaDialog').hide();sendHeight();");
		}
		// ==============================================================

		public Map<String, String> getBolsaFormacionList() {
			return bolsaFormacionList;
		}

		public void setBolsaFormacionList(Map<String, String> bolsaFormacionList) {
			this.bolsaFormacionList = bolsaFormacionList;
		}

		
		
		
		
		/**
		 * Comprueba si el interesado ya ha realizado alguna solicitud para esa convocatoria
		 * 
		 * @return true si es el primer registro en el año
		 */
		public boolean checkInterestedRegistry() {
			boolean firstRegistry = true;
			
			
			// DNI del interesado y id expediente convocatoria
			HashMap<Object, Object> filtro = new HashMap<Object, Object>();
			filtro.put("applicantidentification", model.getInteresteddata()[0].getIdentificationnumber());
			filtro.put("idprocedureparent", idprocedureparent);
			
			List listaCamposprocedures=new ArrayList<Object>();
			listaCamposprocedures.add("idprocedure");
			listaCamposprocedures.add("idprocedureparent");
			listaCamposprocedures.add("idregistryfirst");
			listaCamposprocedures.add("applicantidentification");
			
			try {
				if (model.getInteresteddata()[0].getIdentificationnumber() != null && idprocedureparent != null) {
					// Consultamos todos los expedientes del interesado con el idprocedureparent igual al de la convocatoria en la que estamos realizando la solicitud
					Map<String, Object> individualProceduresQueryResult = queryCividasMaster("procedures.EProcedures",filtro, listaCamposprocedures);
					
					if (!individualProceduresQueryResult.isEmpty()) {
						// si devuelve resultados es que ya hay un registro realizado
						firstRegistry = false;
						
						Map<String, String> listProcedures = MastersUtils.parseMasterMap(individualProceduresQueryResult, "idregistryfirst","idprocedureparent");

						for(Entry<String, String> idsAndCodes : listProcedures.entrySet()) {
//							String procedurecode = String.valueOf(idsAndCodes.getValue());
							
								//Si ya existe un expediente de convocatoria este año recogemos el id
								try{
									idRegistry = NumberFormat.getInstance().parse(idsAndCodes.getKey());
								} catch(ParseException e) {
									log.error("Se ha producido un error. A continuación se mostrará la traza del error", e);
								}
						}
						

					}
				}
			} catch(CividasQueryDataException cqe) {
				log.error("Se ha producido un error al consultar las solicitudes del interesado. A continuación se mostrará la traza del error", cqe);
			}
			
			return firstRegistry;
		}


	public boolean isAlreadyInside(PreferenciaBolsaBean newbolsa) {
		for(PreferenciaBolsaBean bolsaAux: preferenciasBolsas) {
			if(bolsaAux.getCodbolsa().equalsIgnoreCase(newbolsa.getCodbolsa())) {
				return true;
			}
		}
		return false;
	}


}

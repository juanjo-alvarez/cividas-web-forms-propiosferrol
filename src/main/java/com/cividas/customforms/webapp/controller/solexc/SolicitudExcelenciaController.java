package com.cividas.customforms.webapp.controller.solexc;

import com.cividas.customforms.webapp.common.exceptions.CividasQueryDataException;
import com.cividas.customforms.webapp.common.model.solexc.SolExcDynamicDataModel;
import com.cividas.customforms.webapp.common.model.solexc.SolExcModel;
import com.cividas.customforms.webapp.controller.base.BaseControllerUvigo;
import com.cividas.customforms.webapp.utils.CollectionsUtils;
import com.cividas.customforms.webapp.utils.MastersUtils;
import com.cividas.web.common.FieldNamesWeb;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;

@ManagedBean(name = "solicitudExcelenciaController")
@ViewScoped
public class SolicitudExcelenciaController extends BaseControllerUvigo {

	private static final long serialVersionUID = 2148438200796166524L;

	@ManagedProperty("#{solicitudExcelenciaModel}")
	SolExcModel model;
	
	private boolean oppositionverificationss;
	private boolean consenttribute;
	private boolean datatrue;
	private boolean termscall;
	private boolean maintainenrollment;
	private boolean obligationtax;
	private Number idRegistry;


	private Map<String, String> helpRequestTypes = new HashMap<String, String>();
	private Map<String, String> idRegistryAndDate = new HashMap<String,String>();

	private ResourceBundle i18n;

	@Override
	public void init() {
		log.info("Inicializando el controlador: SolicitudExcelenciaController ...");

		// Cargamos este modelo como el modelo base (esto es importante ya que el
		// BaseController debe saber
		// cual es el modelo con el que trabajará para invocar la llamada al servidor)
		this.baseModel = model;

		super.init();

		// Inicializando el modelo particular de datos
		model.setRegistrytypedata(new SolExcDynamicDataModel());
		model.setSubject("Sede electrónica - Solicitude de premio de excelencia");
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
		try {
			if (localeLanguage.equals(FieldNamesWeb.LOCALE_LANGUAGE_LG2)) {
				Map<String, Object> helpRequestTypes = queryCividasMaster("ehelpreqtypes",
						new HashMap<Object, Object>(), new ArrayList<Object>());
				this.helpRequestTypes = MastersUtils.parseMasterMap(helpRequestTypes, "idhelprequest",
						"helprequestdesc_gl");
			} else {
				Map<String, Object> helpRequestTypes = queryCividasMaster("ehelpreqtypes",
						new HashMap<Object, Object>(),
						CollectionsUtils.buildList("idhelprequest", "helprequestdesc_es"));
				this.helpRequestTypes = MastersUtils.parseMasterMap(helpRequestTypes, "idhelprequest",
						"helprequestdesc_es");
			}
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
			
			if(model.getRegistrytypedata().getIdhelprequest() != null) {
				model.getRegistrytypedata().setidhelprequest(model.getRegistrytypedata().getIdhelprequest());
				model.getRegistrytypedata().sethelprequestdesc(helpRequestTypes.get(model.getRegistrytypedata().getIdhelprequest().toString()));
			}
			
			if(getoppositionverificationssbool()) {
				model.getRegistrytypedata().setoppositionverificationss(new Integer(1));
				if(!model.getRegistrytypedata().getoppositionmotive().equals("")) {
					model.getRegistrytypedata().setoppositionmotive(model.getRegistrytypedata().getoppositionmotive());
				}
			} else {
				model.getRegistrytypedata().setoppositionverificationss(new Integer(0));
			}
			
			if(getconsenttributebool()) {
				model.getRegistrytypedata().setconsenttribute(new Integer(1));
			} else {
				model.getRegistrytypedata().setconsenttribute(new Integer(0));
			}
			
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

			respuesta = createNewRegistryInput();
			// Almacenamos la respuesta en el contexto
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
				
			} else { 
				//Redirigimos a una pagina informando de que ya hay un registro existente para este año
				response = "registryInterestedError";
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
		// En caso de exito, redirigir a una página de éxito.
		return response;
	}

	public SolExcModel getModel() {
		return model;
	}

	public void setModel(SolExcModel model) {
		this.model = model;
	}

	public Map<String, String> getHelpRequestTypes() {
		return helpRequestTypes;
	}

	public void setHelpRequestTypes(Map<String, String> requestTypes) {
		this.helpRequestTypes = requestTypes;
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
	 * Comprueba si el interesado ya ha realizado alguna solicitud del mismo tipo 
	 * en el año actual
	 * @return true si es el primer registro en el año
	 */
	public boolean checkInterestedRegistry() {
		boolean firstRegistry = true;
		
		Calendar currentDate = new GregorianCalendar();
		int currentYear = currentDate.get(Calendar.YEAR);

		// DNI del interesado y codigo tipo de registro actual
		Hashtable filtro = new Hashtable();
		filtro.put(FieldNamesWeb.IDENTIFICATIONNUMBER, model.getInteresteddata()[0].getIdentificationnumber());
		filtro.put(FieldNamesWeb.REGISTRYTYPECODE, model.getRegistrytypecode());
		
		try {
			if (model.getInteresteddata()[0].getIdentificationnumber() != null && model.getRegistrytypecode() != null) {
				// Consultamos todos los registros del interesado con el codigo del tipo de registro actual
				Map<String, Object> individualRegistriesQueryResult = queryCividasMaster(FieldNamesWeb.EREGISTRYINPUT,
						filtro, new ArrayList<Object>());
				
				if (!individualRegistriesQueryResult.isEmpty()) {
					if (individualRegistriesQueryResult.get("registrydatehour") != null && individualRegistriesQueryResult.get(FieldNamesWeb.IDREGISTRY) != null) {
						this.idRegistryAndDate = MastersUtils.parseMasterMap(individualRegistriesQueryResult, FieldNamesWeb.IDREGISTRY,
								"registrydatehour");
						
						//Recorremos las fechas de los registros y sacamos el año en el que se hizo el registro
						for(Entry<String, String> idsAndDates : idRegistryAndDate.entrySet()) {
							String dateTime = String.valueOf(idsAndDates.getValue());
							Date date = new Date(Long.parseLong(dateTime));
							Calendar registryDate = new GregorianCalendar();
							registryDate.setTime(date);
							int registryYear = registryDate.get(Calendar.YEAR);
							
							//Comparamos la fecha actual con la del registro realizado anteriormente
							if(currentYear == registryYear) {
								//Si ya existe un registro recogemos el id de registro
								try{
									this.idRegistry = NumberFormat.getInstance().parse(idsAndDates.getKey());
								} catch(ParseException e) {
									log.error("Se ha producido un error. A continuación se mostrará la traza del error", e);
								}
								firstRegistry = false;
								break;
							}
						}
					}
				}
			}
		} catch(CividasQueryDataException cqe) {
			log.error("Se ha producido un error al consultar los registros del interesado. A continuación se mostrará la traza del error", cqe);
		}
		
		return firstRegistry;
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
	

}

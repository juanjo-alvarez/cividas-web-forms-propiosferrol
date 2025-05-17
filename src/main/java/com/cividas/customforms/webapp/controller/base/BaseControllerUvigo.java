package com.cividas.customforms.webapp.controller.base;

import com.cividas.customforms.webapp.common.exceptions.CividasQueryDataException;
import com.cividas.customforms.webapp.common.exceptions.CividasRegistrationException;
import com.cividas.customforms.webapp.common.model.base.*;
import com.cividas.customforms.webapp.common.model.restresponse.RestResponseModel;
import com.cividas.customforms.webapp.common.model.restresponse.RestResponseModel.RestResponseStatus;
import com.cividas.customforms.webapp.configuration.AppConfiguration;
import com.cividas.customforms.webapp.utils.*;
import com.cividas.web.afirma.AfirmaClient;
import com.cividas.web.common.FieldNamesWeb;
import com.cividas.web.common.UploadedFileWrapper;
import com.cividas.web.common.utils.CIFUtils;
import com.cividas.web.common.utils.FileUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;
import com.sun.mail.util.BASE64DecoderStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.Serializable;
import java.security.Key;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

public abstract class BaseControllerUvigo implements Serializable {
	
	private static final long serialVersionUID = -8711865278713531133L;
	protected static final Logger log = LoggerFactory.getLogger(BaseController.class);
	
	private static final String API_REGISTRY_CREATE_COMPLETE_REGISTRY_ENDPOINT = "/register/completeRegistryFromWeb";
	private static final String API_REGISTRY_CREATE_COMPLETE_REGISTRY_TASK_ENDPOINT = "/register/completeRegistryTaskFromWeb";
	
	@ManagedProperty(value = "#{appConfiguration}")
	AppConfiguration appConfiguration;
	
	@ManagedProperty(value = "#{languageBean}")
	LanguageBean languageBean;
	
	protected String baseurl;
	
	// Interfaz que define cual será el tipo de solicitud a tratar. Todos los modelos de datos de solicitudes
	// que quieran hacer uso del BaseController deberían implementar esta interfaz.
	protected IRegistryData baseModel;
	
	// Datos relativos a la recepción de parámetros de la sede
	private String receivedParametersInJSON = null;
	private Map<String, Object> receivedParameters = null;
	protected Map<String, String> queryParameters = null;
	
	// Maestro de direcciones
	private Map<String, String> individualAddress = new HashMap<String, String>();
	private Map<String, String> individualRepreAddress = new HashMap<String, String>();
	
	private LinkedHashMap<String, String> roadtypes = new LinkedHashMap<String, String>();
	private LinkedHashMap<String, String> countries = new LinkedHashMap<String, String>();
	private LinkedHashMap<String, String> provinces = new LinkedHashMap<String, String>();
	private LinkedHashMap <String, String> towns = new LinkedHashMap <String, String>();
	
	private LinkedHashMap<String, String> repreProvinces = new LinkedHashMap<String, String>();
	private LinkedHashMap <String, String> repreTowns = new LinkedHashMap <String, String>();
	
	private LinkedHashMap<String, String> repremanualProvinces = new LinkedHashMap<String, String>();
	private LinkedHashMap <String, String> repremanualTowns = new LinkedHashMap <String, String>();
	
	// Parámetro de control para saber si se habilitan o deshabilitan las direcciones
	private boolean enableAddressFields = false;
	private boolean enableRepreAddressFields = false;
	
	private RegistryAttachment report;
	
	
	private boolean saveContactData					= false;
	private boolean	saveRepreContactData			= false;

	private int secondinterested = 0;
	private boolean representadomanual = false;
	
	private List<RelatedForm> relatedforms = new ArrayList<RelatedForm>();
	
	private String								idRepreInterested;

	public String  EMPTY_ADDRESS_ROADTYPECODE="CALLE";
	public Number  EMPTY_ADDRESS_IDCOUNTRY=69;
	public String  EMPTY_ADDRESS_PROVINCECODE="36";
	public String  EMPTY_ADDRESS_TOWNCODE="36057";
	private boolean									signActive;
	private String 									paramfileMaxSize;
	private String									paramfileMaxNumber;
	private String									paramfileExtension;
	private String 									fileMaxSize;
	private String									fileMaxNumber;
	private String									fileExtension;
	private boolean									paramSign;

	private String								networknotification				= "1";
	// Anexos
	protected transient List<UploadedFileWrapper>	uploadedFiles					= new ArrayList<UploadedFileWrapper>();
	protected transient List<RegistryDocumentTrype>	documentTypesMandatory			= new ArrayList<RegistryDocumentTrype>();
	protected transient List<RegistryDocumentTrype>	documentTypesOptional			= new ArrayList<RegistryDocumentTrype>();
	private int 								uploadedFilesSize				= 0;

	private String								filesBase64;
	private String								filesBase64Signed;
	private String 								resumeBase64Signed;

	protected Number								tmpFileIdToDelete				= null;
	protected int									tmpIdCount						= 0;
	
	protected ResourceBundle						i18n;
	protected 		RestResponseModel respuesta;
	
	private AfirmaClient						afirmaClient ;
	private Number								signatureverification;
	
	
	private Number interestedAsRepre = null;
	
	
	protected Number idprocedureparent;
	
	private String 									afirmaUrl;
	
	@PostConstruct
	public void init(){
		// No hay garantías de que en este momento ya estén cargados los parámetros enviados desde la sede
		log.debug("************* BaseControllerUvigo init() *******");
		// Inicialización del modelo de datos
		((InsertRegistry)baseModel).setInteresteddata(new RegistryInterested[2]);
		((InsertRegistry)baseModel).getInteresteddata()[0] = new RegistryInterested();
		((InsertRegistry)baseModel).getInteresteddata()[0].setInterestedaddress(new RegistryInterestedAddress());
		((InsertRegistry)baseModel).getInteresteddata()[0].setIsprincipal(Integer.valueOf(1));
		((InsertRegistry)baseModel).getInteresteddata()[1] = new RegistryInterested();
		((InsertRegistry)baseModel).getInteresteddata()[1].setInterestedaddress(new RegistryInterestedAddress());
		((InsertRegistry)baseModel).getInteresteddata()[1].setIsprincipal(Integer.valueOf(0));
		FacesContext context = FacesContext.getCurrentInstance();
		i18n = context.getApplication().evaluateExpressionGet(context, "#{i18n}", ResourceBundle.class);
	}
	
	public String obtainAfirmaUrl() {
		try {
			Map<String, Object> attachmentQueryResult = queryCividasMaster(FieldNamesWeb.ECIVIDASPARAMETERS,
					CollectionsUtils.buildMap(FieldNamesWeb.PARAMETER, FieldNamesWeb.AFIRMA_PROPERTIES_URL), new ArrayList<Object>());
			if (attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE) != null
					&& attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE) instanceof List
					&& ((List) attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).size() == 1
					&& ((List) attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).get(0) != null) {
					this.afirmaUrl = (((List) attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).get(0).toString());
			}

		} catch (CividasQueryDataException cqe) {
			log.error("Se ha producido un error al cargar la ruta de @firma", cqe);
		}
		return this.afirmaUrl;
	}
	
	public String obtainFileMaxSize() {
		try {

			report = new RegistryAttachment();
			Map<String, Object> attachmentQueryResult = queryCividasMaster(FieldNamesWeb.ECIVIDASPARAMETERS,
					CollectionsUtils.buildMap(FieldNamesWeb.PARAMETER, FieldNamesWeb.ATTACHMENT_MAX_SIZE), new ArrayList<Object>());
			if (attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE) != null
					&& attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE) instanceof List
					&& ((List) attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).size() == 1
					&& ((List) attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).get(0) != null) {
					this.paramfileMaxSize = (((List) attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).get(0).toString());
			}

		} catch (CividasQueryDataException cqe) {
			log.error(
					"Se ha producido un error al cargar datos de tamaño máximo permitido. A continuación se mostrará la traza del error",
					cqe);
		}
		return this.paramfileMaxSize;
	}


	public String obtainFileMaxNumber() {
		try {

			report = new RegistryAttachment();
			Map<String, Object> attachmentQueryResult = queryCividasMaster(FieldNamesWeb.ECIVIDASPARAMETERS,
					CollectionsUtils.buildMap(FieldNamesWeb.PARAMETER, FieldNamesWeb.FILELIMIT), new ArrayList<Object>());
			if (attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE) != null
					&& attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE) instanceof List
					&& ((List) attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).size() == 1
					&& ((List) attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).get(0) != null) {
					this.paramfileMaxNumber = (((List) attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).get(0).toString());
			}

		} catch (CividasQueryDataException cqe) {
			log.error(
					"Se ha producido un error al cargar dato de archivos máximos permitidos. A continuación se mostrará la traza del error",
					cqe);
		}
		return this.paramfileMaxNumber;
	}

	public String obtainFileExtension() {
		try {

			report = new RegistryAttachment();
			Map<String, Object> attachmentQueryResult = queryCividasMaster(FieldNamesWeb.ECIVIDASPARAMETERS,
					CollectionsUtils.buildMap(FieldNamesWeb.PARAMETER, FieldNamesWeb.ATTACHMENT_VALID_EXTENSIONS), new ArrayList<Object>());
			if (attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE) != null
					&& attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE) instanceof List
					&& ((List) attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).size() == 1
					&& ((List) attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).get(0) != null) {
					this.paramfileExtension = (((List) attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).get(0).toString());
			}

		} catch (CividasQueryDataException cqe) {
			log.error(
					"Se ha producido un error al cargar datos de extensiones de fichero permitido. A continuación se mostrará la traza del error",
					cqe);
		}
		return this.paramfileExtension;
	}
	
	public boolean obtainSignActive() {
		this.paramSign = false;
		try {
			report=new RegistryAttachment();
			Map<String, Object> attachmentQueryResult = queryCividasMaster(FieldNamesWeb.ECIVIDASPARAMETERS,
					CollectionsUtils.buildMap(FieldNamesWeb.PARAMETER, FieldNamesWeb.ATTACHMENT_SIGN_ACTIVE), new ArrayList<Object>());
			if (attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE) != null
					&& attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE) instanceof List
					&& ((List) attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).size() == 1
					&& ((List) attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).get(0) != null
					&& ((List) attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).get(0).toString().equalsIgnoreCase("true")){
					this.paramSign = true;
			}
	
		} catch (CividasQueryDataException cqe) {
			log.error(
					"Se ha producido un error al cargar datos del parámetro que activa la firma automática. A continuación se mostrará la traza del error",
					cqe);
		}
		return this.paramSign;
	}
	
	
	protected String getResourceMessage(String key) {
		String message = key;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			i18n = fc.getApplication().evaluateExpressionGet(fc, "#{i18n}", ResourceBundle.class);
			message = i18n.getString(key);
		} catch (Exception e) {
			// e.printStackTrace();
			message = i18n.getString("NO_VALIDATION_DONE");
		}
		return message;
	}

	protected String receiveParentData(){
		// Recibe, procesa y almacena los parámetros POST enviados desde la sede
		receivePostParametersFromParentWeb();
//		log.info("Parámetros POST procesados correctamente. Información de la solicitud: { idindividualsmaster: " + receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER).toString() + ", username: " + receivedParameters.get(FieldNamesWeb.USERNAME) + " }");
		
		// Se procede con la carga de maestros, solo en caso de que en los datos recibidos haya un sessionID, un username y un idindividualsmaster
		if (receivedParameters != null && receivedParameters.get(FieldNamesWeb.SESSIONID) != null && receivedParameters.get(FieldNamesWeb.USERNAME) != null && receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER) != null){
			// Llamada al método para cargar los maestros. Por defecto, se cargará la dirección, en caso de que el formulario
			// personalizado necesite más maestros debe sobreescribir este método.
			loadDataMasters();
			obtainFileMaxNumber();
			obtainFileMaxSize();
			obtainFileExtension();
			obtainSignActive();
			obtainAfirmaUrl();
			
			loadBaseURL();
			if (isCif()) {
				networknotification="1";
			}
			
		}else{
			log.info("Se omite la carga de maestros ya que no existen datos de sesión.");
		}
		
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	private void receivePostParametersFromParentWeb (){
		try {
			// Conversión del JSON recibido a un mapa de valores
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			this.receivedParameters = mapper.readValue(receivedParametersInJSON, HashMap.class);
			
			processsReceivedParameters();
		} catch (JsonParseException e) {
			log.error("Se ha producido un error al parsear la información del JSON enviado desde la sede. Detalle: ", e);
		} catch (JsonMappingException e) {
			log.error("Se ha producido un error al convertir el JSON enviado desde la sede a un mapa de datos. Detalle: ", e);
		} catch (IOException e) {
			log.error("Se ha producido una excepcion de entrada / salida al acceder a la información del JSON enviado por la sede. Detalle: ", e);
		}
		
		// Se construye el mapa con los atributos para ejecutar consultas sobre Cividas.
		buildQueryParameters(receivedParameters);
		
		// Seteamos el valor del id del procedimiento para que se reciba en la inserción del registro telemático
		if(receivedParameters.get(FieldNamesWeb.IDBDUACPROCEDURETYPE)!=null){
			Number idBduacProcedureType=null;
			if(receivedParameters.get(FieldNamesWeb.IDBDUACPROCEDURETYPE) instanceof String) {
				idBduacProcedureType=Integer.valueOf((String) receivedParameters.get(FieldNamesWeb.IDBDUACPROCEDURETYPE));
			}else {
				idBduacProcedureType=(Number) receivedParameters.get(FieldNamesWeb.IDBDUACPROCEDURETYPE);
			}
			((InsertRegistry)this.baseModel).setIdbduacproceduretype(idBduacProcedureType);
		}
		
		if(receivedParameters.get(FieldNamesWeb.REGISTRYTYPECODE)!=null){
			((InsertRegistry)this.baseModel).setRegistrytypecode(receivedParameters.get(FieldNamesWeb.REGISTRYTYPECODE).toString());
		}

		loadInterestedData();
		loadBduacData();
	}
	
	private void processsReceivedParameters(){
		
		if(!(Boolean)(receivedParameters.get(FieldNamesWeb.IS_REPRESENTATION))){
			
			if (receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER) != null && !receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER).equals("")) {
				Number idIndividualsMaster=null;
				if(receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER) instanceof String) {
					idIndividualsMaster=Integer.valueOf((String) receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER));
				}else {
					idIndividualsMaster=(Number) receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER);
				}
				((InsertRegistry) this.baseModel).getInteresteddata()[0].setIdindividualsmaster(idIndividualsMaster);
			}
			if (receivedParameters.get(FieldNamesWeb.IDENTIFICATIONNUMBER) != null) {
				((InsertRegistry) this.baseModel).getInteresteddata()[0].setIdentificationnumber((String) receivedParameters.get(FieldNamesWeb.IDENTIFICATIONNUMBER));
			}
			if (receivedParameters.get(FieldNamesWeb.NAME) != null) {
				((InsertRegistry) this.baseModel).getInteresteddata()[0].setName((String) receivedParameters.get(FieldNamesWeb.NAME));
			}
			if (receivedParameters.get(FieldNamesWeb.SURNAME1) != null) {
				((InsertRegistry) this.baseModel).getInteresteddata()[0].setSurname1((String) receivedParameters.get(FieldNamesWeb.SURNAME1));
			}
			if (receivedParameters.get(FieldNamesWeb.SURNAME2) != null) {
				((InsertRegistry) this.baseModel).getInteresteddata()[0].setSurname2((String) receivedParameters.get(FieldNamesWeb.SURNAME2));
			}
			if (receivedParameters.get(FieldNamesWeb.EMAIL) != null) {
				((InsertRegistry) this.baseModel).getInteresteddata()[0].setEmail((String) receivedParameters.get(FieldNamesWeb.EMAIL));
			}
		}else{
			if (receivedParameters.get(FieldNamesWeb.IDREPRESENTED) != null) {
				Number idIndividualsRepresented=null;
				if(receivedParameters.get(FieldNamesWeb.IDREPRESENTED) instanceof String) {
					idIndividualsRepresented=Integer.valueOf((String) receivedParameters.get(FieldNamesWeb.IDREPRESENTED));
				}else {
					idIndividualsRepresented=(Number) receivedParameters.get(FieldNamesWeb.IDREPRESENTED);
				}
				((InsertRegistry) this.baseModel).getInteresteddata()[0].setIdindividualsmaster(idIndividualsRepresented);
			}
			if (receivedParameters.get(FieldNamesWeb.REPRESENTEDIDENTIFICATIONNUMBER) != null) {
				((InsertRegistry) this.baseModel).getInteresteddata()[0].setIdentificationnumber((String) receivedParameters.get(FieldNamesWeb.REPRESENTEDIDENTIFICATIONNUMBER));
			}
			if (receivedParameters.get(FieldNamesWeb.REPRESENTEDNAME) != null) {
				((InsertRegistry) this.baseModel).getInteresteddata()[0].setName((String) receivedParameters.get(FieldNamesWeb.REPRESENTEDNAME));
			}
//			if (receivedParameters.get(FieldNamesWeb.EMAIL) != null) {
//				((InsertRegistry) this.baseModel).getInteresteddata()[0].setEmail((String) receivedParameters.get(FieldNamesWeb.EMAIL));
//			}
			if (receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER) != null) {
				Number idIndividualsMaster=null;
				if(receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER) instanceof String) {
					idIndividualsMaster=Integer.valueOf((String) receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER));
				}else {
					idIndividualsMaster=(Number) receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER);
				}
				((InsertRegistry) this.baseModel).getInteresteddata()[0].setIdregrepresentedby(idIndividualsMaster);
				((InsertRegistry) this.baseModel).getInteresteddata()[1].setIdindividualsmaster(idIndividualsMaster);
				this.idRepreInterested=idIndividualsMaster.toString();
			}
			if (receivedParameters.get(FieldNamesWeb.IDENTIFICATIONNUMBER) != null) {
				((InsertRegistry) this.baseModel).getInteresteddata()[1].setIdentificationnumber((String) receivedParameters.get(FieldNamesWeb.IDENTIFICATIONNUMBER));
			}
			if (receivedParameters.get(FieldNamesWeb.NAME) != null) {
				((InsertRegistry) this.baseModel).getInteresteddata()[1].setName((String) receivedParameters.get(FieldNamesWeb.NAME));
			}
			if (receivedParameters.get(FieldNamesWeb.SURNAME1) != null) {
				((InsertRegistry) this.baseModel).getInteresteddata()[1].setSurname1((String) receivedParameters.get(FieldNamesWeb.SURNAME1));
			}
			if (receivedParameters.get(FieldNamesWeb.SURNAME2) != null) {
				((InsertRegistry) this.baseModel).getInteresteddata()[1].setSurname2((String) receivedParameters.get(FieldNamesWeb.SURNAME2));
			}
//			if (receivedParameters.get(FieldNamesWeb.EMAIL) != null) {
//				((InsertRegistry) this.baseModel).getInteresteddata()[1].setEmail((String) receivedParameters.get(FieldNamesWeb.EMAIL));
//			}
		}
		if (receivedParameters.get(FieldNamesWeb.LANG) != null && getLanguageBean() != null){
			getLanguageBean().setLang((String) receivedParameters.get(FieldNamesWeb.LANG));
		}
			this.networknotification=networknotification; //Forzar la notificacion Electronica para coincidir con su sede
	}
	
	
	protected void buildQueryParameters(Map<String, Object> params){
		queryParameters = new HashMap<String, String>();
		queryParameters.put(FieldNamesWeb.SESSIONID, params.get(FieldNamesWeb.SESSIONID) != null && !params.equals("")? String.valueOf(params.get(FieldNamesWeb.SESSIONID)) : "-1");
		queryParameters.put("user", (String)(params.get(FieldNamesWeb.USERNAME)));
	}
	

	protected void loadInterestedData (){
		// Carga de datos no recibidos del interesado
		try{
			
			if(!(Boolean)(receivedParameters.get(FieldNamesWeb.IS_REPRESENTATION))){
				Map<String, Object> individualQueryResult = queryCividasMaster(FieldNamesWeb.EINDIVIDUALSMASTER, CollectionsUtils.buildMap(FieldNamesWeb.IDINDIVIDUALSMASTER, receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER)), new ArrayList<Object>());
				if(individualQueryResult.get(FieldNamesWeb.TELEPHONE) instanceof List && ((List)individualQueryResult.get(FieldNamesWeb.TELEPHONE)).size()==1 //
					&& ((List)individualQueryResult.get(FieldNamesWeb.TELEPHONE)).get(0)!=null){
					((InsertRegistry) this.baseModel).getInteresteddata()[0].setTelephone((String) ((List)individualQueryResult.get(FieldNamesWeb.TELEPHONE)).get(0));
				}

				if(individualQueryResult.get(FieldNamesWeb.EMAIL) instanceof List && ((List)individualQueryResult.get(FieldNamesWeb.EMAIL)).size()==1 //
					&& ((List)individualQueryResult.get(FieldNamesWeb.EMAIL)).get(0)!=null){
					((InsertRegistry) this.baseModel).getInteresteddata()[0].setEmail((String)((List)individualQueryResult.get(FieldNamesWeb.EMAIL)).get(0));
				}

				//CE-1696 Comentado para coincidir con su sede
				/*if(individualQueryResult.get(FieldNamesWeb.NETWORKNOTIFICATION) instanceof List && ((List)individualQueryResult.get(FieldNamesWeb.NETWORKNOTIFICATION)).size()==1 //
					&& ((List)individualQueryResult.get(FieldNamesWeb.NETWORKNOTIFICATION)).get(0)!=null){
					this.networknotification=(((List)individualQueryResult.get(FieldNamesWeb.NETWORKNOTIFICATION)).get(0).toString());
				}*/

			}else{
				// Hay representante
				// interesado principal
				Map<String, Object> individualQueryResult = queryCividasMaster(FieldNamesWeb.EINDIVIDUALSMASTER, CollectionsUtils.buildMap(FieldNamesWeb.IDINDIVIDUALSMASTER, receivedParameters.get(FieldNamesWeb.IDREPRESENTED)), new ArrayList<Object>());
				if(individualQueryResult.get(FieldNamesWeb.TELEPHONE) instanceof List && ((List)individualQueryResult.get(FieldNamesWeb.TELEPHONE)).size()==1//
					&& ((List)individualQueryResult.get(FieldNamesWeb.TELEPHONE)).get(0)!=null){
					((InsertRegistry) this.baseModel).getInteresteddata()[0].setTelephone((String)((List)individualQueryResult.get(FieldNamesWeb.TELEPHONE)).get(0));
				}
				if(individualQueryResult.get(FieldNamesWeb.EMAIL) instanceof List && ((List)individualQueryResult.get(FieldNamesWeb.EMAIL)).size()==1 //
					&& ((List)individualQueryResult.get(FieldNamesWeb.EMAIL)).get(0)!=null){
					((InsertRegistry) this.baseModel).getInteresteddata()[0].setEmail((String)((List)individualQueryResult.get(FieldNamesWeb.EMAIL)).get(0));
				}
				/*if(individualQueryResult.get(FieldNamesWeb.NETWORKNOTIFICATION) instanceof List && ((List)individualQueryResult.get(FieldNamesWeb.NETWORKNOTIFICATION)).size()==1 //
					&& ((List)individualQueryResult.get(FieldNamesWeb.NETWORKNOTIFICATION)).get(0)!=null){
					this.networknotification=(((List)individualQueryResult.get(FieldNamesWeb.NETWORKNOTIFICATION)).get(0).toString());
				}*/

				// representante
				Map<String, Object> individualrepQueryResult = queryCividasMaster(FieldNamesWeb.EINDIVIDUALSMASTER, CollectionsUtils.buildMap(FieldNamesWeb.IDINDIVIDUALSMASTER, receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER)), new ArrayList<Object>());
				if(individualrepQueryResult.get(FieldNamesWeb.TELEPHONE) instanceof List && ((List)individualrepQueryResult.get(FieldNamesWeb.TELEPHONE)).size()==1 //
					&& ((List)individualrepQueryResult.get(FieldNamesWeb.TELEPHONE)).get(0)!=null){
					((InsertRegistry) this.baseModel).getInteresteddata()[1].setTelephone((String)((List)individualrepQueryResult.get(FieldNamesWeb.TELEPHONE)).get(0));
				}
				if(individualrepQueryResult.get(FieldNamesWeb.EMAIL) instanceof List && ((List)individualrepQueryResult.get(FieldNamesWeb.EMAIL)).size()==1//
					&& ((List)individualrepQueryResult.get(FieldNamesWeb.EMAIL)).get(0)!=null){
					((InsertRegistry) this.baseModel).getInteresteddata()[1].setEmail((String)((List)individualrepQueryResult.get(FieldNamesWeb.EMAIL)).get(0));
				}
			}
		}catch (CividasQueryDataException cqe){
			log.error("Se ha producido un error al cargar datos del interesado. A continuación se mostrará la traza del error", cqe);
		}
		
	}
	
	
	protected void loadBduacData() {
		// Carga de datos no recibidos del PCD
		try {
			// Seteo del valor del campo subject con el título del procedimiento de la BDUAC
			if (receivedParameters.get(FieldNamesWeb.IDBDUACPROCEDURETYPE) != null) {
				Map<String, Object> bduacQueryResult = queryCividasMaster(FieldNamesWeb.EBDUACPROCEDURETYPES,
						CollectionsUtils.buildMap(FieldNamesWeb.IDBDUACPROCEDURETYPE,
								receivedParameters.get(FieldNamesWeb.IDBDUACPROCEDURETYPE)),
						new ArrayList<Object>());

				// Castellano
				if (receivedParameters.get(FieldNamesWeb.LANG) != null
						&& receivedParameters.get(FieldNamesWeb.LANG).toString().equalsIgnoreCase("ES")) {
					if (bduacQueryResult.get(FieldNamesWeb.TITLE_LG1) != null
							&& bduacQueryResult.get(FieldNamesWeb.TITLE_LG1) instanceof List
							&& ((List) bduacQueryResult.get(FieldNamesWeb.TITLE_LG1)).size() == 1
							&& ((List) bduacQueryResult.get(FieldNamesWeb.TITLE_LG1)).get(0) != null) {
						((InsertRegistry) this.baseModel)
								.setSubject(((List) bduacQueryResult.get(FieldNamesWeb.TITLE_LG1)).get(0).toString());
					}
				} else
				// Galego
				if (receivedParameters.get(FieldNamesWeb.LANG) != null
						&& receivedParameters.get(FieldNamesWeb.LANG).toString().equalsIgnoreCase("GL")) {
					if (bduacQueryResult.get(FieldNamesWeb.TITLE_LG2) != null
							&& bduacQueryResult.get(FieldNamesWeb.TITLE_LG2) instanceof List
							&& ((List) bduacQueryResult.get(FieldNamesWeb.TITLE_LG2)).size() == 1
							&& ((List) bduacQueryResult.get(FieldNamesWeb.TITLE_LG2)).get(0) != null) {
						((InsertRegistry) this.baseModel)
								.setSubject(((List) bduacQueryResult.get(FieldNamesWeb.TITLE_LG2)).get(0).toString());
					}
				} else
				// Inglés
				if (receivedParameters.get(FieldNamesWeb.LANG) != null
						&& receivedParameters.get(FieldNamesWeb.LANG).toString().equalsIgnoreCase("EN")) {
					if (bduacQueryResult.get(FieldNamesWeb.TITLE_LG3) != null
							&& bduacQueryResult.get(FieldNamesWeb.TITLE_LG3) instanceof List
							&& ((List) bduacQueryResult.get(FieldNamesWeb.TITLE_LG3)).size() == 1
							&& ((List) bduacQueryResult.get(FieldNamesWeb.TITLE_LG3)).get(0) != null) {
						((InsertRegistry) this.baseModel)
								.setSubject(((List) bduacQueryResult.get(FieldNamesWeb.TITLE_LG3)).get(0).toString());
					}
				}
				//verificación de firma
				if (((List)bduacQueryResult.get(FieldNamesWeb.SIGNATURE_VERIFICATION)).size() > 0 
						&& ((List)bduacQueryResult.get(FieldNamesWeb.SIGNATURE_VERIFICATION)).get(0) != null) { 
					setSignatureverification((Number) ((List)bduacQueryResult.get(FieldNamesWeb.SIGNATURE_VERIFICATION)).get(0));
				}				
				//tipos de documentos del trámite
				Map<String, Object> bduacDocumentQueryResult = queryCividasMaster(FieldNamesWeb.EDOCUMENTVERIFICATION,
						CollectionsUtils.buildMap(FieldNamesWeb.IDBDUACPROCEDURETYPE,receivedParameters.get(FieldNamesWeb.IDBDUACPROCEDURETYPE),
													FieldNamesWeb.SEDEREQUESTED,1),
						new ArrayList<Object>());
				if(bduacDocumentQueryResult.get(FieldNamesWeb.SEDEREQUESTED)!=null) {
					ArrayList<String> asedetext = (ArrayList<String>) bduacDocumentQueryResult.get(FieldNamesWeb.SEDETEXT);
					ArrayList<String> asedetextgl = (ArrayList<String>) bduacDocumentQueryResult.get(FieldNamesWeb.SEDETEXTGL);
					ArrayList<Integer> aisnotmandatory = (ArrayList<Integer>) bduacDocumentQueryResult.get(FieldNamesWeb.ISNOTMANDATORY);
					ArrayList<Integer> aiddocumentmastertype = (ArrayList<Integer>) bduacDocumentQueryResult.get(FieldNamesWeb.IDDOCUMENTMASTERTYPE);
					ArrayList<String> adocumentmastertypename = (ArrayList<String>) bduacDocumentQueryResult.get(FieldNamesWeb.DOCUMENTMASTERTYPENAME);
					for(int i=0;i<asedetext.size();i++) {
						RegistryDocumentTrype item = new RegistryDocumentTrype();
						//si sedetext tiene texto usa ese texto, sino el texto del maestro de cividas
						//si viene seteado el lenguage en gallego usamos el campo gl, sino usamos por defecto que es español
						if (receivedParameters.get(FieldNamesWeb.LANG) != null
								&& receivedParameters.get(FieldNamesWeb.LANG).toString().equalsIgnoreCase("GL")) {
							if(asedetextgl.get(i)==null||asedetextgl.get(i).toLowerCase().equalsIgnoreCase("null")||asedetextgl.get(i).trim().equalsIgnoreCase("")) {
								item.setDocumentTypeName(adocumentmastertypename.get(i));
							}else {
								item.setDocumentTypeName(asedetextgl.get(i));
							}
						}else {
							if(asedetext.get(i)==null||asedetext.get(i).toLowerCase().equalsIgnoreCase("null")||asedetext.get(i).trim().equalsIgnoreCase("")) {
								item.setDocumentTypeName(adocumentmastertypename.get(i));
							}else {
								item.setDocumentTypeName(asedetext.get(i));
							}
						}
						//tipo de documento para el insert futuro
						item.setIddocumentmastertype(aiddocumentmastertype.get(i));
						//decidimos si va a la lista de obligatorios o opcionales
						if(aisnotmandatory.get(i)==0) {
							documentTypesMandatory.add(item);
						}else {
							documentTypesOptional.add(item);
						}
						
					}
				}

			}
		} catch (CividasQueryDataException cqe) {
			log.error(
					"Se ha producido un error al cargar datos del interesado. A continuación se mostrará la traza del error",
					cqe);
		}		
	}
	
	protected void loadBaseURL() {
		Map<String, Object> attachmentQueryResult;
		try {
			attachmentQueryResult = queryCividasMaster(FieldNamesWeb.ECIVIDASPARAMETERS, CollectionsUtils.buildMap(FieldNamesWeb.PARAMETER,"Web.General.URLBase"), new ArrayList<Object>());

			if(attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)!=null && attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE) instanceof List 
					&& ((List)attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).size()==1 && ((List)attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).get(0)!=null){
				baseurl=(((List)attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).get(0).toString());
			}
		} catch (CividasQueryDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void loadDataMasters (){
		// Carga de maestros genericos
		log.info("Carga de maestros genericos...");
		
		try{
			
			Map<String, Object> roadtypesQueryResult = queryCividasMaster(FieldNamesWeb.ROADTYPE_ENTITY_NAME, new HashMap(), new ArrayList<Object>());
			this.roadtypes = MastersUtils.parseMasterLinkedHashMap(roadtypesQueryResult, FieldNamesWeb.ROADTYPE_CODE, FieldNamesWeb.ROADTYPE_DESCRIPTION);
			Map<String, Object> countriessQueryResult = queryCividasMaster(FieldNamesWeb.COUNTRY_ENTITY_NAME, new HashMap(), new ArrayList<Object>());
			this.countries = MastersUtils.parseMasterLinkedHashMap(countriessQueryResult, FieldNamesWeb.USER_ADDRESS_COUNTRY_ID, FieldNamesWeb.COUNTRY_DESCRIPTION);
			
			if(!(Boolean)(receivedParameters.get(FieldNamesWeb.IS_REPRESENTATION))){
				// No hay representación
				Map<String, Object> individualAddressQueryResult = queryCividasMaster(FieldNamesWeb.USER_ADDRESS_ENTITY_NAME, CollectionsUtils.buildMap(FieldNamesWeb.IDINDIVIDUALSMASTER, receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER)), new ArrayList<Object>());
				this.individualAddress = MastersUtils.parseMasterMap(individualAddressQueryResult, FieldNamesWeb.USER_ADDRESS_ID, FieldNamesWeb.USER_ADDRESS_COMPLETE_ADDRESS);
				if(individualAddress.isEmpty()){
					enableAddressFields=true;
					setEmptyAddressToPrincipalInterested();
					loadProvinces();
					loadTowns();
				}
				
				// En caso de no haber representación cubrimos los datos de la dirección del representante "manual"
				// Default Values
				setEmptyAddressToRepreInterested();
				loadmanualRepreProvinces();
				loadmanualRepreTowns();
				
			}else{
				// Hay representación
				// Direcciones interesado principal
				Map<String, Object> individualAddressQueryResult = queryCividasMaster(FieldNamesWeb.USER_ADDRESS_ENTITY_NAME, 
						CollectionsUtils.buildMap(FieldNamesWeb.IDINDIVIDUALSMASTER, receivedParameters.get(FieldNamesWeb.IDREPRESENTED)), new ArrayList<Object>());
				this.individualAddress = MastersUtils.parseMasterMap(individualAddressQueryResult, FieldNamesWeb.USER_ADDRESS_ID, FieldNamesWeb.USER_ADDRESS_COMPLETE_ADDRESS);
				if(individualAddress.isEmpty()){
					enableAddressFields=true;
					setEmptyAddressToPrincipalInterested();
					loadProvinces();
					loadTowns();
				}
				// Direcciones representante
				Map<String, Object> representAddressQueryResult = queryCividasMaster(FieldNamesWeb.USER_ADDRESS_ENTITY_NAME, 
						CollectionsUtils.buildMap(FieldNamesWeb.IDINDIVIDUALSMASTER, receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER)), new ArrayList<Object>());
				this.individualRepreAddress = MastersUtils.parseMasterMap(representAddressQueryResult, FieldNamesWeb.USER_ADDRESS_ID, FieldNamesWeb.USER_ADDRESS_COMPLETE_ADDRESS);
				if(individualRepreAddress.isEmpty()){
					enableRepreAddressFields=true;
					// Default Values
					setEmptyAddressToRepreInterested();
					loadRepreProvinces();
					loadRepreTowns();

				}
			}
			
			Map<String, Object> interestedAsQueryResult = queryCividasMaster(FieldNamesWeb.INTERESTEDAS_ENTITY_NAME,
					CollectionsUtils.buildMap(FieldNamesWeb.INTERESTEDASDESC, "Representante"),
					new ArrayList<Object>());
			if(!interestedAsQueryResult.isEmpty()) {
				List<Number> lIds = (List<Number>) interestedAsQueryResult.get(FieldNamesWeb.IDINTERESTEDAS);
				setInterestedAsRepre(lIds.get(0));
			}
			
			// Carga formularios requeridos
			loadrelatedforms();
			
			
					
			// Consultamos el idprocedure de la convocatoria relacionada con el procedimiento en que estamos
			idprocedureparent=getExpedienteConvocatoria();
		}catch (CividasQueryDataException cqe){
			log.error("Se ha producido un error al cargar el maestro de direcciones del interesado. A continuación se mostrará la traza del error", cqe);
		}
		
	}
	
	
	public void setEmptyAddressToPrincipalInterested(){
		// Default Values
		if(((InsertRegistry)this.baseModel).getInteresteddata()[0]!=null && ((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress()!=null){
			((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress().setRoadtypecode(EMPTY_ADDRESS_ROADTYPECODE);
			((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress().setIdcountry(EMPTY_ADDRESS_IDCOUNTRY);
			((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress().setProvincecode(EMPTY_ADDRESS_PROVINCECODE);
			((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress().setTowncode(EMPTY_ADDRESS_TOWNCODE);
		}
	}
	
	public void setEmptyAddressToRepreInterested(){
		// Default Values
		if(((InsertRegistry)this.baseModel).getInteresteddata()[1]!=null && ((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress()!=null){
			((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().setRoadtypecode(EMPTY_ADDRESS_ROADTYPECODE);
			((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().setIdcountry(EMPTY_ADDRESS_IDCOUNTRY);
			((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().setProvincecode(EMPTY_ADDRESS_PROVINCECODE);
			((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().setTowncode(EMPTY_ADDRESS_TOWNCODE);
		}
	}
	
	public void onCountryChange(final AjaxBehaviorEvent event) {
		// Borramos la provincia y la ciudad elegidas anteriormente
		if(((InsertRegistry)this.baseModel).getInteresteddata()[0]!=null &&((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress()!=null){
			((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress().setProvincecode(null);
			((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress().setTowncode(null);
		}
		loadProvinces();
		loadTowns();
	}
	
	
	public void onProvinceChange(final AjaxBehaviorEvent event) {
		loadTowns();
	}
	
	
	public void loadProvinces(){
		try {
			if(((InsertRegistry)this.baseModel).getInteresteddata()[0]!=null 
					&&((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress()!=null 
					&&((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress().getIdcountry()!=null){
				Map<String, Object> provincesQueryResult = queryCividasMaster(FieldNamesWeb.PROVINCE_ENTITY_NAME, CollectionsUtils.buildMap(FieldNamesWeb.USER_ADDRESS_COUNTRY_ID, ((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress().getIdcountry()), new ArrayList<Object>());
				this.provinces = MastersUtils.parseMasterLinkedHashMap(provincesQueryResult, FieldNamesWeb.USER_ADDRESS_PROVINCE_CODE, FieldNamesWeb.USER_ADDRESS_PROVINCE_DESC);//  idcountry
			}
		} catch (CividasQueryDataException e) {
			log.error("Se ha producido un error al cargar el maestro de provincias del interesado. A continuación se mostrará la traza del error", e);
		}	
	}
	
	public void loadTowns(){
		Map<String, Object> townsQueryResult;
		try {
			if(((InsertRegistry)this.baseModel).getInteresteddata()[0]!=null 
					&&((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress()!=null 
					&&((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress().getProvincecode()!=null){
				townsQueryResult = queryCividasMaster("maintenance.ETowns", CollectionsUtils.buildMap(FieldNamesWeb.USER_ADDRESS_PROVINCE_CODE, ((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress().getProvincecode()), new ArrayList<Object>());
				this.towns = MastersUtils.parseMasterLinkedHashMap(townsQueryResult, FieldNamesWeb.USER_ADDRESS_TOWN_CODE, FieldNamesWeb.USER_ADDRESS_TOWN_DESC);//provincecode
			}else{
				this.towns=new LinkedHashMap<String,String>();
			}
		} catch (CividasQueryDataException e) {
			log.error("Se ha producido un error al cargar el maestro de ciudades del interesado. A continuación se mostrará la traza del error", e);
		}	
	}
	
	
	public void onCountryRepreChange(final AjaxBehaviorEvent event) {
		// Borramos la provincia y la ciudad elegidas anteriormente
				if(((InsertRegistry)this.baseModel).getInteresteddata()[1]!=null &&((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress()!=null){
					((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().setProvincecode(null);
					((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().setTowncode(null);
				}
		loadRepreProvinces();
		loadRepreTowns();
	}
	
	
	public void onProvinceRepreChange(final AjaxBehaviorEvent event) {
		loadRepreTowns();
	}
	
	
	public void loadRepreProvinces(){
		try {
			if(((InsertRegistry)this.baseModel).getInteresteddata()[1]!=null 
					&&((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress()!=null 
					&&((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().getIdcountry()!=null){
				Map<String, Object> provincesQueryResult = queryCividasMaster(FieldNamesWeb.PROVINCE_ENTITY_NAME, 
						CollectionsUtils.buildMap(FieldNamesWeb.USER_ADDRESS_COUNTRY_ID, ((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().getIdcountry()), new ArrayList<Object>());
				this.repreProvinces = MastersUtils.parseMasterLinkedHashMap(provincesQueryResult, FieldNamesWeb.USER_ADDRESS_PROVINCE_CODE, FieldNamesWeb.USER_ADDRESS_PROVINCE_DESC);//  idcountry
			}
		} catch (CividasQueryDataException e) {
			log.error("Se ha producido un error al cargar el maestro de provincias del representante. A continuación se mostrará la traza del error", e);
		}	
	}
	
	public void loadRepreTowns(){
		Map<String, Object> townsQueryResult;
		try {
			if(((InsertRegistry)this.baseModel).getInteresteddata()[1]!=null 
					&&((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress()!=null 
					&&((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().getProvincecode()!=null){
				townsQueryResult = queryCividasMaster("maintenance.ETowns", CollectionsUtils.buildMap(FieldNamesWeb.USER_ADDRESS_PROVINCE_CODE, 
						((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().getProvincecode()), new ArrayList<Object>());
				this.repreTowns = MastersUtils.parseMasterLinkedHashMap(townsQueryResult, FieldNamesWeb.USER_ADDRESS_TOWN_CODE, FieldNamesWeb.USER_ADDRESS_TOWN_DESC);//provincecode
			}else{
				this.repreTowns=new LinkedHashMap<String,String>();
			}
		} catch (CividasQueryDataException e) {
			log.error("Se ha producido un error al cargar el maestro de ciudades del representante. A continuación se mostrará la traza del error", e);
		}	
	}
	

	public abstract String sendRequest();
	
	protected RestResponseModel createNewRegistryInput() throws CividasRegistrationException {
		return createNewRegistryInput(API_REGISTRY_CREATE_COMPLETE_REGISTRY_ENDPOINT);
	}
	
	protected RestResponseModel createNewRegistryInputTask() throws CividasRegistrationException {
		return createNewRegistryInput(API_REGISTRY_CREATE_COMPLETE_REGISTRY_TASK_ENDPOINT);
	}
	
	private RestResponseModel createNewRegistryInput(String endpoint) throws CividasRegistrationException {
		RestResponseModel response = new RestResponseModel();
					
		try {
				
			if (baseModel instanceof InsertRegistryTask) {
				baseModel = getModelDataForInsert((InsertRegistryTask) baseModel);
			} else {
				baseModel = getModelDataForInsert((InsertRegistry) baseModel);
			}

			log.debug("createNewRegistryInput - doRegistryInputPostCall");
			response = doRegistryInputPostCall(endpoint);
			
		} catch (Exception ex) {
			log.error("Se ha producido un error no controlado a la hora de invocar el servicio web de alta de registro ", ex);
			throw new CividasRegistrationException("No se ha podido finalizar la solicitud de alta de registro en Cividas.");
		}
		
		return response;
	}
		
	private IRegistryData getModelDataForInsert(InsertRegistryTask modelData) throws CividasRegistrationException {
		
		IRegistryData registryData = getModelDataForInsert((InsertRegistry) modelData);
		
		InsertRegistryTask insertRegistryTask = (InsertRegistryTask) registryData;
		
		Number idProcedure = null;
		if(receivedParameters.get(FieldNamesWeb.ID_PROCEDURE) instanceof String) {
			idProcedure = (Number) Integer.valueOf((String) receivedParameters.get(FieldNamesWeb.ID_PROCEDURE));
		}else {
			idProcedure = (Number) receivedParameters.get(FieldNamesWeb.ID_PROCEDURE);
		}
		insertRegistryTask.setIdprocedure(idProcedure);
			
		Number idTaskType = null;
		if(receivedParameters.get(FieldNamesWeb.IDTASKTYPE) instanceof String) {
			idTaskType = (Number) Integer.valueOf((String) receivedParameters.get(FieldNamesWeb.IDTASKTYPE));
		}else {
			idTaskType = (Number) receivedParameters.get(FieldNamesWeb.IDTASKTYPE);
		}
		insertRegistryTask.setIdtasktype(idTaskType);
				
		return insertRegistryTask;
	}
		
	private IRegistryData getModelDataForInsert(InsertRegistry modelData) throws CividasRegistrationException {
		
		try {
				
			// Setting session info
			modelData.setSessionid(Integer.valueOf((String.valueOf(receivedParameters.get(FieldNamesWeb.SESSIONID)))).intValue());
			modelData.setUsername((String) receivedParameters.get(FieldNamesWeb.USERNAME));
			if (receivedParameters.get(FieldNamesWeb.REGISTRYTYPECODE) != null){
				modelData.setRegistrytypecode((String) receivedParameters.get(FieldNamesWeb.REGISTRYTYPECODE));
			}
			if(receivedParameters.get(FieldNamesWeb.IDPROCEDURETYPE)!=null){
				Number idProcedureType=null;
				if(receivedParameters.get(FieldNamesWeb.IDPROCEDURETYPE) instanceof String) {
					idProcedureType=(Number)Integer.valueOf((String) receivedParameters.get(FieldNamesWeb.IDPROCEDURETYPE));
				}else {
					idProcedureType=(Number) receivedParameters.get(FieldNamesWeb.IDPROCEDURETYPE);
				}
				modelData.setIdproceduretype(idProcedureType);
			}
			
			// Check inserts before insert in DB
			checkInterestedBeforeInsert();
			
			List<RegistryAttachment> attachments = new ArrayList<RegistryAttachment>();
			if (!this.uploadedFiles.isEmpty()) {
				String[] signedFilesB64 = null;
				if (!StringUtils.isBlank(filesBase64Signed)) {
					signedFilesB64 = filesBase64Signed.split(",");
				}
				for (int i = 0; i < this.uploadedFiles.size(); i++) {
					RegistryAttachment attach = new RegistryAttachment();
					attach.setAttachmentfilename(uploadedFiles.get(i).getFile().getFileName());
					attach.setAttachmentbytearray( new String(Base64.encode(uploadedFiles.get(i).getFile().getContent())));
					String byteArrayStr = null;
					if (signedFilesB64 != null && !signedFilesB64[i].isEmpty()) {
						byteArrayStr = signedFilesB64[i];
					} else {
						byteArrayStr = new String(Base64.encode(uploadedFiles.get(i).getFile().getContent()));
					}
					attach.setAttachmentbytearray(byteArrayStr);

					if(uploadedFiles.get(i).getAttachmentdatadesc()!=null && !uploadedFiles.get(i).getAttachmentdatadesc().isEmpty()){
						attach.setAttachmentdatadesc(uploadedFiles.get(i).getAttachmentdatadesc());
					}
										
					//asociar interesado principal a los adjuntos
					if ( modelData.getInteresteddata() != null ){
						if(modelData.getInteresteddata().length>0){
							if(modelData.getInteresteddata()[0]!=null){
								Number idIndividualsMaster = modelData.getInteresteddata()[0].getIdindividualsmaster();
								if(idIndividualsMaster!=null) {
									attach.setIdindividualsmaster(idIndividualsMaster);
								}
							}
						}
					}
					//tipado de ficheros
					if(uploadedFiles.get(i).getIddocumentmastertype()!=null) {
						attach.setIddocumentmastertype(uploadedFiles.get(i).getIddocumentmastertype());
					}
					// añadimos el anexo a la lista con los valores que se van a insertar
					attachments.add(attach);
				}
				//resumen firmado
				if (!StringUtils.isBlank(resumeBase64Signed)) {
					RegistryAttachment attach = new RegistryAttachment();
					attach.setAttachmentbytearray(resumeBase64Signed);
					attach.setAttachmentfilename("solicitude-asinada.pdf");
					//asociar interesado principal a los adjuntos
					if ( modelData.getInteresteddata() != null ){
						if(modelData.getInteresteddata().length>0){
							if(modelData.getInteresteddata()[0]!=null){
								Number idIndividualsMaster = modelData.getInteresteddata()[0].getIdindividualsmaster();
								if(idIndividualsMaster!=null) {
									attach.setIdindividualsmaster(idIndividualsMaster);
								}
							}
						}
					}
					attachments.add(attach);
				}

				if(attachments.size()>0){
					modelData.setAttachments((RegistryAttachment[]) attachments.toArray(new RegistryAttachment[attachments.size()]));
				}
			}
			
			if (receivedParameters.get(FieldNamesWeb.LANG) != null){
				modelData.setLocale((String) receivedParameters.get(FieldNamesWeb.LANG));
			}
			
		} catch (Exception ex) {
			log.error("Se ha producido un error no controlado a la hora de obtener los datos ", ex);
			throw new CividasRegistrationException("No se ha podido finalizar la solicitud de alta de registro en Cividas.");
		}
		
		return modelData;		
	}
	
	private RestResponseModel doRegistryInputPostCall(String endpoint) throws CividasRegistrationException, UniformInterfaceException, 
		ClientHandlerException, JsonProcessingException {
		
		RestResponseModel response = new RestResponseModel();
		
		// Se obtiene el cliente para establecer la comunicación con la capa REST
		Client client = DefaultRestClient.getInstance();
		if (client == null) {
			log.error("Ha sido imposible definir el cliente para establecer la conexión con la capa REST.");
			throw new CividasRegistrationException("No se ha podido finalizar la solicitud de alta de registro en Cividas.");
		}

		// Se obtiene del fichero de configuración app.config la URL base de la capa
		// REST. Ojo, la ruta del fichero app.config se configura
		// en el web.xml. Los cambios sobr este fichero se cogen en caliente.
		String baseUri = appConfiguration.getRestBaseURI();
		if (baseUri == null) {
			log.error("La configuración de los formularios personalizados es incorrecta, no se puede cargar la URI de la capa REST");
			throw new CividasRegistrationException("No se ha podido finalizar la solicitud de alta de registro en Cividas.");
		}

		// Carga del servicio REST que se utilizará
		WebResource resource = client.resource(baseUri + endpoint);
					
		// Invocación de la capa REST -> Alta de registro
		String newRegistryCreated = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(String.class, baseModel.toJSON());

		// Comprobaciones del resultado devuelto por Cividas
		if (newRegistryCreated == null) {
			log.error(
					"Se ha producido un error en el proceso de registro telemático. Tras la invocación del servicio web de alta de registro se obtiene un null como respuesta. ");
			throw new CividasRegistrationException(
					"No se ha podido finalizar la solicitud de alta de registro en Cividas.");
		} else {
			log.info("¡La invocación del servicio web de alta de registro telemático ha finalizado!");
			try {
				log.info("Se procede a interpretar la respuesta del servidor...");

				// Comprobación de la información develta por el servidor. Al menos debe existir
				// código de registro!
				JSONObject jsonResult = new JSONObject(newRegistryCreated);
				if (jsonResult.getInt("code") == 1) {
					log.error(
							"Algo ha ido mal en el proceso de alta de registro. La respuesta del servidor no contiene código de registro...");
					throw new CividasRegistrationException(
							"No se ha podido finalizar la solicitud de alta de registro en Cividas.");
				}

				// Obteniendo la información de la respuesta del servidor
				JSONArray jArr = jsonResult.getJSONArray("data");
				Object idRegistry = jArr.getJSONObject(0).opt("idregistry") ;
				// Preparando la respuesta del método
				log.info("Construyendo la respuesta del método sendRequest()...");
				response.setStatus(RestResponseStatus.SUCCESS);
				response.setIdRegistry(Integer.valueOf(String.valueOf(idRegistry)));

				if(jArr.getJSONObject(0).opt("idattachmentdata") != null){
					response.setIdRegistryReport(Integer.valueOf(String.valueOf(jArr.getJSONObject(0).opt("idattachmentdata"))));
				}

				log.info("¡La invocación del método sendRequest() ha sido exitosa!");
				return response;
			} catch (JSONException ex) {
				log.error(
						"Se ha producido un error inesperado a la hora de procesar el JSON de respuesta del servicio de alta de registro ",
						ex);
				throw new CividasRegistrationException(
						"No se ha podido finalizar la solicitud de alta de registro en Cividas.");
			}
		}
	}
	
	
	private void checkInterestedBeforeInsert(){
		if ( ((InsertRegistry)baseModel).getInteresteddata() != null ){
			for (int index = 0; index < ((InsertRegistry)baseModel).getInteresteddata().length; index ++){
				if(((InsertRegistry)baseModel).getInteresteddata()[index]!=null){
					Number idIndividualsMaster = ((InsertRegistry)baseModel).getInteresteddata()[index].getIdindividualsmaster();
					String name = ((InsertRegistry)baseModel).getInteresteddata()[index].getName();
					String dni = ((InsertRegistry)baseModel).getInteresteddata()[index].getIdentificationnumber();

					if (index == 0) {
						if (!isRepresentadomanual()) {
							((InsertRegistry) baseModel).getInteresteddata()[index].setNetworknotification(Integer.parseInt(getNetworknotification()));
							((InsertRegistry) baseModel).getInteresteddata()[index].setSavecontactdata(isSaveContactData());
						} else {
							((InsertRegistry) baseModel).getInteresteddata()[index].setIsprincipal(Integer.valueOf(0));
							((InsertRegistry) baseModel).getInteresteddata()[index].setSavecontactdata(isSaveRepreContactData());
						}
					}
					if (index == 1) {
						if (!isRepresentadomanual()) {
							((InsertRegistry) baseModel).getInteresteddata()[index].setSavecontactdata(isSaveRepreContactData());
							if (getInterestedAsRepre() != null) {
								((InsertRegistry) baseModel).getInteresteddata()[index].setIdinterestedas(getInterestedAsRepre());
							}
							((InsertRegistry) baseModel).getInteresteddata()[index].setWebvisible(Integer.valueOf(1));
						} else {
							((InsertRegistry) baseModel).getInteresteddata()[index].setIsprincipal(Integer.valueOf(1));
							((InsertRegistry) baseModel).getInteresteddata()[index].setNetworknotification(Integer.parseInt(getNetworknotification()));
							((InsertRegistry) baseModel).getInteresteddata()[index].setSavecontactdata(isSaveContactData());
							((InsertRegistry) baseModel).getInteresteddata()[index].setIdregrepresentedby(((InsertRegistry) baseModel).getInteresteddata()[0].getIdindividualsmaster());
						}
					}

					// Discard interested data if not contains the minim. required fields
					if (idIndividualsMaster == null && (name == null || name.isEmpty()  || dni == null || dni.isEmpty()
							|| (!isRepresentadomanual() && index == 1 /* en este caso quitamos el representante manual si está desmarcado el check*/))){
						((InsertRegistry)baseModel).getInteresteddata()[index] = null;
					}else{
						RegistryInterestedAddress ria=((RegistryInterested)((InsertRegistry)baseModel).getInteresteddata()[index]).getInterestedaddress();
						if(ria!=null && ria.getIdindividualsmasteraddress()!=null){
							if(ria.getIdindividualsmasteraddress().toString().equals("0")){
								ria.setIdindividualsmasteraddress(null);
								// Controlamos que si la provincia y ciudad están vacías no enviarlas
								// al servidor con "" que hace que falle el registro
								if(ria.getTowncode().equalsIgnoreCase("")) {
									ria.setTowncode(null);
								}
							
								if(ria.getProvincecode().equalsIgnoreCase("")) {
									ria.setProvincecode(null);
								}

								// validamos que el código postal es un número de 5 dígitos, caso contrario lo metemos en la cadena de dirección y lo ponemos a null
								// de este modo admitiremos correctamente los códigos postales portugueses, esta es una limitación de Geiser, no de Cividas
								// en portugal un código postal válido es 4920-118 y Geiser dice tiene que corresponder a la expresiÃ³n regular "^([1-9]{2}|[0-9][1-9]|[1-9][0-9])[0-9]{3}$" y el tamaÃ±o tiene que estar entre 5 y 5
								if(ria.getPostalcode()!=null && !ria.getPostalcode().equalsIgnoreCase("")) {
									String sPostalCode=ria.getPostalcode();
									boolean validPostalCode=true;
									// validate postal Code
									if(sPostalCode.length()!=5) {
										validPostalCode=false;
									}else {
										try {  
											Double.parseDouble(sPostalCode);  
										} catch(NumberFormatException e){  
											validPostalCode= false;  
										}  
									}

									// retocamos la dirección
									if(!validPostalCode) {
									    if(ria.getAddress()!=null) {
									    	ria.setAddress(ria.getAddress()+ " Cp: "+ria.getPostalcode());
									    }
										ria.setPostalcode(null);
									}

								}

								
								
							}else{
								try {
									Map<String, Object> individualAddressQueryResult = queryCividasMaster(FieldNamesWeb.USER_ADDRESS_ENTITY_NAME, CollectionsUtils.buildMap(FieldNamesWeb.USER_ADDRESS_ID, ria.getIdindividualsmasteraddress()), new ArrayList<Object>());

									//Parseamos la dirección
									if(individualAddressQueryResult.get(FieldNamesWeb.ROADTYPE_CODE)!=null){
										ria.setRoadtypecode((String) (((ArrayList<Object>) individualAddressQueryResult.get(FieldNamesWeb.ROADTYPE_CODE)).get(0)));
									}
									if(individualAddressQueryResult.get(FieldNamesWeb.ROADTYPE_DESCRIPTION)!=null){
										ria.setRoadtypedesc((String)  (((ArrayList<Object>)individualAddressQueryResult.get(FieldNamesWeb.ROADTYPE_DESCRIPTION)).get(0)));
									}
									if(individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_ADDRESS)!=null){
										ria.setAddress((String) (((ArrayList<Object>)individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_ADDRESS)).get(0)));
									}
									if(individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_BUILDING_NUMBER)!=null){
										ria.setBuildingnumber((String) (((ArrayList<Object>)individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_BUILDING_NUMBER)).get(0)));
									}
									if(individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_BUILDING_LETTER)!=null){
										ria.setLetter((String) (((ArrayList<Object>)individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_BUILDING_LETTER)).get(0)));
									}
									if(individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_BUILDING_STAIR)!=null){
										ria.setStair((String) (((ArrayList<Object>)individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_BUILDING_STAIR)).get(0)));
									}
									if(individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_BUILDING_FLOOR)!=null){
										ria.setFloor((String) (((ArrayList<Object>)individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_BUILDING_FLOOR)).get(0)));
									}
									if(individualAddressQueryResult.get(FieldNamesWeb.FREETOWN)!=null){
										ria.setFreetown((String) (((ArrayList<Object>)individualAddressQueryResult.get(FieldNamesWeb.FREETOWN)).get(0)));
									}
									if(individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_POSTAL_CODE)!=null){
										ria.setPostalcode((String)(((ArrayList<Object>)individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_POSTAL_CODE)).get(0)));
									}
									if(individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_TOWN_CODE)!=null){
										ria.setTowncode((String) (((ArrayList<Object>)individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_TOWN_CODE)).get(0)));
									}
									if(individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_TOWN_DESC)!=null){
										ria.setTowndesc((String) (((ArrayList<Object>)individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_TOWN_DESC)).get(0)));
									}
									if(individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_PROVINCE_CODE)!=null){
										ria.setProvincecode((String)(((ArrayList<Object>)individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_PROVINCE_CODE)).get(0)));
									}
									if(individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_PROVINCE_DESC)!=null){
										ria.setProvincedesc((String)(((ArrayList<Object>) individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_PROVINCE_DESC)).get(0)));
									}
									if(individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_COUNTRY_ID)!=null){
										ria.setIdcountry((Number) (((ArrayList<Object>)individualAddressQueryResult.get(FieldNamesWeb.USER_ADDRESS_COUNTRY_ID)).get(0)));
									}

								} catch (CividasQueryDataException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							((RegistryInterested)((InsertRegistry)baseModel).getInteresteddata()[index]).setInterestedaddress(ria);
						}

					}
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	protected Map<String, Object> queryCividasMaster (String entity, Map<Object, Object> kv, List<Object> av) throws CividasQueryDataException{
		Map<String, Object> response = null;
		try {
			Client client = DefaultRestClient.getInstance();
			String baseUri = appConfiguration.getRestBaseURI();
			
			// Carga del servicio REST que se utilizará
			WebResource resource = client.resource(baseUri + "/cividas-api/query");
			
			// Se crea el mapa con los parámetros para invocar la consulta. Coapia de los parámetros por defecto y el KV y AV correspondiente
			Map<String, Object> parameters = (Map<String, Object>) ((HashMap<String, String>) queryParameters).clone();
			parameters.put("entity", entity);
			
			parameters.put("av", new JSONArray(av));
			
			if (!kv.isEmpty()){
				parameters.put("kv", new JSONObject(kv));
			}
			
			
			// Invocación de la capa REST -> Alta de registro
			String responseJSON = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(String.class, new JSONObject(parameters));	
			
			try {
				log.info("Se procede a interpretar la respuesta del servidor...");
				
				// Conversión del resultado. JSON -> Mapa
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				Map<String, Object> responseComplete =mapper.readValue(responseJSON, HashMap.class);
				
				if (responseComplete.get("code") != null && responseComplete.get("code").equals(Integer.valueOf(0))){
					response = (Map<String, Object>) responseComplete.get("data");
				}
				
			} catch (Exception ex) {
				log.error("Se ha producido un error al interpretar la respuesta del servidor al invocar una consulta de datos sobre la entidad: " + entity);
				throw new CividasQueryDataException("No se ha podido realizar la consulta sobre la entidad: " + entity);
			}
			
		} catch (Exception ex) {
			log.error("Se ha producido un error no esperado al invocar una consulta de datos sobre la entidad: " + entity);
			log.error("Error: "+ex.toString());
			throw new CividasQueryDataException("No se ha podido realizar la consulta sobre la entidad: " + entity);
		}
		return response;
	}
	

	public Map<String, String> getIndividualAddress() {
		return individualAddress;
	}
	public void setIndividualAddress(Map<String, String> individualAddress) {
		this.individualAddress = individualAddress;
	}
	public Object getReceivedParametersInJSON() {
		return receivedParametersInJSON;
	}
	
	public void setReceivedParametersInJSON(String receivedParametersInJSON) throws Exception {
		byte[] byteArr = BASE64DecoderStream.decode(receivedParametersInJSON.getBytes());
		this.receivedParametersInJSON = decrypt(byteArr);
		//this.receivedParametersInJSON = receivedParametersInJSON;
	}
	
	public byte[] stringToByte(String encryptedString) {
		String[] byteValues = encryptedString.trim().substring(1, encryptedString.length() - 1).split(",");
		return new byte[byteValues.length];
	}
	
	private static String decrypt(byte[] encryptedText) throws Exception {
	    byte[] keyBytes = "bytesGeneradosDP".getBytes();
	    Key key = new SecretKeySpec(keyBytes, "AES");
	    Cipher c = Cipher.getInstance("AES");
	    c.init(Cipher.DECRYPT_MODE, key);
	    byte[] decValue = c.doFinal(encryptedText);
	    String decryptedValue = new String(decValue);
	    return decryptedValue;
	  }
	
	public void onAddressChange(final AjaxBehaviorEvent event) {
		if ((((InsertRegistry)this.baseModel)) != null && ((InsertRegistry)this.baseModel).getInteresteddata() != null && ((InsertRegistry)this.baseModel).getInteresteddata()[0] != null && ((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress()!=null){
			if (((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress().getIdindividualsmasteraddress().equals(0)){
				this.enableAddressFields = true;
				// Default Values
				((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress().setRoadtypecode(EMPTY_ADDRESS_ROADTYPECODE);
				((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress().setIdcountry(EMPTY_ADDRESS_IDCOUNTRY);
				((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress().setProvincecode(EMPTY_ADDRESS_PROVINCECODE);
				((InsertRegistry)this.baseModel).getInteresteddata()[0].getInterestedaddress().setTowncode(EMPTY_ADDRESS_TOWNCODE);
				loadProvinces();
				loadTowns();
			}
			else{
				this.enableAddressFields = false;
			}
		}else{
			this.enableAddressFields = false;
		}
	}
	
	
	public void onRepreAddressChange(final AjaxBehaviorEvent event) {
		if ((((InsertRegistry)this.baseModel)) != null && ((InsertRegistry)this.baseModel).getInteresteddata() != null && ((InsertRegistry)this.baseModel).getInteresteddata()[1] != null 
				&& ((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress()!=null){
			if (((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().getIdindividualsmasteraddress().equals(0)){
				this.enableRepreAddressFields= true;
				// Default Values
				((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().setRoadtypecode(EMPTY_ADDRESS_ROADTYPECODE);
				((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().setIdcountry(EMPTY_ADDRESS_IDCOUNTRY);
				((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().setProvincecode(EMPTY_ADDRESS_PROVINCECODE);
				((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().setTowncode(EMPTY_ADDRESS_TOWNCODE);
				loadRepreProvinces();
				loadRepreTowns();
			}
			else{
				this.enableRepreAddressFields = false;
			}
		}else{
			this.enableRepreAddressFields = false;
		}
	}
	
	
	
	
	public boolean isEnableAddressFields() {
		return enableAddressFields;
	}
	public void setEnableAddressFields(boolean enableAddressFields) {
		this.enableAddressFields = enableAddressFields;
	}
	
	public boolean isEnableRepreAddressFields() {
		return enableRepreAddressFields;
	}
	public void setEnableRepreAddressFields(boolean enableRepreAddressFields) {
		this.enableRepreAddressFields = enableRepreAddressFields;
	}
	
	public AppConfiguration getAppConfiguration() {
		return appConfiguration;
	}
	
	public void setAppConfiguration(AppConfiguration appConfiguration) {
		this.appConfiguration = appConfiguration;
	}
	
	public LanguageBean getLanguageBean() {
		return languageBean;
	}
	public void setLanguageBean(LanguageBean languageBean) {
		this.languageBean = languageBean;
	}
	
	
	
	
	public void handleFileUpload(FileUploadEvent event) throws Exception {
		
		log.debug("uploadingfile");
		UploadedFile file = event.getFile();
		String maxFile = paramfileMaxNumber;
		String maxSizeFile = paramfileMaxSize;
		String allowedFileExtensions = paramfileExtension;
		log.debug("maxFile: " + maxFile + ", maxSizeFile: " + maxSizeFile + ", allowedFileExtensions: " + allowedFileExtensions);
		int uploadFile = 0;
		String extension = FilenameUtils.getExtension(file.getFileName());
		Long fileSize = FileUtils.getFileSize(file);
		uploadFile = FileUtils.valideFilemasterType(extension, allowedFileExtensions, maxSizeFile, fileSize);
		if(uploadFile!=0) {
			if (event.getComponent().getAttributes().get("validtype") != null) {
				log.error("No se ha podido adjuntar el documento. " + FileUtils.messageValidationFile(uploadFile));
				FacesContext.getCurrentInstance().addMessage(
						"fileupload_" + event.getComponent().getAttributes().get("validtype") ,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								getResourceMessage(FileUtils.messageValidationFile(uploadFile)), ""));
				PrimeFaces.current().executeScript("document.getElementById('text_"+event.getComponent().getAttributes().get("documenttypeid")+"').style.color = '#b94a48';");
				return;
			} else {
				log.error("No se ha podido adjuntar el documento. " + FileUtils.messageValidationFile(uploadFile));
				FacesContext.getCurrentInstance().addMessage("fileupload",
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								getResourceMessage(FileUtils.messageValidationFile(uploadFile)), ""));
				PrimeFaces.current().executeScript("document.getElementById('text_otros').style.color = '#b94a48';");
				return;
			}
		}else {
			if (event.getComponent().getAttributes().get("validtype") != null) {
				PrimeFaces.current().executeScript("document.getElementById('text_"+event.getComponent().getAttributes().get("documenttypeid")+"').style.color = '#000000';");
			}else {
				PrimeFaces.current().executeScript("document.getElementById('text_otros').style.color = '#000000';");
			}
			
		}
		if(event.getComponent().getAttributes().get("iddocumentmastertype")!=null) {
			//el documento viene con tipado
			Integer iddocumentmastertype = (Integer) event.getComponent().getAttributes().get("iddocumentmastertype");
			String documentTypeName = (String) event.getComponent().getAttributes().get("documentTypeName");
			Boolean encontrado = false;
			//comprobamos que no esté ya subido y si lo está lo sobreescribimos
			for(int i=0; i < uploadedFiles.size();i++) {
				if(uploadedFiles.get(i).getIddocumentmastertype()!= null && uploadedFiles.get(i).getIddocumentmastertype().intValue() == iddocumentmastertype) {
					encontrado = true;
					uploadedFiles.set(i, new UploadedFileWrapper(file, tmpIdCount,iddocumentmastertype,documentTypeName));
				}
			}
			if(!encontrado) {
				log.debug("uploadfile");
				this.uploadedFiles.add( new UploadedFileWrapper(file, tmpIdCount,iddocumentmastertype,documentTypeName));
			}
		}else {
			log.debug("uploadfile");
			if(maxFile!=null) {
				int uploadedFilesCount = getUploadedFilesOther().size();
				if(maxFile!=null) {
					uploadFile = FileUtils.valideMaxFile(maxFile, uploadedFilesCount);
				}
				if(uploadFile!=0) {
					log.error("No se ha podido adjuntar el documento. "+ FileUtils.messageValidationFile(uploadFile));
					FacesContext.getCurrentInstance().addMessage("fileupload", new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage(FileUtils.messageValidationFile(uploadFile)), ""));
					PrimeFaces.current().executeScript("document.getElementById('text_otros').style.color = '#b94a48';");
					return;
				}else {
					PrimeFaces.current().executeScript("document.getElementById('text_otros').style.color = '#000000';");
				}
			}
			this.uploadedFiles.add( new UploadedFileWrapper(file, tmpIdCount));
		}
		tmpIdCount++;

	}
	
	private void clearAttachments() {
		this.tmpFileIdToDelete = null;
		for (final UploadedFileWrapper fileWrapper : this.uploadedFiles) {
			try {
				fileWrapper.getFile().getInputStream().close();
			} catch (IOException e) {
				log.warn("Unable to close input stream for file {}", fileWrapper.getFile().getFileName(), e);
			}
		}
		this.uploadedFiles.clear();
	}

	public List<UploadedFileWrapper> getUploadedFiles() {
		return uploadedFiles;
	}

	public void setUploadedFiles(List<UploadedFileWrapper> uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}
	
	public void removeFile() {
		if (this.tmpFileIdToDelete != null) {
			for (int i = 0; i < this.uploadedFiles.size(); i++) {
				if (this.uploadedFiles.get(i).getTmpId().equals(this.tmpFileIdToDelete.intValue())) {
					this.uploadedFiles.get(i).setAttachmentdatadesc("");
					this.uploadedFiles.remove(i);
					break;
				}
			}
			tmpFileIdToDelete = null;
		}
	}

	public Number getTmpFileIdToDelete() {
		return tmpFileIdToDelete;
	}

	public void setTmpFileIdToDelete(Number tmpFileIdToDelete) {
		this.tmpFileIdToDelete = tmpFileIdToDelete;
	}
	
	
	public boolean isSaveContactData() {
		return saveContactData;
	}

	public void setSaveContactData(boolean saveContactData) {
		this.saveContactData = saveContactData;
	}


	public LinkedHashMap<String, String> getRoadtypes() {
		return roadtypes;
	}


	public void setRoadtypes(LinkedHashMap<String, String> roadtypes) {
		this.roadtypes = roadtypes;
	}


	public LinkedHashMap<String, String> getCountries() {
		return countries;
	}


	public void setCountries(LinkedHashMap<String, String> countries) {
		this.countries = countries;
	}


	public LinkedHashMap<String, String> getProvinces() {
		return provinces;
	}


	public void setProvinces(LinkedHashMap<String, String> provinces) {
		this.provinces = provinces;
	}


	public LinkedHashMap <String, String> getTowns() {
		return towns;
	}


	public void setTowns(LinkedHashMap <String, String> towns) {
		this.towns = towns;
	}
	
	public String getNetworknotification() {
		return networknotification;
	}

	public void setNetworknotification(String networknotification) {
		this.networknotification = networknotification;
	}
	
	public boolean isCif(){
		if(((InsertRegistry) this.baseModel).getInteresteddata()[0].getIdentificationnumber()!=null){
			return CIFUtils.isCIFWellFormed(((InsertRegistry) this.baseModel).getInteresteddata()[0].getIdentificationnumber());
		}
		return false;
	}


	public String getIdRepreInterested() {
		return idRepreInterested;
	}


	public void setIdRepreInterested(String idRepreInterested) {
		this.idRepreInterested = idRepreInterested;
	}


	public LinkedHashMap<String, String> getRepreProvinces() {
		return repreProvinces;
	}


	public void setRepreProvinces(LinkedHashMap<String, String> repreProvinces) {
		this.repreProvinces = repreProvinces;
	}


	public LinkedHashMap<String, String> getRepreTowns() {
		return repreTowns;
	}


	public void setRepreTowns(LinkedHashMap<String, String> repreTowns) {
		this.repreTowns = repreTowns;
	}
	
	public boolean isSaveRepreContactData() {
		return saveRepreContactData;
	}

	public void setSaveRepreContactData(boolean saveRepreContactData) {
		this.saveRepreContactData = saveRepreContactData;
	}


	public Map<String, String> getIndividualRepreAddress() {
		return individualRepreAddress;
	}


	public void setIndividualRepreAddress(Map<String, String> individualRepreAddress) {
		this.individualRepreAddress = individualRepreAddress;
	}


	public RestResponseModel getRespuesta() {
		return respuesta;
	}


	public void setRespuesta(RestResponseModel respuesta) {
		this.respuesta = respuesta;
	}

	/**
	 * Paso del idRegistry para cargar en la pagina registryInterestedError
	 * @param idRegistry 
	 * @return
	 * @throws CividasRegistrationException
	 * @throws UniformInterfaceException
	 * @throws ClientHandlerException
	 * @throws JsonProcessingException
	 */
	protected RestResponseModel requestRegistryTypeCodeError(Number idRegistry) throws CividasRegistrationException, UniformInterfaceException,
		ClientHandlerException, JsonProcessingException {

		RestResponseModel response = new RestResponseModel();

		// Comprobaciones del resultado devuelto por Cividas
		if (idRegistry == null) {
			log.error(
					"Se ha producido un error al recuperar el id del registro.");
			throw new CividasRegistrationException(
					"No se ha podido consultar la solicitud.");
		} else {
				// Preparando la respuesta del método
				log.info("Construyendo la respuesta del método sendRequest()...");
				response.setIdRegistry(idRegistry);

				log.info("¡La invocación del método sendRequest() ha sido exitosa!");
				return response;
		}
	}
	// Representante cubierto manualmente
	public void onmanualCountryRepreChange(final AjaxBehaviorEvent event) {
		// Borramos la provincia y la ciudad elegidas anteriormente
				if(((InsertRegistry)this.baseModel).getInteresteddata()[1]!=null &&((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress()!=null){
					((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().setProvincecode(null);
					((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().setTowncode(null);
				}
		loadmanualRepreProvinces();
		loadmanualRepreTowns();
	}
	
	
	public void onmanualProvinceRepreChange(final AjaxBehaviorEvent event) {
		loadmanualRepreTowns();
	}
	
	
	public void loadmanualRepreProvinces(){
		try {
			if(((InsertRegistry)this.baseModel).getInteresteddata()[1]!=null 
					&&((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress()!=null 
					&&((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().getIdcountry()!=null){
				Map<String, Object> provincesQueryResult = queryCividasMaster(FieldNamesWeb.PROVINCE_ENTITY_NAME, 
						CollectionsUtils.buildMap(FieldNamesWeb.USER_ADDRESS_COUNTRY_ID, ((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().getIdcountry()), new ArrayList<Object>());
				this.repremanualProvinces = MastersUtils.parseMasterLinkedHashMap(provincesQueryResult, FieldNamesWeb.USER_ADDRESS_PROVINCE_CODE, FieldNamesWeb.USER_ADDRESS_PROVINCE_DESC);//  idcountry
			}
		} catch (CividasQueryDataException e) {
			log.error("Se ha producido un error al cargar el maestro de provincias del representante. A continuación se mostrará la traza del error", e);
		}	
	}
	
	public void loadmanualRepreTowns(){
		Map<String, Object> townsQueryResult;
		try {
			if(((InsertRegistry)this.baseModel).getInteresteddata()[1]!=null 
					&&((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress()!=null 
					&&((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().getProvincecode()!=null){
				townsQueryResult = queryCividasMaster("maintenance.ETowns", CollectionsUtils.buildMap(FieldNamesWeb.USER_ADDRESS_PROVINCE_CODE, 
						((InsertRegistry)this.baseModel).getInteresteddata()[1].getInterestedaddress().getProvincecode()), new ArrayList<Object>());
				this.repremanualTowns = MastersUtils.parseMasterLinkedHashMap(townsQueryResult, FieldNamesWeb.USER_ADDRESS_TOWN_CODE, FieldNamesWeb.USER_ADDRESS_TOWN_DESC);//provincecode
			}else{
				this.repreTowns=new LinkedHashMap<String,String>();
			}
		} catch (CividasQueryDataException e) {
			log.error("Se ha producido un error al cargar el maestro de ciudades del representante. A continuación se mostrará la traza del error", e);
		}	
	}
	
	public void loadrelatedforms(){
		try {
			if(((InsertRegistry)this.baseModel).getIdbduacproceduretype()!=null){

				ArrayList<Object> aQuery = new ArrayList<Object>();
				aQuery.add("idregulation");
				aQuery.add("title_lg1");
				
				Map<String, Object> requiredformsQueryResult = queryCividasMaster("regulations.ERegulationsCatalogSelectCatalog",
				CollectionsUtils.buildMap(FieldNamesWeb.IDBDUACPROCEDURETYPE, ((InsertRegistry)this.baseModel).getIdbduacproceduretype(),"selected", (Object)new Integer(1)), aQuery);
				LinkedHashMap<String, String> listarequeridos = MastersUtils.parseMasterLinkedHashMap(requiredformsQueryResult,"idregulation", "title_lg1");
				
				if(listarequeridos!=null && listarequeridos.size()>0) {
					for (Map.Entry<String, String> entry : listarequeridos.entrySet()) {
						String key = entry.getKey();
						RelatedForm relatedform=new RelatedForm();
						ArrayList<Object> aQuery2 = new ArrayList<Object>();
						aQuery2.add("idregulation");
						aQuery2.add("regulationtitle_lg1");
						aQuery2.add("regulationtitle_lg2");
						aQuery2.add("regulationobservations_lg1");
						aQuery2.add("regulationobservations_lg2");
						Map<String, Object> requiredformQueryResult = queryCividasMaster("regulations.ERegulations",CollectionsUtils.buildMap("idregulation", (Object)new Integer(key)), aQuery2);
						Object keyColumn = requiredformQueryResult.get("idregulation");
						LinkedHashMap <String, String> parsedMap =new LinkedHashMap<String, String>();
						if (keyColumn instanceof List<?>){
							for (int index = 0; index < ((List<String>)keyColumn).size(); index++){
								relatedform.setIdregulation(key);
								relatedform.setRegulationtitle_lg1(((List<String>)requiredformQueryResult.get("regulationtitle_lg1")).get(index));
								relatedform.setRegulationtitle_lg2(((List<String>)requiredformQueryResult.get("regulationtitle_lg2")).get(index));
								relatedform.setRegulationobservations_lg1(((List<String>)requiredformQueryResult.get("regulationobservations_lg1")).get(index));
								relatedform.setRegulationobservations_lg2(((List<String>)requiredformQueryResult.get("regulationobservations_lg2")).get(index));
								relatedforms.add(relatedform);
							}
						}
					}
				}
			}
		} catch (CividasQueryDataException e) {
			log.error("Se ha producido un error al cargar el maestro de provincias del representante. A continuación se mostrará la traza del error", e);
		}	
	}

	public void handleChangeFileDesc(String tmpId){
		log.debug("File description changed");
	}

	public Number getSignatureverification() {
		return signatureverification;
	}

	public void setSignatureverification(Number signatureverification) {
		this.signatureverification = signatureverification;
	}
	
	public void showError(String key) {
		FacesContext fc = FacesContext.getCurrentInstance();
		String msg = null;
		try {
			i18n = fc.getApplication().evaluateExpressionGet(fc, "#{i18n}", ResourceBundle.class);
			msg = i18n.getString(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
		fc.renderResponse();
	}
	
	public LinkedHashMap<String, String> getRepremanualProvinces() {
		return repremanualProvinces;
	}

	public void setRepremanualProvinces(LinkedHashMap<String, String> repremanualProvinces) {
		this.repremanualProvinces = repremanualProvinces;
	}

	public LinkedHashMap<String, String> getRepremanualTowns() {
		return repremanualTowns;
	}

	public void setRepremanualTowns(LinkedHashMap<String, String> repremanualTowns) {
		this.repremanualTowns = repremanualTowns;
	}

	public int getSecondinterested() {
		return secondinterested;
	}

	public void setSecondinterested(int secondinterested) {
		this.secondinterested = secondinterested;
		if (this.secondinterested == 1) {
//			setRepresentantemanual(true);
			setRepresentadomanual(false);
		} else if (this.secondinterested == 2) {
//			setRepresentantemanual(false);
			setRepresentadomanual(true);
		} else {
//			setRepresentantemanual(false);
			setRepresentadomanual(false);
		}
	}


	public boolean isRepresentadomanual() {
		return representadomanual;
	}

	public void setRepresentadomanual(boolean representadomanual) {
		this.representadomanual = representadomanual;
	}

	public List<RelatedForm> getRelatedforms() {
		return relatedforms;
	}

	public void setRelatedforms(List<RelatedForm> relatedforms) {
		this.relatedforms = relatedforms;
	}

	public String getBaseurl() {
		return baseurl;
	}

	public void setBaseurl(String baseurl) {
		this.baseurl = baseurl;
	}
	
	public Number getInterestedAsRepre() {
		return interestedAsRepre;
	}

	public void setInterestedAsRepre(Number interestedAsRepre) {
		this.interestedAsRepre = interestedAsRepre;
	}
	
	
	
	@SuppressWarnings("unchecked")
	protected Map<String, Object> updateEntity (String entity, Map<Object, Object> kv, Map<Object, Object> av) throws CividasQueryDataException{
		Map<String, Object> response = null;
		if (!kv.isEmpty() && !av.isEmpty()){ 
			try {
				Client client = DefaultRestClient.getInstance();
				String baseUri = appConfiguration.getRestBaseURI();
				log.info("Llamo al rest: "+baseUri);
				// Carga del servicio REST que se utilizará
				WebResource resource = client.resource(baseUri + "/cividas-api/update");

				// Se crea el mapa con los parámetros para invocar el update. Copia de los parámetros por defecto y el KV y AV correspondiente
				Map<String, Object> parameters = (Map<String, Object>) ((HashMap<String, String>) queryParameters).clone();
				parameters.put("entity", entity);
				parameters.put("av", new JSONObject(av));
				parameters.put("kv", new JSONObject(kv));



				// Invocación de la capa REST -> Alta de registro
				String responseJSON = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(String.class, new JSONObject(parameters));

				try {
					log.info("Se procede a interpretar la respuesta del servidor...");

					// Conversión del resultado. JSON -> Mapa
					ObjectMapper mapper = new ObjectMapper();
					mapper.setSerializationInclusion(Include.NON_NULL);
					Map<String, Object> responseComplete =mapper.readValue(responseJSON, HashMap.class);

					if (responseComplete.get("code") != null && responseComplete.get("code").equals(Integer.valueOf(0))){
						response = (Map<String, Object>) responseComplete.get("data");
					}

				} catch (Exception ex) {
					log.error("Se ha producido un error al interpretar la respuesta del servidor al invocar una consulta de datos sobre la entidad: " + entity);
					throw new CividasQueryDataException("No se ha podido realizar el update sobre la entidad: " + entity);
				}

			} catch (Exception ex) {
				log.error("Se ha producido un error no esperado al invocar un update de datos sobre la entidad: " + entity);
				log.error("Error: "+ex.toString());
				throw new CividasQueryDataException("No se ha podido realizar el update sobre la entidad: " + entity);
			}
		}
		return response;
	}

	protected Map<String, Object> getReceivedParameters() {
		return receivedParameters;
	}
	
	
	
	
	
	
	
	/**
	 * Obtiene el id del último expediente de convocatoria relacionado con el tipo de expediente de la solicitud actual efrelexphijoconvocatoria
	 * @return 
	 */
	public Number getExpedienteConvocatoria() {
		Number expedienteConv=null;

		try {
			if(getReceivedParameters().get(FieldNamesWeb.IDBDUACPROCEDURETYPE)!=null){
				Number idBduacProcedureType=null;
				if(getReceivedParameters().get(FieldNamesWeb.IDBDUACPROCEDURETYPE) instanceof String) {
					idBduacProcedureType=(Number)Integer.valueOf((String) getReceivedParameters().get(FieldNamesWeb.IDBDUACPROCEDURETYPE));
				}else {
					idBduacProcedureType=(Number) getReceivedParameters().get(FieldNamesWeb.IDBDUACPROCEDURETYPE);
				}

				if(idBduacProcedureType!=null) {
					// Cogemos el id del expediente asociado al procedimiento del catálogo

					HashMap hkeysbduac=new HashMap<Object, Object>();
					hkeysbduac.put("idbduacproceduretype", idBduacProcedureType);
					List listaCampos=new ArrayList<Object>();
					listaCampos.add("idprocedure");
					Map<String, Object> procedure = queryCividasMaster("efrelexphijoconvocatoria",hkeysbduac,listaCampos );
					if(procedure != null && procedure.get("idprocedure") != null && ((ArrayList)procedure.get("idprocedure")).get(0)!=null) {
						Map<String, Object> listProcedure = MastersUtils.parseMasterMapObj(procedure, "idbduacproceduretype","idprocedure");
						expedienteConv=NumberFormat.getInstance().parse(String.valueOf(listProcedure.get(idBduacProcedureType.toString())));
					}
				}
			}
		} catch (CividasQueryDataException | ParseException cqe) {
			log.error("Se ha producido un error al buscar la convocatoria relacionada con esta solicitud",cqe);
		}
		return expedienteConv;
	}
	
	
	/*
	 * Método para poner como expediente padre de una solicitud a la convocatoria correspondiente
	 */
	protected Map<String, Object> updateProcedureParent () throws ParseException, CividasQueryDataException{
		Map<String, Object> res = null;
		if(idprocedureparent!=null && respuesta!=null && respuesta.getIdRegistry()!=null) {
			HashMap kv= new HashMap<Object, Object>();
			kv.put("idregistryfirst", respuesta.getIdRegistry());
			List listaCamposprocedures=new ArrayList<Object>();
			listaCamposprocedures.add("idprocedure");
			listaCamposprocedures.add("idregistryfirst");
			Map<String, Object> procedures = queryCividasMaster("procedures.EProcedures",kv,listaCamposprocedures );
			Map<String, Object> listProcedures = MastersUtils.parseMasterMapObj(procedures, "idregistryfirst","idprocedure");
			Number idProcedure=NumberFormat.getInstance().parse(String.valueOf(listProcedures.get( respuesta.getIdRegistry().toString())));

			// Update, pide el idprocedure para el update
			HashMap kvupdate= new HashMap<Object, Object>();
			kvupdate.put("idprocedure", idProcedure);
			HashMap av= new HashMap<Object, Object>();
			av.put("idprocedureparent", idprocedureparent);
			res=updateEntity("procedures.EProcedures", kvupdate, av);
		}
		return res;
	}
	
	public List<RegistryDocumentTrype> getDocumentTypesMandatory() {
		return documentTypesMandatory;
	}
	
	public Integer getDocumentTypesMandatorySize() {
		return documentTypesMandatory == null ? 0 :documentTypesMandatory.size();
	}

	public void setDocumentTypesMandatory(List<RegistryDocumentTrype> documentTypesMandatory) {
		this.documentTypesMandatory = documentTypesMandatory;
	}

	public List<RegistryDocumentTrype> getDocumentTypesOptional() {
		return documentTypesOptional;
	}
	
	public Integer getDocumentTypesOptionalSize() {
		return documentTypesOptional == null ? 0 : documentTypesOptional.size();
	}

	public void setDocumentTypesOptional(List<RegistryDocumentTrype> documentTypesOptional) {
		this.documentTypesOptional = documentTypesOptional;
	}
	
	public List<UploadedFileWrapper> getUploadedFilesOther() {
		ArrayList<UploadedFileWrapper> result = new ArrayList<UploadedFileWrapper>();
		for(UploadedFileWrapper file:uploadedFiles) {
			if(file.getIddocumentmastertype()==null) {
				result.add(file);
			}
		}
		return result;
	}
	public List<UploadedFileWrapper> getDocumentByType(Integer iddocumentmastertype) {
		ArrayList<UploadedFileWrapper> result = new ArrayList<UploadedFileWrapper>();
		for(UploadedFileWrapper file:uploadedFiles) {
			if(file.getIddocumentmastertype()!=null && file.getIddocumentmastertype()==iddocumentmastertype) {
				result.add(file);
			}
		}
		return result;
	}
	
	public String getMandatoryFilesUploaded() {
		for(RegistryDocumentTrype tipo:documentTypesMandatory) {
			Integer id = tipo.getIddocumentmastertype();
			Boolean encontrado=false;
			for(UploadedFileWrapper file : uploadedFiles) {
				if(file.getIddocumentmastertype()!=null&&file.getIddocumentmastertype().intValue()==id) {
					encontrado=true;
				}
			}
			if(!encontrado) {
				return "";
			}
		}
		return "ok";
	}
	
	public void setMandatoryFilesUploaded(String value) {}

	public void updateFilesBase64() {
		List<String> result = new ArrayList<String>();
		for(UploadedFileWrapper file : uploadedFiles) {
			result.add(file.getFileBase64());
		}
		filesBase64 = result.toString();
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("filesB64:filesBase64");
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("finalList");
	}

	public String getFilesBase64() {
		return filesBase64;
	}
	public void setFilesBase64(String value) {
		this.filesBase64 = value;
	}


	public boolean isSignActive() {
		if(paramSign && uploadedFiles.size()>0) {
			signActive=true;
		}else {
			signActive=false;
		}
		return signActive;
	}


	public void setSignActive(boolean signActive) {
		this.signActive = signActive;
	}
	
	public String getFileMaxSize() {
		if(paramfileMaxSize!=null) {
			Double attachmentMaxSizeNum = new Double(paramfileMaxSize);			
			this.fileMaxSize=String.format("%1$.2f", attachmentMaxSizeNum / (1024 * 1024));
		}else {
			fileMaxSize="";
		}
		return fileMaxSize;
	}


	public void setFileMaxSize(String fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}
	
	public String getFileMaxNumber() {
		if(paramfileMaxNumber!=null) {
			this.fileMaxNumber=paramfileMaxNumber;
		}else {
			fileMaxNumber="";
		}
		return fileMaxNumber;
	}


	public void setFileMaxNumber(String fileMaxNumber) {
		this.fileMaxNumber = fileMaxNumber;
	}
	
	public String getFileExtension() {
		if(paramfileExtension!=null) {
			this.fileExtension=paramfileExtension;
		}else {
			fileExtension="";
		}
		return fileExtension;
	}


	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
	public String getAfirmaUrl() {
		return afirmaUrl;
	}

	public void setAfirmaUrl(String afirmaUrl) {
		this.afirmaUrl = afirmaUrl;
	}

	public String getFilesBase64Signed() {
		return filesBase64Signed;
	}

	public void setFilesBase64Signed(String filesBase64Signed) {
		this.filesBase64Signed = filesBase64Signed;
	}

	public String getResumeBase64Signed() {
		return resumeBase64Signed;
	}

	public void setResumeBase64Signed(String resumeBase64Signed) {
		this.resumeBase64Signed = resumeBase64Signed;
	}

	public int getUploadedFilesSize() {
		return getUploadedFiles().size();
	}

	public void setUploadedFilesSize(int uploadedFilesSize) {
		this.uploadedFilesSize = uploadedFilesSize;
	}
}

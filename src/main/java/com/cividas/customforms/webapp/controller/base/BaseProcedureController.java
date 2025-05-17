package com.cividas.customforms.webapp.controller.base;

import java.io.IOException;
import java.io.Serializable;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.application.Application;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cividas.customforms.webapp.common.exceptions.CividasQueryDataException;
import com.cividas.customforms.webapp.common.model.base.IRegistryData;
import com.cividas.customforms.webapp.common.model.base.InsertProcedure;
import com.cividas.customforms.webapp.common.model.base.RegistryAttachment;
import com.cividas.customforms.webapp.common.model.base.RegistryInterested;
import com.cividas.customforms.webapp.common.model.base.RegistryInterestedAddress;
import com.cividas.customforms.webapp.common.model.base.RegistryTask;
import com.cividas.customforms.webapp.configuration.AppConfiguration;
import com.cividas.customforms.webapp.utils.CollectionsUtils;
import com.cividas.customforms.webapp.utils.DefaultRestClient;
import com.cividas.customforms.webapp.utils.LanguageBean;
import com.cividas.web.common.FieldNamesWeb;
import com.cividas.web.common.utils.CIFUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.mail.util.BASE64DecoderStream;

/**
* [CE-1024] IFrame base para el detalle de los expedientes
* 
* @author juanjo.alvarez
* @since 28/11/2019
*/

public abstract class BaseProcedureController implements Serializable {
	
	private static final long serialVersionUID = -8711865278713531133L;
	protected static final Logger log = LoggerFactory.getLogger(BaseController.class);
	
	@ManagedProperty(value = "#{appConfiguration}")
	AppConfiguration appConfiguration;
	
	@ManagedProperty(value = "#{languageBean}")
	LanguageBean languageBean;
	
	
	// Interfaz que define cual será el tipo de solicitud a tratar. Todos los modelos de datos de solicitudes
	// que quieran hacer uso del BaseController deberían implementar esta interfaz.
	protected IRegistryData baseModel;
	
	// Datos relativos a la recepción de parámetros de la sede
	private String receivedParametersInJSON = null;
	protected Map<String, Object> receivedParameters = null;
	protected Map<String, String> queryParameters = null;
	
	@PostConstruct
	public void init(){
		// No hay garantías de que en este momento ya estén cargados los parámetros enviados desde la sede
		
		// Inicialización del modelo de datos
		((InsertProcedure)baseModel).setInteresteddata(new RegistryInterested[2]);
		((InsertProcedure)baseModel).getInteresteddata()[0] = new RegistryInterested();
		((InsertProcedure)baseModel).getInteresteddata()[0].setInterestedaddress(new RegistryInterestedAddress());
		((InsertProcedure)baseModel).getInteresteddata()[0].setIsprincipal(Integer.valueOf(1));
		((InsertProcedure)baseModel).getInteresteddata()[1] = new RegistryInterested();
		((InsertProcedure)baseModel).getInteresteddata()[1].setInterestedaddress(new RegistryInterestedAddress());
		((InsertProcedure)baseModel).getInteresteddata()[1].setIsprincipal(Integer.valueOf(0));
	}
	

	protected String receiveParentData(){
		// Recibe, procesa y almacena los parámetros POST enviados desde la sede
		receivePostParametersFromParentWeb();
//		log.info("Parámetros POST procesados correctamente. Información de la solicitud: { idindividualsmaster: " + receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER).toString() + ", username: " + receivedParameters.get(FieldNamesWeb.USERNAME) + " }");
		
		// Se procede con la carga de maestros, solo en caso de que en los datos recibidos haya un sessionID, un username y un idindividualsmaster
		if (receivedParameters != null && receivedParameters.get(FieldNamesWeb.SESSIONID) != null && receivedParameters.get(FieldNamesWeb.USERNAME) != null && receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER) != null){
			// Llamada al método para cargar los maestros. Por defecto, se cargará la dirección, en caso de que el formulario
			// personalizado necesite más maestros debe sobreescribir este método.
		}else{
			log.info("Se omite la carga de maestros ya que no existen datos de sesión.");
		}
		
		return null;
	}
	
	protected Map<Object, Object> getDataFromParameters(){
		// Recibe, procesa y almacena los parámetros POST enviados desde la sede
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		Map<Object, Object> mParams = new HashMap<>();
		
		try {
			if(receivedParametersInJSON != null) {
				this.receivedParameters = mapper.readValue(receivedParametersInJSON, HashMap.class);
			}else{
				this.receivedParameters = new HashMap<>();
				return mParams;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mParams;
	}
	
	
	@SuppressWarnings("unchecked")
	private void receivePostParametersFromParentWeb (){
		try {
			// Conversión del JSON recibido a un mapa de valores
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			if(receivedParametersInJSON != null) {
				this.receivedParameters = mapper.readValue(receivedParametersInJSON, HashMap.class);
				processsReceivedParameters();
			}
			
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
		if(receivedParameters.get(FieldNamesWeb.ID_PROCEDURE)!=null){
			Number idprocedure=null;
			if(receivedParameters.get(FieldNamesWeb.ID_PROCEDURE) instanceof String) {
				idprocedure=Integer.valueOf((String) receivedParameters.get(FieldNamesWeb.ID_PROCEDURE));
			}else {
				idprocedure=(Number) receivedParameters.get(FieldNamesWeb.ID_PROCEDURE);
			}
			((InsertProcedure)this.baseModel).setIdprocedure(idprocedure);
		}
		
		log.info("Parámetros recividos:"+receivedParameters.toString());
		
		loadInterestedData();
		loadBduacData();
		loadAttachments();
		loadTasks();
	}
	
	private void processsReceivedParameters(){
		if(receivedParameters.get(FieldNamesWeb.IS_REPRESENTATION)==null ||  !(Boolean)(receivedParameters.get(FieldNamesWeb.IS_REPRESENTATION))){
			setRepresentedInfo();
		}else{
			setUserInfo();
		}
		if (receivedParameters.get(FieldNamesWeb.LANG) != null && getLanguageBean() != null){
			getLanguageBean().setLang((String) receivedParameters.get(FieldNamesWeb.LANG));
		}
		if(receivedParameters.get(FieldNamesWeb.ID_PROCEDURE) != null) {
			setProcedureInfo();
		}
	}
	
	private void setProcedureInfo() {
		if (receivedParameters.get(FieldNamesWeb.PROCEDURECODE) != null && !receivedParameters.get(FieldNamesWeb.PROCEDURECODE).equals("")) {
			((InsertProcedure) this.baseModel).setProcedurecode((String) receivedParameters.get(FieldNamesWeb.PROCEDURECODE));
		}
		if (receivedParameters.get(FieldNamesWeb.PROCEDURETYPEFRAMEURL) != null && !receivedParameters.get(FieldNamesWeb.PROCEDURETYPEFRAMEURL).equals("")) {
			((InsertProcedure) this.baseModel).setProceduretypeframeurl((String) receivedParameters.get(FieldNamesWeb.PROCEDURETYPEFRAMEURL));
		}
		if (receivedParameters.get(FieldNamesWeb.PROCEDURETYPEFRAMEENTITY) != null && !receivedParameters.get(FieldNamesWeb.PROCEDURETYPEFRAMEENTITY).equals("")) {
			Number proceduretypeframeentity=null;
			if(receivedParameters.get(FieldNamesWeb.PROCEDURETYPEFRAMEENTITY) instanceof String) {
				proceduretypeframeentity=Integer.valueOf((String) receivedParameters.get(FieldNamesWeb.PROCEDURETYPEFRAMEENTITY));
			}else {
				proceduretypeframeentity=(Number) receivedParameters.get(FieldNamesWeb.PROCEDURETYPEFRAMEENTITY);
			}
			((InsertProcedure) this.baseModel).setProceduretypeframeentity(proceduretypeframeentity);
		}
		if (receivedParameters.get(FieldNamesWeb.PROCEDURETYPEFRAMEENTITYNAME) != null && !receivedParameters.get(FieldNamesWeb.PROCEDURETYPEFRAMEENTITYNAME).equals("")) {
			((InsertProcedure) this.baseModel).setProceduretypeframeentityname((String) receivedParameters.get(FieldNamesWeb.PROCEDURETYPEFRAMEENTITYNAME));
		}
		if (receivedParameters.get(FieldNamesWeb.PROCEDURETYPE_CODE) != null && !receivedParameters.get(FieldNamesWeb.PROCEDURETYPE_CODE).equals("")) {
			((InsertProcedure) this.baseModel).setProceduretypecode((String) receivedParameters.get(FieldNamesWeb.PROCEDURETYPE_CODE));
		}
		if (receivedParameters.get(FieldNamesWeb.CREATIONDATE) != null && !receivedParameters.get(FieldNamesWeb.CREATIONDATE).equals("")) {
			((InsertProcedure) this.baseModel).setCreationdate((String) receivedParameters.get(FieldNamesWeb.CREATIONDATE));
		}
		if (receivedParameters.get(FieldNamesWeb.CREATIONDATE) != null && !receivedParameters.get(FieldNamesWeb.CREATIONDATE).equals("")) {
			((InsertProcedure) this.baseModel).setCreationdate((String) receivedParameters.get(FieldNamesWeb.CREATIONDATE));
		}
		if (receivedParameters.get(FieldNamesWeb.APPLICANTSUBJECT) != null && !receivedParameters.get(FieldNamesWeb.APPLICANTSUBJECT).equals("")) {
			((InsertProcedure) this.baseModel).setApplicantsubject((String) receivedParameters.get(FieldNamesWeb.APPLICANTSUBJECT));
		}
		if (receivedParameters.get(FieldNamesWeb.SERVICEGROUPCODE) != null && !receivedParameters.get(FieldNamesWeb.SERVICEGROUPCODE).equals("")) {
			((InsertProcedure) this.baseModel).setServicegroupcode((String) receivedParameters.get(FieldNamesWeb.SERVICEGROUPCODE));
		}
		if (receivedParameters.get(FieldNamesWeb.PROCEDURETYPE_DESC) != null && !receivedParameters.get(FieldNamesWeb.PROCEDURETYPE_DESC).equals("")) {
			((InsertProcedure) this.baseModel).setProceduretypedesc((String) receivedParameters.get(FieldNamesWeb.PROCEDURETYPE_DESC));
		}
		if (receivedParameters.get(FieldNamesWeb.IDSERVICEGROUP) != null && !receivedParameters.get(FieldNamesWeb.IDSERVICEGROUP).equals("")) {
			Number idservicegroup=null;
			if(receivedParameters.get(FieldNamesWeb.IDSERVICEGROUP) instanceof String) {
				idservicegroup=Integer.valueOf((String) receivedParameters.get(FieldNamesWeb.IDSERVICEGROUP));
			}else {
				idservicegroup=(Number) receivedParameters.get(FieldNamesWeb.IDSERVICEGROUP);
			}
			((InsertProcedure) this.baseModel).setIdservicegroup(idservicegroup);
		}
		if (receivedParameters.get(FieldNamesWeb.SERVICEGROUPNAME) != null && !receivedParameters.get(FieldNamesWeb.SERVICEGROUPNAME).equals("")) {
			((InsertProcedure) this.baseModel).setServicegroupname((String) receivedParameters.get(FieldNamesWeb.SERVICEGROUPNAME));
		}
		if (receivedParameters.get(FieldNamesWeb.STATEDESCRIPTION) != null && !receivedParameters.get(FieldNamesWeb.STATEDESCRIPTION).equals("")) {
			((InsertProcedure) this.baseModel).setStatedescription((String) receivedParameters.get(FieldNamesWeb.STATEDESCRIPTION));
		}
		if (receivedParameters.get(FieldNamesWeb.IDSERVICEGROUP_RESPONSIBLE) != null && !receivedParameters.get(FieldNamesWeb.IDSERVICEGROUP_RESPONSIBLE).equals("")) {
			Number idservicegroupresponsible=null;
			if(receivedParameters.get(FieldNamesWeb.IDSERVICEGROUP_RESPONSIBLE) instanceof String) {
				idservicegroupresponsible=Integer.valueOf((String) receivedParameters.get(FieldNamesWeb.IDSERVICEGROUP_RESPONSIBLE));
			}else {
				idservicegroupresponsible=(Number) receivedParameters.get(FieldNamesWeb.IDSERVICEGROUP_RESPONSIBLE);
			}
			((InsertProcedure) this.baseModel).setIdservicegroupresponsible(idservicegroupresponsible);
		}
		if (receivedParameters.get(FieldNamesWeb.PROCEDURETYPEDESCWEB_LG1) != null && !receivedParameters.get(FieldNamesWeb.PROCEDURETYPEDESCWEB_LG1).equals("")) {
			((InsertProcedure) this.baseModel).setProceduretypedescweb_lg1((String) receivedParameters.get(FieldNamesWeb.PROCEDURETYPEDESCWEB_LG1));
		}
		if (receivedParameters.get(FieldNamesWeb.PROCEDURETYPEDESCWEB_LG2) != null && !receivedParameters.get(FieldNamesWeb.PROCEDURETYPEDESCWEB_LG2).equals("")) {
			((InsertProcedure) this.baseModel).setProceduretypedescweb_lg2((String) receivedParameters.get(FieldNamesWeb.PROCEDURETYPEDESCWEB_LG2));
		}
		if (receivedParameters.get(FieldNamesWeb.LANGUAGE) != null && !receivedParameters.get(FieldNamesWeb.LANGUAGE).equals("")) {
			((InsertProcedure) this.baseModel).setLanguage((String) receivedParameters.get(FieldNamesWeb.LANGUAGE));
		}
		if (receivedParameters.get(FieldNamesWeb.DOSSIER_STATUS_DESC_WEB_ES) != null && !receivedParameters.get(FieldNamesWeb.DOSSIER_STATUS_DESC_WEB_ES).equals("")) {
			((InsertProcedure) this.baseModel).setDossierstatusdescweb_lg1((String) receivedParameters.get(FieldNamesWeb.DOSSIER_STATUS_DESC_WEB_ES));
		}
		if (receivedParameters.get(FieldNamesWeb.DOSSIER_STATUS_DESC_WEB_GL) != null && !receivedParameters.get(FieldNamesWeb.DOSSIER_STATUS_DESC_WEB_GL).equals("")) {
			((InsertProcedure) this.baseModel).setDossierstatusdescweb_lg2((String) receivedParameters.get(FieldNamesWeb.DOSSIER_STATUS_DESC_WEB_GL));
		}
	}
	
	private void setRepresentedInfo() {
		if (receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER) != null && !receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER).equals("")) {
			Number idIndividualsMaster=null;
			if(receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER) instanceof String) {
				idIndividualsMaster=Integer.valueOf((String) receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER));
			}else {
				idIndividualsMaster=(Number) receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER);
			}
			((InsertProcedure) this.baseModel).getInteresteddata()[0].setIdindividualsmaster(idIndividualsMaster);
		}
		if (receivedParameters.get(FieldNamesWeb.IDENTIFICATIONNUMBER) != null) {
			((InsertProcedure) this.baseModel).getInteresteddata()[0].setIdentificationnumber((String) receivedParameters.get(FieldNamesWeb.IDENTIFICATIONNUMBER));
		}
		if (receivedParameters.get(FieldNamesWeb.NAME) != null) {
			((InsertProcedure) this.baseModel).getInteresteddata()[0].setName((String) receivedParameters.get(FieldNamesWeb.NAME));
		}
		if (receivedParameters.get(FieldNamesWeb.SURNAME1) != null) {
			((InsertProcedure) this.baseModel).getInteresteddata()[0].setSurname1((String) receivedParameters.get(FieldNamesWeb.SURNAME1));
		}
		if (receivedParameters.get(FieldNamesWeb.SURNAME2) != null) {
			((InsertProcedure) this.baseModel).getInteresteddata()[0].setSurname2((String) receivedParameters.get(FieldNamesWeb.SURNAME2));
		}
		if (receivedParameters.get(FieldNamesWeb.EMAIL) != null) {
			((InsertProcedure) this.baseModel).getInteresteddata()[0].setEmail((String) receivedParameters.get(FieldNamesWeb.EMAIL));
		}
	}
	
	private void setUserInfo() {
		if (receivedParameters.get(FieldNamesWeb.IDREPRESENTED) != null) {
			Number idIndividualsRepresented=null;
			if(receivedParameters.get(FieldNamesWeb.IDREPRESENTED) instanceof String) {
				idIndividualsRepresented=Integer.valueOf((String) receivedParameters.get(FieldNamesWeb.IDREPRESENTED));
			}else {
				idIndividualsRepresented=(Number) receivedParameters.get(FieldNamesWeb.IDREPRESENTED);
			}
			((InsertProcedure) this.baseModel).getInteresteddata()[0].setIdindividualsmaster(idIndividualsRepresented);
		}
		if (receivedParameters.get(FieldNamesWeb.REPRESENTEDIDENTIFICATIONNUMBER) != null) {
			((InsertProcedure) this.baseModel).getInteresteddata()[0].setIdentificationnumber((String) receivedParameters.get(FieldNamesWeb.REPRESENTEDIDENTIFICATIONNUMBER));
		}
		if (receivedParameters.get(FieldNamesWeb.REPRESENTEDNAME) != null) {
			((InsertProcedure) this.baseModel).getInteresteddata()[0].setName((String) receivedParameters.get(FieldNamesWeb.REPRESENTEDNAME));
		}
		if (receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER) != null) {
			Number idIndividualsMaster=null;
			if(receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER) instanceof String) {
				idIndividualsMaster=Integer.valueOf((String) receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER));
			}else {
				idIndividualsMaster=(Number) receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER);
			}
			((InsertProcedure) this.baseModel).getInteresteddata()[0].setIdregrepresentedby(idIndividualsMaster);
			((InsertProcedure) this.baseModel).getInteresteddata()[1].setIdindividualsmaster(idIndividualsMaster);
		}
		if (receivedParameters.get(FieldNamesWeb.IDENTIFICATIONNUMBER) != null) {
			((InsertProcedure) this.baseModel).getInteresteddata()[1].setIdentificationnumber((String) receivedParameters.get(FieldNamesWeb.IDENTIFICATIONNUMBER));
		}
		if (receivedParameters.get(FieldNamesWeb.NAME) != null) {
			((InsertProcedure) this.baseModel).getInteresteddata()[1].setName((String) receivedParameters.get(FieldNamesWeb.NAME));
		}
		if (receivedParameters.get(FieldNamesWeb.SURNAME1) != null) {
			((InsertProcedure) this.baseModel).getInteresteddata()[1].setSurname1((String) receivedParameters.get(FieldNamesWeb.SURNAME1));
		}
		if (receivedParameters.get(FieldNamesWeb.SURNAME2) != null) {
			((InsertProcedure) this.baseModel).getInteresteddata()[1].setSurname2((String) receivedParameters.get(FieldNamesWeb.SURNAME2));
		}
	}
	
	protected void buildQueryParameters(Map<String, Object> params){
		queryParameters = new HashMap<String, String>();
		queryParameters.put(FieldNamesWeb.SESSIONID, params.get(FieldNamesWeb.SESSIONID) != null && !params.equals("")? String.valueOf(params.get(FieldNamesWeb.SESSIONID)) : "-1");
		queryParameters.put("user", (String)(params.get(FieldNamesWeb.USERNAME)));
	}
	

	protected void loadInterestedData (){
		// Carga de datos no recibidos del interesado
		try{
			
			if(receivedParameters.get(FieldNamesWeb.IS_REPRESENTATION)==null || !(Boolean)(receivedParameters.get(FieldNamesWeb.IS_REPRESENTATION))){
				Map<String, Object> individualQueryResult = queryCividasMaster(FieldNamesWeb.EINDIVIDUALSMASTER, CollectionsUtils.buildMap(FieldNamesWeb.IDINDIVIDUALSMASTER, receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER)), new ArrayList<Object>());
				if(individualQueryResult.get(FieldNamesWeb.TELEPHONE)!=null && individualQueryResult.get(FieldNamesWeb.TELEPHONE) instanceof List 
						&& ((List)individualQueryResult.get(FieldNamesWeb.TELEPHONE)).size()==1 && ((List)individualQueryResult.get(FieldNamesWeb.TELEPHONE)).get(0)!=null){
					((InsertProcedure) this.baseModel).getInteresteddata()[0].setTelephone(((List)individualQueryResult.get(FieldNamesWeb.TELEPHONE)).get(0).toString());
				}

//				if (receivedParameters.get(FieldNamesWeb.EMAIL) == null || receivedParameters.get(FieldNamesWeb.EMAIL).toString().equals("")) {
				if(individualQueryResult.get(FieldNamesWeb.EMAIL)!=null && individualQueryResult.get(FieldNamesWeb.EMAIL) instanceof List 
						&& ((List)individualQueryResult.get(FieldNamesWeb.EMAIL)).size()==1 && ((List)individualQueryResult.get(FieldNamesWeb.EMAIL)).get(0)!=null){
					((InsertProcedure) this.baseModel).getInteresteddata()[0].setEmail(((List)individualQueryResult.get(FieldNamesWeb.EMAIL)).get(0).toString());
				}
//				}
			}else{
				// Hay representante
				// interesado principal
				Map<String, Object> individualQueryResult = queryCividasMaster(FieldNamesWeb.EINDIVIDUALSMASTER, CollectionsUtils.buildMap(FieldNamesWeb.IDINDIVIDUALSMASTER, receivedParameters.get(FieldNamesWeb.IDREPRESENTED)), new ArrayList<Object>());
				if(individualQueryResult.get(FieldNamesWeb.TELEPHONE)!=null && individualQueryResult.get(FieldNamesWeb.TELEPHONE) instanceof List 
						&& ((List)individualQueryResult.get(FieldNamesWeb.TELEPHONE)).size()==1 && ((List)individualQueryResult.get(FieldNamesWeb.TELEPHONE)).get(0)!=null){
					((InsertProcedure) this.baseModel).getInteresteddata()[0].setTelephone(((List)individualQueryResult.get(FieldNamesWeb.TELEPHONE)).get(0).toString());
				}
				if(individualQueryResult.get(FieldNamesWeb.EMAIL)!=null && individualQueryResult.get(FieldNamesWeb.EMAIL) instanceof List 
						&& ((List)individualQueryResult.get(FieldNamesWeb.EMAIL)).size()==1 && ((List)individualQueryResult.get(FieldNamesWeb.EMAIL)).get(0)!=null){
					((InsertProcedure) this.baseModel).getInteresteddata()[0].setEmail(((List)individualQueryResult.get(FieldNamesWeb.EMAIL)).get(0).toString());
				}
				// representante
				Map<String, Object> individualrepQueryResult = queryCividasMaster(FieldNamesWeb.EINDIVIDUALSMASTER, CollectionsUtils.buildMap(FieldNamesWeb.IDINDIVIDUALSMASTER, receivedParameters.get(FieldNamesWeb.IDINDIVIDUALSMASTER)), new ArrayList<Object>());
				if(individualrepQueryResult.get(FieldNamesWeb.TELEPHONE)!=null && individualrepQueryResult.get(FieldNamesWeb.TELEPHONE) instanceof List 
						&& ((List)individualrepQueryResult.get(FieldNamesWeb.TELEPHONE)).size()==1 && ((List)individualrepQueryResult.get(FieldNamesWeb.TELEPHONE)).get(0)!=null){
					((InsertProcedure) this.baseModel).getInteresteddata()[1].setTelephone(((List)individualrepQueryResult.get(FieldNamesWeb.TELEPHONE)).get(0).toString());
				}
				if(individualrepQueryResult.get(FieldNamesWeb.EMAIL)!=null && individualrepQueryResult.get(FieldNamesWeb.EMAIL) instanceof List 
						&& ((List)individualrepQueryResult.get(FieldNamesWeb.EMAIL)).size()==1 && ((List)individualrepQueryResult.get(FieldNamesWeb.EMAIL)).get(0)!=null){
					((InsertProcedure) this.baseModel).getInteresteddata()[1].setEmail(((List)individualrepQueryResult.get(FieldNamesWeb.EMAIL)).get(0).toString());
				}
			}
		}catch (CividasQueryDataException cqe){
			log.error("Se ha producido un error al cargar datos del interesado. A continuación se mostrará la traza del error", cqe);
		}
		
	}
	
	
	protected void loadBduacData (){
		// Carga de datos no recibidos del PCD
		try{
			// Seteo del valor del campo subject con el título del procedimiento de la BDUAC
			if(receivedParameters.get(FieldNamesWeb.IDBDUACPROCEDURETYPE)!=null){
				Map<String, Object> bduacQueryResult = queryCividasMaster(FieldNamesWeb.EBDUACPROCEDURETYPES,
						CollectionsUtils.buildMap(FieldNamesWeb.IDBDUACPROCEDURETYPE, receivedParameters.get(FieldNamesWeb.IDBDUACPROCEDURETYPE)), new ArrayList<Object>());
				
				// Castellano
				if(receivedParameters.get(FieldNamesWeb.LANG)!=null && receivedParameters.get(FieldNamesWeb.LANG).toString().equalsIgnoreCase("ES")) {
					if(bduacQueryResult.get(FieldNamesWeb.TITLE_LG1)!=null && bduacQueryResult.get(FieldNamesWeb.TITLE_LG1) instanceof List 
							&& ((List)bduacQueryResult.get(FieldNamesWeb.TITLE_LG1)).size()==1 && ((List)bduacQueryResult.get(FieldNamesWeb.TITLE_LG1)).get(0)!=null){
						((InsertProcedure) this.baseModel).setApplicantsubject(((List)bduacQueryResult.get(FieldNamesWeb.TITLE_LG1)).get(0).toString());
					}
				}else 
					// Galego
					if(receivedParameters.get(FieldNamesWeb.LANG)!=null && receivedParameters.get(FieldNamesWeb.LANG).toString().equalsIgnoreCase("GL")) {
						if(bduacQueryResult.get(FieldNamesWeb.TITLE_LG2)!=null && bduacQueryResult.get(FieldNamesWeb.TITLE_LG2) instanceof List 
								&& ((List)bduacQueryResult.get(FieldNamesWeb.TITLE_LG2)).size()==1 && ((List)bduacQueryResult.get(FieldNamesWeb.TITLE_LG2)).get(0)!=null){
							((InsertProcedure) this.baseModel).setApplicantsubject(((List)bduacQueryResult.get(FieldNamesWeb.TITLE_LG2)).get(0).toString());
						}
					}else 
						// Inglés
						if(receivedParameters.get(FieldNamesWeb.LANG)!=null && receivedParameters.get(FieldNamesWeb.LANG).toString().equalsIgnoreCase("EN")) {
							if(bduacQueryResult.get(FieldNamesWeb.TITLE_LG3)!=null && bduacQueryResult.get(FieldNamesWeb.TITLE_LG3) instanceof List 
									&& ((List)bduacQueryResult.get(FieldNamesWeb.TITLE_LG3)).size()==1 && ((List)bduacQueryResult.get(FieldNamesWeb.TITLE_LG3)).get(0)!=null){
								((InsertProcedure) this.baseModel).setApplicantsubject(((List)bduacQueryResult.get(FieldNamesWeb.TITLE_LG3)).get(0).toString());
							}
						}
			}
		}catch (CividasQueryDataException cqe){
			log.error("Se ha producido un error al cargar datos del interesado. A continuación se mostrará la traza del error", cqe);
		}
		
	}
	
	protected void loadAttachments(){
		try {
			Map<String, Object> attachmentQueryResult = queryCividasMaster(FieldNamesWeb.EATTACHMENTDATAWEB,CollectionsUtils.buildMap(FieldNamesWeb.PROCEDURECODE, receivedParameters.get(FieldNamesWeb.PROCEDURECODE)), new ArrayList<Object>());
			Integer size =0;
			ArrayList allFileNames = (ArrayList)attachmentQueryResult.get(FieldNamesWeb.ATTACHMENTFILENAME);
			if(allFileNames!=null) {
				size=allFileNames.size();
			}
			Vector<RegistryAttachment> attList = new Vector<RegistryAttachment>();
			for (int i=0;i<size;i++) {
				RegistryAttachment attach = new RegistryAttachment();
				Number webPubishable = (Number)((ArrayList)attachmentQueryResult.get(FieldNamesWeb.WEB_PUBLISHABLE)).get(i);
				Number idindividualsmaster = (Number)((ArrayList)attachmentQueryResult.get(FieldNamesWeb.IDINDIVIDUALSMASTER)).get(i);
				Number userIndividualsmaster = ((InsertProcedure) this.baseModel).getInteresteddata()[0].getIdindividualsmaster();
				if(webPubishable==(Number)1) {
					attach.setAttachmentfilename((String)((ArrayList)attachmentQueryResult.get(FieldNamesWeb.ATTACHMENTFILENAME)).get(i));
					attach.setAttachmentdatadesc((String)((ArrayList)attachmentQueryResult.get(FieldNamesWeb.ATTACH_DATA_DESC)).get(i));
					attach.setAttachmentfilesize((Number)((ArrayList)attachmentQueryResult.get(FieldNamesWeb.ATTACHMENTFILESIZE)).get(i));
					attach.setCsv((String)((ArrayList)attachmentQueryResult.get(FieldNamesWeb.CSV)).get(i));
					attList.add(attach);
				}else if(webPubishable==(Number)2&&idindividualsmaster!=null&&userIndividualsmaster==idindividualsmaster) {
					attach.setAttachmentfilename((String)((ArrayList)attachmentQueryResult.get(FieldNamesWeb.ATTACHMENTFILENAME)).get(i));
					attach.setAttachmentdatadesc((String)((ArrayList)attachmentQueryResult.get(FieldNamesWeb.ATTACH_DATA_DESC)).get(i));
					attach.setAttachmentfilesize((Number)((ArrayList)attachmentQueryResult.get(FieldNamesWeb.ATTACHMENTFILESIZE)).get(i));
					attach.setCsv((String)((ArrayList)attachmentQueryResult.get(FieldNamesWeb.CSV)).get(i));
					attList.add(attach);
				}
			}
			((InsertProcedure)baseModel).setAttachments(attList);
		} catch (CividasQueryDataException e) {
			e.printStackTrace();
		}
	}
	
	protected void loadTasks(){
		try {
			Map<String, Object> taskQueryResult = queryCividasMaster(FieldNamesWeb.ETASKSWEB,CollectionsUtils.buildMap(FieldNamesWeb.ID_PROCEDURE, receivedParameters.get(FieldNamesWeb.ID_PROCEDURE)), new ArrayList<Object>());
			ArrayList allIdTask = (ArrayList)taskQueryResult.get(FieldNamesWeb.IDTASK);
			Integer size = 0;
			if(allIdTask!=null) {
				size = allIdTask.size();
			}
			Vector<RegistryTask> taskList = new Vector<RegistryTask>();
			for (int i=0;i<size;i++) {
				RegistryTask task = new RegistryTask();
				task.setIdtask((Number)((ArrayList)taskQueryResult.get(FieldNamesWeb.IDTASK)).get(i));
				task.setTasktypedescweb_lg1((String)((ArrayList)taskQueryResult.get(FieldNamesWeb.TASKTYPEDESCWEB_LG1)).get(i));
				task.setTasktypedescweb_lg2((String)((ArrayList)taskQueryResult.get(FieldNamesWeb.TASKTYPEDESCWEB_LG2)).get(i));
				task.setTaskdescription((String)((ArrayList)taskQueryResult.get(FieldNamesWeb.TASKDESCRIPTION)).get(i));
				task.setTaskdate(new SimpleDateFormat("dd/MM/yyyy (HH:mm)").format(new Date((Long)((ArrayList)taskQueryResult.get(FieldNamesWeb.TASKDATE)).get(i))));
				taskList.add(task);
			}
			((InsertProcedure)baseModel).setTasks(taskList);
		} catch (CividasQueryDataException e) {
			e.printStackTrace();
		}
	}

	public abstract String sendRequest();
	
	
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
			throw new CividasQueryDataException("No se ha podido realizar la consulta sobre la entidad: " + entity);
		}
		return response;
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

	
	public boolean isCif(){
		if(((InsertProcedure) this.baseModel).getInteresteddata()[0].getIdentificationnumber()!=null){
			return CIFUtils.isCIFWellFormed(((InsertProcedure) this.baseModel).getInteresteddata()[0].getIdentificationnumber());
		}
		return false;
	}
	
	public static String getFileNameExtension(String fileName){
		
		if (fileName == null){
			return "";
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		Application app = context.getApplication();
		ArrayList<String> iconFileNames = (ArrayList<String>)app.evaluateExpressionGet(context, "#{catalogBean.iconFileNames}", Object.class);
		String[] parts = fileName.split("\\.");
		String extensionPng = parts[parts.length-1]+".png";
		
		return iconFileNames.contains(extensionPng)?extensionPng:"file.png";
		
	}
}

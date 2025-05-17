package com.cividas.customforms.webapp.controller.solconvxenexen;

import com.cividas.customforms.webapp.common.exceptions.CividasQueryDataException;
import com.cividas.customforms.webapp.common.model.base.EntityData;
import com.cividas.customforms.webapp.common.model.base.JsonMultipleData;
import com.cividas.customforms.webapp.common.model.solconvxenexen.SolConvXenExenDynamicDataModel;
import com.cividas.customforms.webapp.common.model.solconvxenexen.SolConvXenExenModel;
import com.cividas.customforms.webapp.controller.base.BaseControllerUvigo;
import com.cividas.customforms.webapp.controller.solconvxen.DeclaracionResponsable;
import com.cividas.customforms.webapp.utils.MastersUtils;
import com.cividas.web.common.FieldNamesWeb;
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

@ManagedBean(name = "solConvXenExenController")
@ViewScoped
public class SolConvXenExenController extends BaseControllerUvigo {

	private static final long serialVersionUID = 2148438200796166524L;

	@ManagedProperty("#{solConvXenExenModel}")
	SolConvXenExenModel model;
	

	private boolean consenttribute;

	private boolean discapacidade;
	private boolean familianumerosa;
	private boolean persoaluvigo;
	private boolean vitimaterrorxenero;
	
	protected List<DeclaracionResponsable>	declaracionesResponsablesList= new ArrayList<DeclaracionResponsable>();
	
	
	// configuración convocatoria
	private boolean showconsenttribute=false;
	private boolean showdataqueryparagraph=false;
	private String showdataqueryparagraphtxt;
	private String lopdrightsurl;
	
	private Number idRegistry;
	private ResourceBundle i18n;

	@Override
	public void init() {
		log.info("Inicializando el controlador: SolConvXenExenController ...");

		// Cargamos este modelo como el modelo base (esto es importante ya que el
		// BaseController debe saber
		// cual es el modelo con el que trabajará para invocar la llamada al servidor)
		this.baseModel = model;

		super.init();

		// Inicializando el modelo particular de datos
		model.setRegistrytypedata(new SolConvXenExenDynamicDataModel());
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

		// Consultamos la lista de declaraciones responsables para esa convocatoria iddeclararesponsable;declararesponsablecode;declararesponsablees;declararesponsablegl;idprocedure;selected
		try {
			HashMap hkeys=new HashMap<Object, Object>();
			if(idprocedureparent!=null) {
				hkeys.put("idprocedure", idprocedureparent);
				hkeys.put("selected", 1);
			}
			Map<String, Object> declaracionesResponsables = queryCividasMaster("uvigo.decresptask.Easigdeclararesponsables",hkeys, new ArrayList<Object>());
			Map<String, String> declaracionesResponsablesMap= new HashMap<String, String>();
			if (localeLanguage.equals(FieldNamesWeb.LOCALE_LANGUAGE_LG2)) {
				declaracionesResponsablesMap= MastersUtils.parseMasterMap(declaracionesResponsables, "declararesponsablecode","declararesponsablegl");
			} else {
				declaracionesResponsablesMap = MastersUtils.parseMasterMap(declaracionesResponsables, "declararesponsablecode","declararesponsablees");
			}
			
			if(!declaracionesResponsablesMap.isEmpty()) {				
				Set<String> decrespkeys = declaracionesResponsablesMap.keySet();
				Iterator<String> iteratorkeys=decrespkeys.iterator();
				while(iteratorkeys.hasNext()) {
					DeclaracionResponsable item=new DeclaracionResponsable();
					item.setDeclararesponsablecode(iteratorkeys.next());
					item.setDeclararesponsable(declaracionesResponsablesMap.get(item.getDeclararesponsablecode()));
					this.declaracionesResponsablesList.add(item);					
				}

			}
			
			HashMap hkeys2=new HashMap<Object, Object>();
			if(idprocedureparent!=null) {
				hkeys2.put("idprocedure", idprocedureparent);
			}
			Map<String, Object> convConfig = queryCividasMaster("efbasesconvxen",hkeys2, new ArrayList<Object>());
			// Extraemos los datos de la respuesta del query sobre bases convocatoria
			List colshowconsenttribute = (List)convConfig.get("showconsenttribute");
			if (colshowconsenttribute !=null && colshowconsenttribute.size()>0 && colshowconsenttribute.get(0) instanceof Number){
				if(((Number)colshowconsenttribute.get(0)).intValue()==1) {
					showconsenttribute=true;	
				}
			}

			List colshowdataqueryparagraph = (List)convConfig.get("showdataqueryparagraph");
			if (colshowdataqueryparagraph !=null && colshowdataqueryparagraph.size()>0 && colshowdataqueryparagraph.get(0) instanceof Number){
				if(((Number)colshowdataqueryparagraph.get(0)).intValue()==1) {
					showdataqueryparagraph=true;	
				}
			}

			if (localeLanguage.equals(FieldNamesWeb.LOCALE_LANGUAGE_LG2)) {
				List colshowdataqueryparagraphtxtgl = (List)convConfig.get("showdataqueryparagraphtxtgl");
				if (colshowdataqueryparagraphtxtgl !=null && colshowdataqueryparagraphtxtgl.size()>0 && colshowdataqueryparagraphtxtgl.get(0) instanceof String){
					showdataqueryparagraphtxt=colshowdataqueryparagraphtxtgl.get(0).toString();	
				}
			}else {
				List colshowdataqueryparagraphtxtes = (List)convConfig.get("showdataqueryparagraphtxtes");
				if (colshowdataqueryparagraphtxtes !=null && colshowdataqueryparagraphtxtes.size()>0 && colshowdataqueryparagraphtxtes.get(0) instanceof String){
					showdataqueryparagraphtxt=colshowdataqueryparagraphtxtes.get(0).toString();	
				}
			}
			
			List collopdrightsurl = (List)convConfig.get("lopdrightsurl");
			if (collopdrightsurl !=null  && collopdrightsurl.size()>0 && collopdrightsurl.get(0) instanceof String){
				lopdrightsurl=collopdrightsurl.get(0).toString();	
			}			
			
		} catch (CividasQueryDataException cqe) {
			log.error("Se ha producido un error al cargar el maestro de los tipos de solicitudes. A continuación se mostrará la traza del error",cqe);
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

			if (isShowconsenttribute()) {
				model.getRegistrytypedata().setConsenttribute(isConsenttribute() ? 1 : 0);
			}

			model.getRegistrytypedata().setDeclararesponsablesok(isDeclararesponsablesok() ? 1 : 0);

			model.getRegistrytypedata().setDiscapacidade(getDiscapacidade() ? 1 : 0);
			model.getRegistrytypedata().setFamilianumerosa(getFamilianumerosa() ? 1 : 0);
			model.getRegistrytypedata().setVitimaterrorxenero(getVitimaterrorxenero() ? 1 : 0);
			model.getRegistrytypedata().setPersoaluvigo(getPersoaluvigo() ? 1 : 0);

			//construyo el modelo para la insercion multiple de las listas
			List<EntityData> entityDataList = new ArrayList<EntityData>();
			entityDataList.add(new EntityData("efdindeclaracionesresponsables", this.getDeclaracionesResponsablesList()));

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


			// Almacenamos la respuesta en el contexto
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters", queryParameters);

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

	
	
	
	
	
	
	public SolConvXenExenModel getModel() {
		return model;
	}

	public void setModel(SolConvXenExenModel model) {
		this.model = model;
	}
	

	
	
	public boolean isUserLogged() {

		return (queryParameters != null && queryParameters.get("user") != null) ? true : false;
	}

	public void showDlg(String dlgName) {
		PrimeFaces.current().executeScript(dlgName + ".show()");
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

		public boolean isDeclararesponsablesok() {
			boolean declararesponsablesok=false;			
			if(declaracionesResponsablesList.size()>0) {
				declararesponsablesok=true;
				for (DeclaracionResponsable declaracionResponsable : declaracionesResponsablesList) {
					if(!declaracionResponsable.isChecked()) {
						declararesponsablesok=false;
						break;
					}
				}
			}
			return declararesponsablesok;
		}

		
		public boolean isConsenttribute() {
			return consenttribute;
		}

		public void setConsenttribute(boolean consenttribute) {
			this.consenttribute = consenttribute;
		}



		public boolean isShowconsenttribute() {
			return showconsenttribute;
		}

		public void setShowconsenttribute(boolean showconsenttribute) {
			this.showconsenttribute = showconsenttribute;
		}

		public boolean isShowdataqueryparagraph() {
			return showdataqueryparagraph;
		}

		public void setShowdataqueryparagraph(boolean showdataqueryparagraph) {
			this.showdataqueryparagraph = showdataqueryparagraph;
		}


		public String getLopdrightsurl() {
			return lopdrightsurl;
		}

		public void setLopdrightsurl(String lopdrightsurl) {
			this.lopdrightsurl = lopdrightsurl;
		}

		public List<DeclaracionResponsable> getDeclaracionesResponsablesList() {
			return declaracionesResponsablesList;
		}

		public void setDeclaracionesResponsablesList(List<DeclaracionResponsable> declaracionesResponsablesList) {
			this.declaracionesResponsablesList = declaracionesResponsablesList;
		}

		public String getShowdataqueryparagraphtxt() {
			return showdataqueryparagraphtxt;
		}

		public void setShowdataqueryparagraphtxt(String showdataqueryparagraphtxt) {
			this.showdataqueryparagraphtxt = showdataqueryparagraphtxt;
		}

	public boolean getDiscapacidade() {
		return discapacidade;
	}

	public void setDiscapacidade(boolean discapacidade) {
		this.discapacidade = discapacidade;
	}

	public boolean getFamilianumerosa() {
		return familianumerosa;
	}

	public void setFamilianumerosa(boolean familianumerosa) {
		this.familianumerosa = familianumerosa;
	}

	public boolean getPersoaluvigo() {
		return persoaluvigo;
	}

	public void setPersoaluvigo(boolean persoaluvigo) {
		this.persoaluvigo = persoaluvigo;
	}

	public boolean getVitimaterrorxenero() {
		return vitimaterrorxenero;
	}

	public void setVitimaterrorxenero(boolean vitimaterrorxenero) {
		this.vitimaterrorxenero = vitimaterrorxenero;
	}
}

package com.cividas.customforms.webapp.controller.solconvxenpersonal;

import com.cividas.customforms.webapp.common.exceptions.CividasQueryDataException;
import com.cividas.customforms.webapp.common.model.base.EntityData;
import com.cividas.customforms.webapp.common.model.base.JsonMultipleData;
import com.cividas.customforms.webapp.common.model.solconvxenpersonal.DatosOposicionSelected;
import com.cividas.customforms.webapp.common.model.solconvxenpersonal.SolConvXenPersonalDynamicDataModel;
import com.cividas.customforms.webapp.common.model.solconvxenpersonal.SolConvXenPersonalModel;
import com.cividas.customforms.webapp.controller.base.BaseController;
import com.cividas.customforms.webapp.controller.base.BaseControllerUvigo;
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

@ManagedBean(name = "solConvXenPersonalController")
@ViewScoped
public class SolConvXenPersonalController extends BaseControllerUvigo {
    private static final long serialVersionUID = -1907325954784860459L;

    @ManagedProperty("#{solConvXenPersonalModel}")
    SolConvXenPersonalModel model;

    private boolean consenttribute;

    protected List<DeclaracionResponsable> declaracionesResponsablesList = new ArrayList<DeclaracionResponsable>();
    protected List<DatosOposicion> datosOposicionList = new ArrayList<>();

    public List<DatosOposicionSelected> datosOposicionSelectedList = new ArrayList<>();

    // configuración convocatoria
    private boolean showconsenttribute = false;
    private boolean showdataqueryparagraph = false;
    private String showdataqueryparagraphtxt;
    private String lopdrightsurl;
    private String tituloconv = null;
    private String conv = null;
    private String anhoconv = null;
    private String escala = null;
    private String subescala = null;
    private String tipoconv = null;
    private String plaza = null;
    private Boolean onlyoneapplication = false;

    private Number idRegistry;
    private ResourceBundle i18n;

    @Override
    public void init() {
        log.info("Inicializando el controlador: SolConvXenPersonalController ...");
        this.baseModel = model;
        super.init();
        model.setRegistrytypedata(new SolConvXenPersonalDynamicDataModel());
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
            HashMap<Object, Object> hkeys = new HashMap<Object, Object>();
            if (idprocedureparent != null) {
                hkeys.put("idprocedure", idprocedureparent);
                hkeys.put("selected", 1);
            }
            Map<String, Object> declaracionesResponsables = queryCividasMaster("uvigo.decresptask.Easigdeclararesponsables", hkeys, new ArrayList<Object>());
            Map<String, String> declaracionesResponsablesMap = new HashMap<String, String>();
            if (localeLanguage.equals(FieldNamesWeb.LOCALE_LANGUAGE_LG2)) {
                declaracionesResponsablesMap = MastersUtils.parseMasterMap(declaracionesResponsables, "declararesponsablecode", "declararesponsablegl");
            } else {
                declaracionesResponsablesMap = MastersUtils.parseMasterMap(declaracionesResponsables, "declararesponsablecode", "declararesponsablees");
            }

            if (!declaracionesResponsablesMap.isEmpty()) {
                Set<String> decrespkeys = declaracionesResponsablesMap.keySet();
                for (String decrespkey : decrespkeys) {
                    DeclaracionResponsable item = new DeclaracionResponsable();
                    item.setDeclararesponsablecode(decrespkey);
                    item.setDeclararesponsable(declaracionesResponsablesMap.get(item.getDeclararesponsablecode()));
                    this.declaracionesResponsablesList.add(item);
                }
            }

            Map<String, Object> datosOposicion = queryCividasMaster("uvigo.decresptask.Easigndatosoposicion", hkeys, new ArrayList<Object>());
            Map<String, String> datosOposicionMap = new HashMap<String, String>();
            if (localeLanguage.equals(FieldNamesWeb.LOCALE_LANGUAGE_LG2)) {
                datosOposicionMap = MastersUtils.parseMasterMap(datosOposicion, "datosoposicioncode", "datosoposiciongl");
            } else {
                datosOposicionMap = MastersUtils.parseMasterMap(datosOposicion, "datosoposicioncode", "datosoposicionees");
            }

            if (!datosOposicionMap.isEmpty()) {
                Set<String> decrespkeysOp = datosOposicionMap.keySet();
                for (String s : decrespkeysOp) {
                    DatosOposicion itemOp = new DatosOposicion();
                    itemOp.setDatosoposicioncode(s);
                    itemOp.setDatosoposicion(datosOposicionMap.get(itemOp.getDatosoposicioncode()));
                    this.datosOposicionList.add(itemOp);
                }
            }

            HashMap<Object, Object> hkeys2 = new HashMap<Object, Object>();
            if (idprocedureparent != null) {
                hkeys2.put("idprocedure", idprocedureparent);
            }
            Map<String, Object> convConfig = queryCividasMaster("efbasesconvxen", hkeys2, new ArrayList<Object>());
            List colshowconsenttribute = (List) convConfig.get("showconsenttribute");
            if (colshowconsenttribute != null && !colshowconsenttribute.isEmpty() && colshowconsenttribute.get(0) instanceof Number) {
                if (((Number) colshowconsenttribute.get(0)).intValue() == 1) {
                    showconsenttribute = true;
                }
            }

            List colshowdataqueryparagraph = (List) convConfig.get("showdataqueryparagraph");
            if (colshowdataqueryparagraph != null && !colshowdataqueryparagraph.isEmpty() && colshowdataqueryparagraph.get(0) instanceof Number) {
                if (((Number) colshowdataqueryparagraph.get(0)).intValue() == 1) {
                    showdataqueryparagraph = true;
                }
            }

            List onlyoneapplicationparagraph = (List) convConfig.get("onlyoneapplication");
            if (onlyoneapplicationparagraph != null && !onlyoneapplicationparagraph.isEmpty() && onlyoneapplicationparagraph.get(0) instanceof Number) {
                if (((Number) onlyoneapplicationparagraph.get(0)).intValue() == 1) {
                    onlyoneapplication = true;
                }
            }

            List colshowdataqueryparagraphtxtgl = (List) convConfig.get("showdataqueryparagraphtxtgl");
            if (colshowdataqueryparagraphtxtgl != null && !colshowdataqueryparagraphtxtgl.isEmpty() && colshowdataqueryparagraphtxtgl.get(0) instanceof String) {
                showdataqueryparagraphtxt = colshowdataqueryparagraphtxtgl.get(0).toString();
            }

            List tituloconvshow = (List) convConfig.get("tituloconfigwebgal");
            if (tituloconvshow != null && !tituloconvshow.isEmpty() && tituloconvshow.get(0) instanceof String) {
                tituloconv = tituloconvshow.get(0).toString();
            }

            List convocatoriaconfigweb = (List) convConfig.get("convocatoriaconfigwebgal");
            if (convocatoriaconfigweb != null && !convocatoriaconfigweb.isEmpty() && convocatoriaconfigweb.get(0) instanceof String) {
                conv = convocatoriaconfigweb.get(0).toString();
            }
            model.getRegistrytypedata().setTituloconfigwebgal(tituloconv);
            model.getRegistrytypedata().setConvocatoriaconfigwebgal(conv);

            if (localeLanguage.equals(FieldNamesWeb.LOCALE_LANGUAGE_LG1)) {
                List colshowdataqueryparagraphtxtes = (List) convConfig.get("showdataqueryparagraphtxtes");
                if (colshowdataqueryparagraphtxtes != null && !colshowdataqueryparagraphtxtes.isEmpty() && colshowdataqueryparagraphtxtes.get(0) instanceof String) {
                    showdataqueryparagraphtxt = colshowdataqueryparagraphtxtes.get(0).toString();
                }
                tituloconvshow = (List) convConfig.get("tituloconfigweb");
                if (tituloconvshow != null && !tituloconvshow.isEmpty() && tituloconvshow.get(0) instanceof String) {
                    tituloconv = tituloconvshow.get(0).toString();
                }
                convocatoriaconfigweb = (List) convConfig.get("convocatoriaconfigweb");
                if (convocatoriaconfigweb != null && !convocatoriaconfigweb.isEmpty() && convocatoriaconfigweb.get(0) instanceof String) {
                    conv = convocatoriaconfigweb.get(0).toString();
                }
            }

            List anhoconfigweb = (List) convConfig.get("anhoconfigweb");
            if (anhoconfigweb != null && !anhoconfigweb.isEmpty() && anhoconfigweb.get(0) instanceof String) {
                anhoconv = anhoconfigweb.get(0).toString();
            }
            model.getRegistrytypedata().setAnhoconfigweb(anhoconv);

            List escalaweb = (List) convConfig.get("escala");
            if (escalaweb != null && !escalaweb.isEmpty() && escalaweb.get(0) instanceof String) {
                escala = escalaweb.get(0).toString();
            }
            model.getRegistrytypedata().setEscala(escala);

            List subescalaweb = (List) convConfig.get("subescala");
            if (subescalaweb != null && !subescalaweb.isEmpty() && subescalaweb.get(0) instanceof String) {
                subescala = subescalaweb.get(0).toString();
            }
            model.getRegistrytypedata().setSubescala(subescala);

            List tipoconvweb = (List) convConfig.get("tipoconv");
            if (tipoconvweb != null && !tipoconvweb.isEmpty() && tipoconvweb.get(0) instanceof String) {
                tipoconv = tipoconvweb.get(0).toString();
            }
            model.getRegistrytypedata().setTipoconv(tipoconv);

            List plazaweb = (List) convConfig.get("plaza");
            if (plazaweb != null && !plazaweb.isEmpty() && plazaweb.get(0) instanceof String) {
                plaza = plazaweb.get(0).toString();
            }
            model.getRegistrytypedata().setPlaza(plaza);

            List collopdrightsurl = (List) convConfig.get("lopdrightsurl");
            if (collopdrightsurl != null && !collopdrightsurl.isEmpty() && collopdrightsurl.get(0) instanceof String) {
                lopdrightsurl = collopdrightsurl.get(0).toString();
            }

        } catch (CividasQueryDataException cqe) {
            log.error("Se ha producido un error al cargar el maestro de los tipos de solicitudes. A continuación se mostrará la traza del error", cqe);
        }
    }


    @Override
    public String sendRequest() {
        String response = "success";

        boolean firstregistry = true;
        if (onlyoneapplication) {
            firstregistry = checkInterestedRegistry();
        }

        try {
            if ("".equalsIgnoreCase(getMandatoryFilesUploaded())) {
                log.error("No se adjuntaron todos los documentos.");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("mandatorydocumentsmiss"), ""));
                return "error";
            }
            if (firstregistry) {
                if (isShowconsenttribute()) {
                    model.getRegistrytypedata().setConsenttribute(isConsenttribute() ? 1 : 0);
                }

                model.getRegistrytypedata().setDeclararesponsablesok(isDeclararesponsablesok() ? 1 : 0);

                List<EntityData> entityDataList = new ArrayList<EntityData>();
                entityDataList.add(new EntityData("efdindeclaracionesresponsables", this.getDeclaracionesResponsablesList()));

                datosOposicionSelectedList();
                entityDataList.add(new EntityData("eformdatosoposicionselectedpersonal", this.getDatosOposicionSelectedList()));

                if (!entityDataList.isEmpty()) {
                    model.getRegistrytypedata().setJsonMultipleData(new JsonMultipleData(entityDataList));
                }

                respuesta = createNewRegistryInput();
                FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
                FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters", queryParameters);
            } else {
                response = "onlyOneRegistryError";
                respuesta = requestRegistryTypeCodeError(this.idRegistry);
                FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
                log.info("El interesado ya tiene una solicitud para este tipo de registro");
            }

            if (respuesta.getIdRegistryReport() == null && firstregistry) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, getResourceMessage("no_report"), ""));
            }

            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters", queryParameters);
        } catch (Exception e) {
            response = "error";
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters", queryParameters);
        }
        try {
            Map<String, Object> res = updateProcedureParent();
        } catch (ParseException | CividasQueryDataException e) {
            log.error("No se pudo asocociar el registro: {} al expediente padre: {} ", respuesta.getIdRegistry(), idprocedureparent);
            throw new RuntimeException(e);
        }
        return response;
    }


    public SolConvXenPersonalModel getModel() {
        return model;
    }

    public void setModel(SolConvXenPersonalModel model) {
        this.model = model;
    }


    public boolean isUserLogged() {

        return queryParameters != null && queryParameters.get("user") != null;
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

        HashMap<Object, Object> filtro = new HashMap<Object, Object>();
        filtro.put("applicantidentification", model.getInteresteddata()[0].getIdentificationnumber());
        filtro.put("idprocedureparent", idprocedureparent);

        List<Object> listaCamposprocedures = new ArrayList<>();
        listaCamposprocedures.add("idprocedure");
        listaCamposprocedures.add("idprocedureparent");
        listaCamposprocedures.add("idregistryfirst");
        listaCamposprocedures.add("applicantidentification");

        try {
            if (model.getInteresteddata()[0].getIdentificationnumber() != null && idprocedureparent != null) {
                Map<String, Object> individualProceduresQueryResult = queryCividasMaster("procedures.EProcedures", filtro, listaCamposprocedures);

                if (!individualProceduresQueryResult.isEmpty()) {

                    firstRegistry = false;
                    Map<String, String> listProcedures = MastersUtils.parseMasterMap(individualProceduresQueryResult, "idregistryfirst", "idprocedureparent");
                    for (Entry<String, String> idsAndCodes : listProcedures.entrySet()) {
                        try {
                            idRegistry = NumberFormat.getInstance().parse(idsAndCodes.getKey());
                        } catch (ParseException e) {
                            log.error("Se ha producido un error. A continuación se mostrará la traza del error", e);
                        }
                    }
                }
            }
        } catch (CividasQueryDataException cqe) {
            log.error("Se ha producido un error al consultar las solicitudes del interesado. A continuación se mostrará la traza del error", cqe);
        }

        return firstRegistry;
    }

    public boolean isDeclararesponsablesok() {
        boolean declararesponsablesok = false;
        if (!declaracionesResponsablesList.isEmpty()) {
            declararesponsablesok = true;
            for (DeclaracionResponsable declaracionResponsable : declaracionesResponsablesList) {
                if (!declaracionResponsable.isChecked()) {
                    declararesponsablesok = false;
                    break;
                }
            }
        }
        return declararesponsablesok;
    }

    private void datosOposicionSelectedList() {
        if (!datosOposicionList.isEmpty()) {
            for (DatosOposicion datosOposicion : datosOposicionList) {
                if (datosOposicion.isChecked()) {
                    datosOposicionSelectedList.add(new DatosOposicionSelected(datosOposicion.getDatosoposicion(), 1));
                } else {
                    datosOposicionSelectedList.add(new DatosOposicionSelected(datosOposicion.getDatosoposicion(), 0));
                }
            }
        }
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

    public List<DatosOposicion> getDatosOposicionList() {
        return datosOposicionList;
    }

    public void setDatosOposicionList(List<DatosOposicion> datosOposicionList) {
        this.datosOposicionList = datosOposicionList;
    }

    public List<DatosOposicionSelected> getDatosOposicionSelectedList() {
        return datosOposicionSelectedList;
    }

    public void setDatosOposicionSelectedList(List<DatosOposicionSelected> datosOposicionSelectedList) {
        this.datosOposicionSelectedList = datosOposicionSelectedList;
    }

    public String getTituloconv() {
        return tituloconv;
    }

    public String getConv() {
        return conv;
    }

    public String getAnhoconv() {
        return anhoconv;
    }

    public void setTituloconv(String tituloconv) {
        this.tituloconv = tituloconv;
    }

    public void setConv(String conv) {
        this.conv = conv;
    }

    public void setAnhoconv(String anhoconv) {
        this.anhoconv = anhoconv;
    }

    public String getEscala() {
        return escala;
    }

    public void setEscala(String escala) {
        this.escala = escala;
    }

    public String getPlaza() {
        return plaza;
    }

    public void setPlaza(String plaza) {
        this.plaza = plaza;
    }

    public String getTipoconv() {
        return tipoconv;
    }

    public void setTipoconv(String tipoconv) {
        this.tipoconv = tipoconv;
    }

    public String getSubescala() {
        return subescala;
    }

    public void setSubescala(String subescala) {
        this.subescala = subescala;
    }
}

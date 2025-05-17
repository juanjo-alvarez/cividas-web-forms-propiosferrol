package com.cividas.customforms.webapp.controller.obmenoresferrol;

import com.cividas.customforms.webapp.common.model.base.EntityData;
import com.cividas.customforms.webapp.common.model.base.JsonMultipleData;
import com.cividas.customforms.webapp.common.model.obmenonesferrol.ObrasDetalles;
import com.cividas.customforms.webapp.common.model.obmenonesferrol.ObrasMenoresFerrolDynamicDataModel;
import com.cividas.customforms.webapp.common.model.obmenonesferrol.ObrasMenoresFerrolModel;
import com.cividas.customforms.webapp.controller.base.BaseControllerFerrol;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "obrasMenoresFerrolContoller")
@ViewScoped
public class ObrasMenoresControllerFerrol extends BaseControllerFerrol {
    private static final long serialVersionUID = 7650027418297999353L;

    public List<ObrasDetalles> obrasDetallesList;
    private ObrasDetalles obra;
    private ObrasDetalles deleteObra;
    public boolean autorizo;

    @ManagedProperty("#{obrasMenoresFerrolModel}")
    ObrasMenoresFerrolModel model;

    @Override
    public void init() {
        log.info("Inicializando el controlador: ObrasMenoresFerrolController ...");
        this.baseModel = model;
        super.init();
        model.setRegistrytypedata(new ObrasMenoresFerrolDynamicDataModel());
        model.setSubject("Sede electronica - Solicitud obras menores");
        obrasDetallesList = new ArrayList<ObrasDetalles>();
        obra = new ObrasDetalles();
    }

    @Override
    public String receiveParentData() {
        log.info("Recibiendo los parametros de sesion de la sede ...");
        super.receiveParentData();
        return null;
    }

    @Override
    protected void loadDataMasters() {
        super.loadDataMasters();
    }

    @Override
    public String sendRequest() {
        String response = "success";
        try {
            if ("".equalsIgnoreCase(getMandatoryFilesUploaded())) {
                log.error("No se adjuntaron todos los documentos.");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("mandatorydocumentsmiss"), ""));
                return "error";
            }
            if(isRepresentadomanual()) {
                model.getRegistrytypedata().setActuaenrepresentacion(new Integer(1));
            } else {
                model.getRegistrytypedata().setActuaenrepresentacion(new Integer(0));
            }
            respuesta = createNewRegistryInput();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters", queryParameters);
        } catch (Exception e) {
            response = "error";
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters", queryParameters);
        }
        return response;
    }

    public String validateSolicitud() {
        List<EntityData> entityDataList = new ArrayList<EntityData>();
        entityDataList.add(new EntityData("eformobrasmenoresdetalles", this.getObrasDetallesList()));

        if (!this.getObrasDetallesList().isEmpty() && !entityDataList.isEmpty()) {
            model.getRegistrytypedata().setJsonMultipleData(new JsonMultipleData(entityDataList));
            if (autorizo) return this.sendRequest();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("autorizo_mandatory_option"), ""));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("tabla_mandatory_option"), ""));
        }
        return null;
    }

    public List<ObrasDetalles> getObrasDetallesList() {
        return obrasDetallesList;
    }

    public void setObrasDetallesList(List<ObrasDetalles> obrasDetallesList) {
        this.obrasDetallesList = obrasDetallesList;
    }

    public ObrasDetalles getObra() {
        return obra;
    }

    public void setObra(ObrasDetalles obra) {
        this.obra = obra;
    }

    public ObrasMenoresFerrolModel getModel() {
        return model;
    }

    public void setModel(ObrasMenoresFerrolModel model) {
        this.model = model;
    }

    public ObrasDetalles getDeleteObra() {
        return deleteObra;
    }

    public void setDeleteObra(ObrasDetalles deleteObra) {
        this.deleteObra = deleteObra;
        this.obrasDetallesList.remove(this.deleteObra);
    }

    public void saveObra() {
        PrimeFaces.current().executeScript("PF('obrasDialog').hide();sendHeight();");
    }

    public void addObra() {
        this.obra = new ObrasDetalles();
    }

    public void newObra() {
        this.obrasDetallesList.add(this.obra);
        PrimeFaces.current().executeScript("PF('obrasDialog').hide();sendHeight();");
    }

    public boolean isAutorizo() {
        return autorizo;
    }

    public void setAutorizo(boolean autorizo) {
        this.autorizo = autorizo;
    }
}
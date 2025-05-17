package com.cividas.customforms.webapp.controller.obmayoresferrol;

import com.cividas.customforms.webapp.common.model.obmayoresferrol.ObrasMayoresFerrolDynamicDataModel;
import com.cividas.customforms.webapp.common.model.obmayoresferrol.ObrasMayoresFerrolModel;
import com.cividas.customforms.webapp.controller.base.BaseControllerFerrol;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "obrasMayoresFerrolController")
@ViewScoped
public class ObrasMayoresControllerFerrol extends BaseControllerFerrol {

    private static final long serialVersionUID = -4416891013063428018L;

    @ManagedProperty("#{obrasMayoresFerrolModel}")
    ObrasMayoresFerrolModel model;

    public boolean autorizo;

    @Override
    public void init() {
        log.info("Inicializando el controlador: obrasMayoresFerrolController ...");
        this.baseModel = model;
        super.init();
        model.setRegistrytypedata(new ObrasMayoresFerrolDynamicDataModel());
        model.setSubject("Sede electronica - Solicitud de certificado de obras mayores");
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
        if (autorizo) return this.sendRequest();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("autorizo_mandatory_option"), ""));
        return null;
    }

    public ObrasMayoresFerrolModel getModel() {
        return model;
    }

    public void setModel(ObrasMayoresFerrolModel model) {
        this.model = model;
    }

    public boolean isAutorizo() {
        return autorizo;
    }

    public void setAutorizo(boolean autorizo) {
        this.autorizo = autorizo;
    }
}

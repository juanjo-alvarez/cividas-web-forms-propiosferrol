package com.cividas.customforms.webapp.controller.accidentetraficoferrol;

import com.cividas.customforms.webapp.common.model.accidentetraficoferrol.AccidenteTraficoFerrolDynamicDataModel;
import com.cividas.customforms.webapp.common.model.accidentetraficoferrol.AccidenteTraficoFerrolModel;
import com.cividas.customforms.webapp.controller.base.BaseControllerFerrol;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "accidenteTraficoFerrolController")
@ViewScoped
public class AccidenteTraficoFerrolControllerFerrol extends BaseControllerFerrol {

    private static final long serialVersionUID = -5336171888596662897L;

    private boolean existendilixencias;
    private boolean desonocedilixencias;

    @ManagedProperty("#{accidenteTraficoFerrolModel}")
    AccidenteTraficoFerrolModel model;

    @Override
    public void init() {
        log.info("Inicializando el controlador: AccidenteTraficoFerrolController ...");
        this.baseModel = model;
        super.init();
        model.setRegistrytypedata(new AccidenteTraficoFerrolDynamicDataModel());
        model.setSubject("Sede electronica - Accidente trafico");
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
        if (desonocedilixencias || existendilixencias) {
            return this.sendRequest();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("accidente_mandatory_option"), ""));
            return null;
        }
    }

    public AccidenteTraficoFerrolModel getModel() {
        return model;
    }

    public void setModel(AccidenteTraficoFerrolModel model) {
        this.model = model;
    }

    public boolean isExistendilixencias() {
        return existendilixencias;
    }

    public void setExistendilixencias(boolean existendilixencias) {
        this.existendilixencias = existendilixencias;
    }

    public boolean isDesonocedilixencias() {
        return desonocedilixencias;
    }

    public void setDesonocedilixencias(boolean desonocedilixencias) {
        this.desonocedilixencias = desonocedilixencias;
    }

}


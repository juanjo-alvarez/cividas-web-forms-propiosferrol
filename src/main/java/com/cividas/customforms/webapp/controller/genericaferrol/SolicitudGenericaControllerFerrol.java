package com.cividas.customforms.webapp.controller.genericaferrol;

import com.cividas.customforms.webapp.common.model.genericaferrol.SolGenericaFerrolDynamicDataModel;
import com.cividas.customforms.webapp.common.model.genericaferrol.SolGenericaFerrolModel;
import com.cividas.customforms.webapp.controller.base.BaseControllerFerrol;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "solGenericaFerrolController")
@ViewScoped
public class SolicitudGenericaControllerFerrol extends BaseControllerFerrol {

    private static final long serialVersionUID = 3944811193764256986L;

    @ManagedProperty("#{solGenericaFerrolModel}")
    SolGenericaFerrolModel model;

    @Override
    public void init() {
        log.info("Inicializando el controlador: SolicitudGenericaFerrolController ...");
        this.baseModel = model;
        super.init();
        model.setRegistrytypedata(new SolGenericaFerrolDynamicDataModel());
        model.setSubject("Sede electronica - Solicitud generica");
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

    public SolGenericaFerrolModel getModel() {
        return model;
    }

    public void setModel(SolGenericaFerrolModel model) {
        this.model = model;
    }

}

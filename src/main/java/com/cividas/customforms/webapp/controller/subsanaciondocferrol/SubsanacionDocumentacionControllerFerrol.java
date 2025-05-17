package com.cividas.customforms.webapp.controller.subsanaciondocferrol;

import com.cividas.customforms.webapp.common.model.subsanaciondocferrol.SubsanacionDocFerrolDynamicDataModel;
import com.cividas.customforms.webapp.common.model.subsanaciondocferrol.SubsanacionDocFerrolModel;
import com.cividas.customforms.webapp.controller.base.BaseControllerFerrol;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "subsanacionDocFerrolController")
@ViewScoped
public class SubsanacionDocumentacionControllerFerrol extends BaseControllerFerrol {

    private static final long serialVersionUID = -1103235358851853479L;

    @ManagedProperty("#{subsanacionDocFerrolModel}")
    SubsanacionDocFerrolModel model;

    @Override
    public void init() {
        log.info("Inicializando el controlador: SubsanacionDocumentacionFerrolController ...");
        this.baseModel = model;
        super.init();
        model.setRegistrytypedata(new SubsanacionDocFerrolDynamicDataModel());
        model.setSubject("Sede electronica - Subsanacion de documentacion");
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
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        getResourceMessage("mandatorydocumentsmiss"), ""));
                return "error";
            }
            if(isRepresentadomanual()) {
                model.getRegistrytypedata().setActuaenrepresentacion(new Integer(1));
            } else {
                model.getRegistrytypedata().setActuaenrepresentacion(new Integer(0));
            }
            respuesta = createNewRegistryInput();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters",
                    queryParameters);
        } catch (Exception e) {
            response = "error";
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters",
                    queryParameters);
        }

        return response;
    }

    public SubsanacionDocFerrolModel getModel() {
        return model;
    }

    public void setModel(SubsanacionDocFerrolModel model) {
        this.model = model;
    }
}

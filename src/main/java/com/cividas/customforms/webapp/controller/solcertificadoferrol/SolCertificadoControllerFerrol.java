package com.cividas.customforms.webapp.controller.solcertificadoferrol;

import com.cividas.customforms.webapp.common.model.solcertificadoferrol.SolCertificadoFerrolDynamicDataModel;
import com.cividas.customforms.webapp.common.model.solcertificadoferrol.SolCertificadoFerrolModel;
import com.cividas.customforms.webapp.controller.base.BaseControllerFerrol;
import org.apache.commons.lang.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "solCertificadoFerrolController")
@ViewScoped
public class SolCertificadoControllerFerrol extends BaseControllerFerrol {

    private static final long serialVersionUID = -2829675096598593114L;
    private boolean bienes;
    private boolean vehiculos;
    private int option_bienes;
    private int vehiculos_option;

    @ManagedProperty("#{solCertificadoFerrolModel}")
    SolCertificadoFerrolModel model;

    @Override
    public void init() {
        log.info("Inicializando el controlador: SolCertificadoFerrolController ...");
        this.baseModel = model;
        super.init();
        model.setRegistrytypedata(new SolCertificadoFerrolDynamicDataModel());
        model.setSubject("Sede electronica - Solicitud de certificado de bienes");
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
            optionValue();
            if (StringUtils.isEmpty(getMandatoryFilesUploaded())) {
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
        if (bienes || vehiculos) {
            return this.sendRequest();
        } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("soli_mandatory_option"), ""));
            return null;
        }
    }

    private void optionValue() {
        if (bienes) {
            model.getRegistrytypedata().setBienes(1);
            if (option_bienes == 0) model.getRegistrytypedata().setPoseebienes(1);
            else model.getRegistrytypedata().setNegativobienes(1);
        }
        if (vehiculos) {
            model.getRegistrytypedata().setVehiculos(1);
            if (vehiculos_option == 0) model.getRegistrytypedata().setPoseevehiculos(1);
            else model.getRegistrytypedata().setNegativovehiculos(1);
        }
    }

    public boolean isBienes() {
        return bienes;
    }

    public void setBienes(boolean bienes) {
        this.bienes = bienes;
    }

    public boolean isVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(boolean vehiculos) {
        this.vehiculos = vehiculos;
    }

    public int getVehiculos_option() {
        return vehiculos_option;
    }

    public void setVehiculos_option(int vehiculos_option) {
        this.vehiculos_option = vehiculos_option;
    }

    public int getOption_bienes() {
        return option_bienes;
    }

    public void setOption_bienes(int option_bienes) {
        this.option_bienes = option_bienes;
    }

    public SolCertificadoFerrolModel getModel() {
        return model;
    }

    public void setModel(SolCertificadoFerrolModel model) {
        this.model = model;
    }
}

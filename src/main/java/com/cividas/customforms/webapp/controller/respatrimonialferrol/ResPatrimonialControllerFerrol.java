package com.cividas.customforms.webapp.controller.respatrimonialferrol;

import com.cividas.customforms.webapp.common.model.respatrimonialferrol.ResPatrimonialFerrolDynamicDataModel;
import com.cividas.customforms.webapp.common.model.respatrimonialferrol.ResPatrimonialFerrolModel;
import com.cividas.customforms.webapp.controller.base.BaseControllerFerrol;
import org.apache.commons.lang.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "resPatrimonialFerrolController")
@ViewScoped
public class ResPatrimonialControllerFerrol extends BaseControllerFerrol {
    private static final long serialVersionUID = -2015324862606063653L;

    private int option;

    @ManagedProperty("#{resPatrimonialFerrolModel}")
    ResPatrimonialFerrolModel model;

    @Override
    public void init() {
        log.info("Inicializando el controlador: ResPatrimonialController ...");
        this.baseModel = model;
        super.init();
        model.setRegistrytypedata(new ResPatrimonialFerrolDynamicDataModel());
        model.setSubject("Sede electronica - Responsabilidad patrimonial");
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

    private void optionValue() {
        if (option == OpcionesEnum.CAIDA.getId()) model.getRegistrytypedata().setCaida(1);
        else if (option == OpcionesEnum.DANOVEHICULO.getId()) model.getRegistrytypedata().setDanosvehiculo(1);
        else if (option == OpcionesEnum.OTROS.getId()) model.getRegistrytypedata().setOutros(1);
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public ResPatrimonialFerrolModel getModel() {
        return model;
    }

    public void setModel(ResPatrimonialFerrolModel model) {
        this.model = model;
    }

    public enum OpcionesEnum {
        CAIDA(0),
        DANOVEHICULO(1),
        OTROS(2);

        private final int id;

        OpcionesEnum(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}

package com.cividas.customforms.webapp.controller.usoviaferrol;

import com.cividas.customforms.webapp.common.model.usoviaferrol.UsoViaFerrolDynamicDataModel;
import com.cividas.customforms.webapp.common.model.usoviaferrol.UsoViaFerrolModel;
import com.cividas.customforms.webapp.controller.base.BaseControllerFerrol;
import org.apache.commons.lang.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "usoViaFerrolController")
@ViewScoped
public class UsoViaControllerFerrol extends BaseControllerFerrol {
    private static final long serialVersionUID = 1739503222797573840L;

    private Integer option = 0;
    private String optionDetail;

    @ManagedProperty("#{usoViaFerrolModel}")
    UsoViaFerrolModel model;

    @Override
    public void init() {
        log.info("Inicializando el controlador: UsoViaFerrolController ...");
        this.baseModel = model;
        super.init();
        model.setRegistrytypedata(new UsoViaFerrolDynamicDataModel());
        model.setSubject("Sede electronica - Uso vía Pública");
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

        if (OpcionesEnum.BARRERA.getId() == option) {
            model.getRegistrytypedata().setBarrera(OpcionesEnum.BARRERA.id);
            model.getRegistrytypedata().setBarreralabel(optionDetail);
        }
        if (OpcionesEnum.CORTE_CIRCULACION.getId() == option) {
            model.getRegistrytypedata().setCortecirculacion(OpcionesEnum.CORTE_CIRCULACION.getId());
        }
        if (OpcionesEnum.ACCESO_VEHICULO.getId() == option) {
            model.getRegistrytypedata().setAccesovehiculo(OpcionesEnum.ACCESO_VEHICULO.getId());
        }
        if (OpcionesEnum.ACCESO_VEHICULO_GRANDE.getId() == option) {
            model.getRegistrytypedata().setAccesovehiculogrande(OpcionesEnum.ACCESO_VEHICULO_GRANDE.getId());
        }
        if (OpcionesEnum.OTROS.getId() == option) {
            model.getRegistrytypedata().setOtro(OpcionesEnum.OTROS.getId());
            model.getRegistrytypedata().setOtrolabel(optionDetail);
        }
    }

    public Integer getOption() {
        return option;
    }

    public void setOption(Integer option) {
        this.option = option;
    }

    public String getOptionDetail() {
        return optionDetail;
    }

    public void setOptionDetail(String optionDetail) {
        this.optionDetail = optionDetail;
    }

    public UsoViaFerrolModel getModel() {
        return model;
    }

    public void setModel(UsoViaFerrolModel model) {
        this.model = model;
    }

    public enum OpcionesEnum {
        BARRERA(5),
        CORTE_CIRCULACION(1),
        ACCESO_VEHICULO(2),
        ACCESO_VEHICULO_GRANDE(3),
        OTROS(4);

        private final int id;

        OpcionesEnum(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

}

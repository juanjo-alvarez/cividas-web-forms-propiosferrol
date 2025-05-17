package com.cividas.customforms.webapp.controller.certempadronamientoferrol;

import com.cividas.customforms.webapp.common.model.certempadronamientoferrol.CertEmpadronamientoFerrolDynamicDataModel;
import com.cividas.customforms.webapp.common.model.certempadronamientoferrol.CertEmpadronamientoFerrolModel;
import com.cividas.customforms.webapp.controller.base.BaseControllerFerrol;
import org.apache.commons.lang.StringUtils;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "certEmpadronamientoFerrolController")
@ViewScoped
public class CertEmpadronamientoControllerFerrol extends BaseControllerFerrol {

    private static final long serialVersionUID = 5981594689898775870L;
    private Integer expongoOption = 0;
    private String expongoDetail;

    private boolean defuncion;
    private boolean historicodefuncion;
    private boolean historicoconvivencia;
    private boolean otros;

    @ManagedProperty("#{certEmpadronamientoFerrolModel}")
    CertEmpadronamientoFerrolModel model;

    @Override
    public void init() {
        log.info("Inicializando el controlador: CertEmpadronamientoFerrolController ...");
        this.baseModel = model;
        super.init();
        model.setRegistrytypedata(new CertEmpadronamientoFerrolDynamicDataModel());
        model.setSubject("Sede electronica - Certificado de empadronamiento");
    }

    @Override
    public String receiveParentData() {
        log.info("Recibiendo los parametros de sesion de la sede ...");
        super.receiveParentData();
        return null;
    }

    public void cancelarCamposSeleccionados() {
        this.defuncion = false;
        this.historicodefuncion = false;
        this.historicoconvivencia = false;
        this.otros = false;

        model.getRegistrytypedata().setHistoricodefuncionlabel(null);
        model.getRegistrytypedata().setHistoricoconvivencialabel(null);
        model.getRegistrytypedata().setOtroslabel(null);
    }

    @Override
    protected void loadDataMasters() {
        super.loadDataMasters();
    }

    /**
     * Se invoca en el xhtml para controlar que se selecciona al menos una opcion "expongo" antes de enviar
     *
     * @return sendRequest, null si se ha producido un error
     */
    public void validateSolicitud() {
        boolean isValid = defuncion || historicodefuncion || historicoconvivencia || otros;

        if (!isValid) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("soli_mandatory_option"), ""));
        } else {
            PrimeFaces.current().executeScript("PF('confirmdoc').show();");
        }
    }

    @Override
    public String sendRequest() {
        String response = "success";
        try {
            expongoValue();
            solicitaValue();

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

    private void solicitaValue() {
        if (defuncion) model.getRegistrytypedata().setDefuncion(1);
        if (historicoconvivencia) model.getRegistrytypedata().setHistoricoconvivencia(1);
        if (historicodefuncion) model.getRegistrytypedata().setHistoricodefuncion(1);
        if (otros) model.getRegistrytypedata().setOtros(1);
    }

    private void expongoValue() {
        if (expongoOption.equals(SolicitoOpcionesEnum.OBTENCION_AYUDA.getId())) {
            model.getRegistrytypedata().setObtencionayuda(1);
            model.getRegistrytypedata().setObtencionayudalabel(expongoDetail);
        }
        if (expongoOption.equals(SolicitoOpcionesEnum.MATRIMONIO.getId())) {
            model.getRegistrytypedata().setMatrimonio(1);
        }
        if (expongoOption.equals(SolicitoOpcionesEnum.IMPUESTOS.getId())) {
            model.getRegistrytypedata().setImpuesto(1);
        }
        if (expongoOption.equals(SolicitoOpcionesEnum.OTROS_FINES.getId())) {
            model.getRegistrytypedata().setOtrosfines(1);
            model.getRegistrytypedata().setOtrosfineslabel(expongoDetail);
        }

    }

    public CertEmpadronamientoFerrolModel getModel() {
        return model;
    }

    public void setModel(CertEmpadronamientoFerrolModel model) {
        this.model = model;
    }

    public Integer getExpongoOption() {
        return expongoOption;
    }

    public void setExpongoOption(Integer expongoOption) {
        this.expongoOption = expongoOption;
    }

    public String getExpongoDetail() {
        return expongoDetail;
    }

    public void setExpongoDetail(String expongoDetail) {
        this.expongoDetail = expongoDetail;
    }

    public boolean isDefuncion() {
        return defuncion;
    }

    public void setDefuncion(boolean defuncion) {
        this.defuncion = defuncion;
    }

    public boolean isHistoricodefuncion() {
        return historicodefuncion;
    }

    public void setHistoricodefuncion(boolean historicodefuncion) {
        this.historicodefuncion = historicodefuncion;
    }

    public boolean isHistoricoconvivencia() {
        return historicoconvivencia;
    }

    public void setHistoricoconvivencia(boolean historicoconvivencia) {
        this.historicoconvivencia = historicoconvivencia;
    }

    public boolean isOtros() {
        return otros;
    }

    public void setOtros(boolean otros) {
        this.otros = otros;
    }

    public enum SolicitoOpcionesEnum {
        OBTENCION_AYUDA(0),
        MATRIMONIO(1),
        IMPUESTOS(2),
        OTROS_FINES(3);

        private final int id;

        SolicitoOpcionesEnum(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

}

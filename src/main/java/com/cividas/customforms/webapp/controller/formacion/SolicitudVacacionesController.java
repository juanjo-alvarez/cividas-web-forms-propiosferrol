package com.cividas.customforms.webapp.controller.formacion;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.cividas.customforms.webapp.common.exceptions.CividasQueryDataException;
import com.cividas.customforms.webapp.common.model.formacion.SolVacDynamicDataModel;
import com.cividas.customforms.webapp.common.model.formacion.SolVacModel;
import com.cividas.customforms.webapp.controller.base.BaseControllerFerrol;
import com.cividas.customforms.webapp.utils.CollectionsUtils;
import com.cividas.customforms.webapp.utils.MastersUtils;

@ManagedBean(name = "solicitudVacacionesController")
@ViewScoped
public class SolicitudVacacionesController extends BaseControllerFerrol {

    private static final long serialVersionUID = 3944811193764256986L;

    @ManagedProperty("#{solicitudVacacionesModel}")
    SolVacModel model;
    
    private LinkedHashMap<String, String> destinosVacaciones = new LinkedHashMap<String, String>();

    @Override
    public void init() {
        log.info("Inicializando el controlador: SolicitudVacacionesController ...");
        this.baseModel = model;
        super.init();
        model.setRegistrytypedata(new SolVacDynamicDataModel());
        model.setSubject("Sede electronica - Solicitud vacaciones");
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
        try {	
			HashMap hkeysDestinos=new HashMap<Object, Object>();
			Map<String, Object> destinosVacacionesMap= queryCividasMaster("edestinovacaciones",hkeysDestinos, CollectionsUtils.buildList("coddestino", "nomdestino"));
			this.destinosVacaciones = MastersUtils.parseMasterLinkedHashMap(destinosVacacionesMap, "coddestino","nomdestino");			
		} catch (CividasQueryDataException cqe) {
			log.error("Se ha producido un error al cargar el maestro de los destinos de las solicitudes. A continuación se mostrará la traza del error",cqe);
		}

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

    public SolVacModel getModel() {
        return model;
    }

    public void setModel(SolVacModel model) {
        this.model = model;
    }

	public LinkedHashMap<String, String> getDestinosVacaciones() {
		return destinosVacaciones;
	}

	public void setDestinosVacaciones(LinkedHashMap<String, String> destinosVacaciones) {
		this.destinosVacaciones = destinosVacaciones;
	}

}

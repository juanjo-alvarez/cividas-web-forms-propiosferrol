package com.cividas.customforms.webapp.controller.solregistrohidrologico;

import com.cividas.customforms.webapp.IServiceVerification;
import com.cividas.customforms.webapp.ServiceVerification;
import com.cividas.customforms.webapp.common.exceptions.CividasQueryDataException;
import com.cividas.customforms.webapp.common.model.solregistrohidrologico.SolRegistroHidrologicoDynamicDataModel;
import com.cividas.customforms.webapp.common.model.solregistrohidrologico.SolRegistroHidrologicoModel;
import com.cividas.customforms.webapp.controller.base.BaseController;
import com.cividas.customforms.webapp.utils.CollectionsUtils;
import com.cividas.customforms.webapp.utils.MastersUtils;
import com.cividas.web.common.FieldNamesWeb;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ManagedBean(name = "solRegistroHidrologicoController")
@ViewScoped
public class SolRegistroHidrologicoController extends BaseController {

    private static final long serialVersionUID = -5438237965295570675L;

    @ManagedProperty("#{solRegistroHidrologico}")
    SolRegistroHidrologicoModel model;

    private LinkedHashMap<String, String> anhoHidrologico = new LinkedHashMap<>();
    private Integer option = 4;
    private String numregistro = "";
    private boolean agreeWithPolicy = false;

    @Override
    public void init() {
        log.info("Inicializando el controlador: SolRegistroHidrologicoController ...");
        this.baseModel = model;
        super.init();
        model.setRegistrytypedata(new SolRegistroHidrologicoDynamicDataModel());
    }

    @Override
    public String receiveParentData() {
        log.info("Recibiendo los parámetros de sessión de la sede ...");
        super.receiveParentData();

        return null;
    }

    @Override
    protected void loadDataMasters() {
        super.loadDataMasters();
        log.info("Cargando maestros propios del procedimiento año hidrológico");

        try{
            Map<String, Object> anhoHidrologicoQueryResult = queryCividasMaster("eanhohidrologico", new HashMap<>(), new ArrayList<Object>());
            this.anhoHidrologico = MastersUtils.parseMasterLinkedHashMap(anhoHidrologicoQueryResult, "descripcion", "descripcion");
        }catch (CividasQueryDataException cqe){
            log.error("Se ha producido un error al cargar el maestro del Año Hidrologico", cqe);
        }
    }

    @Override
    public String sendRequest() {
        String response = "success";
        optionValue();

        try {
			// Envio de la solicitud. Aqui se incluir? la l?gica propia del env?o de cada formulario, en caso de que exista
			// Alta del registro en Cividas
			if("".equalsIgnoreCase(getMandatoryFilesUploaded())) {
				log.error("No se adjuntaron todos los documentos.");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("mandatorydocumentsmiss"), ""));
				return "error";
			}
            respuesta = createNewRegistryInput();
			if(respuesta.getIdRegistryReport() == null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, getResourceMessage("no_report"), ""));
			}
			// Almacenamos la respuesta en el contexto
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);

			// Almacenamos la respuesta en el contexto
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters", queryParameters);

        } catch (Exception e) {
			// En caso de error, redirección a una página de error
            response = "error";
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("respuesta", respuesta);
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("queryParameters", queryParameters);
        }
		// En caso de exito, redirigir a una página de éxito.
        return response;
    }

    public String checkDocumentation() throws IOException, CividasQueryDataException {
        if(checkDNIFormat(model.getRegistrytypedata().getCif())){
            Map<String, Object> attachmentQueryResult = queryCividasMaster(FieldNamesWeb.ECIVIDASPARAMETERS,
                    CollectionsUtils.buildMap(FieldNamesWeb.PARAMETER, "Validar.Documento.Identidad.Cartagena.Active"), new ArrayList<Object>());
            if(attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE) != null
                    && ((List) attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).get(0).equals("1")) {

                attachmentQueryResult = queryCividasMaster(FieldNamesWeb.ECIVIDASPARAMETERS,
                        CollectionsUtils.buildMap(FieldNamesWeb.PARAMETER, "Validar.Documento.Identidad.Cartagena.URL"), new ArrayList<Object>());
                if(attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE) != null){
                    IServiceVerification verification = new ServiceVerification(((List) attachmentQueryResult.get(FieldNamesWeb.STRINGVALUE)).get(0).toString());
                    int resultadoValidacion = verification.verificarTitular(model.getRegistrytypedata().getCif());

                    if(resultadoValidacion == 1){
                        return this.sendRequest();
                    } else if (resultadoValidacion == 0){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("NO_VALIDATION_DONE"), ""));
                        return null;
                    }
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("SERVICE_ERROR"), ""));
                    return null;
                }

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("BAD_SERVICE"), ""));
                return null;
            }

            return this.sendRequest();

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("registro_cif_validation"), ""));
            return null;
        }
    }


    private boolean checkDNIFormat(String dni){
        Pattern patron = Pattern.compile("[a-zA-Z0-9]*");
        Matcher matcher = patron.matcher(dni);
        return matcher.matches();
    }

    private boolean checkSoloNumeros(String texto){
        Pattern patron = Pattern.compile("[0-9,]*");
        Matcher matcher = patron.matcher(texto);
        return matcher.matches();
    }

    public String comprobarFormulario() throws CividasQueryDataException, IOException {
        if (!checkSoloNumeros(model.getRegistrytypedata().getSuperficie())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("registro_superficie_validacion"), ""));
            return null;
        }
        return checkDocumentation();
    }

    private void optionValue(){
        if(option.equals(Opciones.NUEVA.getId())){
            model.getRegistrytypedata().setIsubsanacion(Opciones.NUEVA.getNombre());
            model.getRegistrytypedata().setNumregistro("");
        } else if (option.equals(Opciones.SUBSANACION.getId())) {
            model.getRegistrytypedata().setIsubsanacion(Opciones.SUBSANACION.getNombre());
            model.getRegistrytypedata().setNumregistro(numregistro);
        } else if (option.equals(Opciones.MODIFICACION.getId())) {
            model.getRegistrytypedata().setIsubsanacion(Opciones.MODIFICACION.getNombre());
            model.getRegistrytypedata().setNumregistro("");
        } else {
            model.getRegistrytypedata().setIsubsanacion(Opciones.ACTUALIZACION.getNombre());
            model.getRegistrytypedata().setNumregistro("");
        }
    }


    public enum Opciones{
        NUEVA("Nueva", 0),
        SUBSANACION("Subsanación", 1),
        ACTUALIZACION("Actualización", 2),
        MODIFICACION("Modificación substancial", 3);
        private final String nombre;
        private final int id;
        private Opciones(String nombre, int id){
            this.nombre = nombre;
            this.id = id;
        }
        public String getNombre() {
            return nombre;
        }

        public int getId() {
            return id;
        }
    }


    public Integer getOption() { return option; }
    public void setOption(Integer option) { this.option = option; }
    public String getNumregistro() { return numregistro; }
    public void setNumregistro(String numregistro) { this.numregistro = numregistro; }
    public LinkedHashMap<String, String> getAnhoHidrologico() { return anhoHidrologico; }
    public void setAnhoHidrologico(LinkedHashMap<String, String> anhoHidrologico) { this.anhoHidrologico = anhoHidrologico; }

    public SolRegistroHidrologicoModel getModel() { return model; }

    public void setModel(SolRegistroHidrologicoModel model) { this.model = model; }



    public boolean isAgreeWithPolicy() {
        return agreeWithPolicy;
    }
    public void setAgreeWithPolicy(boolean agreeWithPolicy) {
        this.agreeWithPolicy = agreeWithPolicy;
    }
}

package com.cividas.customforms.webapp.common.model.accidentetraficoferrol;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class AccidenteTraficoFerrolDynamicDataModel implements Serializable {
    private static final long serialVersionUID = -6106519643543368118L;

    private boolean existendilixencias;
    private boolean desconozcodilixencias;
    private String dilixenciasprevias;
    private String fecha;
    private String datosvehiculo;
    private String datosinteresado;
    private Number actuaenrepresentacion;

    public boolean isExistendilixencias() {
        return existendilixencias;
    }

    public void setExistendilixencias(boolean existendilixencias) {
        this.existendilixencias = existendilixencias;
    }

    public boolean isDesconozcodilixencias() {
        return desconozcodilixencias;
    }

    public void setDesconozcodilixencias(boolean desconozcodilixencias) {
        this.desconozcodilixencias = desconozcodilixencias;
    }

    public String getDilixenciasprevias() {
        return dilixenciasprevias;
    }

    public void setDilixenciasprevias(String dilixenciasprevias) {
        this.dilixenciasprevias = dilixenciasprevias;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDatosvehiculo() {
        return datosvehiculo;
    }

    public void setDatosvehiculo(String datosvehiculo) {
        this.datosvehiculo = datosvehiculo;
    }

    public String getDatosinteresado() {
        return datosinteresado;
    }

    public void setDatosinteresado(String datosinteresado) {
        this.datosinteresado = datosinteresado;
    }

    public Number getActuaenrepresentacion() {
        return actuaenrepresentacion;
    }

    public void setActuaenrepresentacion(Number actuaenrepresentacion) {
        this.actuaenrepresentacion = actuaenrepresentacion;
    }
}

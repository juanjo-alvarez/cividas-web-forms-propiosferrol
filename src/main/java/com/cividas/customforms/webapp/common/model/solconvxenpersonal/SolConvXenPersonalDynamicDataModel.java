package com.cividas.customforms.webapp.common.model.solconvxenpersonal;

import com.cividas.customforms.webapp.common.model.base.JsonMultipleData;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class SolConvXenPersonalDynamicDataModel implements Serializable {

    private static final long serialVersionUID = -7490272279324150635L;

    private Number consenttribute;
    private Number declararesponsablesok;
    private String convocatoriaconfigwebgal;
    private String tituloconfigwebgal;
    private String anhoconfigweb;
    private String escala;
    private String subescala;
    private String tipoconv;
    private String plaza;
    private JsonMultipleData jsonMultipleData;

    public JsonMultipleData getJsonMultipleData() {
        return jsonMultipleData;
    }

    public void setJsonMultipleData(JsonMultipleData jsonMultipleData) {
        this.jsonMultipleData = jsonMultipleData;
    }

    public Number getConsenttribute() {
        return consenttribute;
    }

    public void setConsenttribute(Number consenttribute) {
        this.consenttribute = consenttribute;
    }

    public Number getDeclararesponsablesok() {
        return declararesponsablesok;
    }

    public void setDeclararesponsablesok(Number declararesponsablesok) {
        this.declararesponsablesok = declararesponsablesok;
    }

    public String getConvocatoriaconfigwebgal() {
        return convocatoriaconfigwebgal;
    }

    public void setConvocatoriaconfigwebgal(String convocatoriaconfigwebgal) {
        this.convocatoriaconfigwebgal = convocatoriaconfigwebgal;
    }

    public String getAnhoconfigweb() {
        return anhoconfigweb;
    }

    public void setAnhoconfigweb(String anhoconfigweb) {
        this.anhoconfigweb = anhoconfigweb;
    }

    public String getTituloconfigwebgal() {
        return tituloconfigwebgal;
    }

    public void setTituloconfigwebgal(String tituloconfigwebgal) {
        this.tituloconfigwebgal = tituloconfigwebgal;
    }

    public String getEscala() {
        return escala;
    }

    public void setEscala(String escala) {
        this.escala = escala;
    }

    public String getSubescala() {
        return subescala;
    }

    public void setSubescala(String subescala) {
        this.subescala = subescala;
    }

    public String getTipoconv() {
        return tipoconv;
    }

    public void setTipoconv(String tipoconv) {
        this.tipoconv = tipoconv;
    }

    public String getPlaza() {
        return plaza;
    }

    public void setPlaza(String plaza) {
        this.plaza = plaza;
    }
}

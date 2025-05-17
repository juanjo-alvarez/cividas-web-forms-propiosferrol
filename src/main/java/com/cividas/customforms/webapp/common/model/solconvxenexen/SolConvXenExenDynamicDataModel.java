package com.cividas.customforms.webapp.common.model.solconvxenexen;

import com.cividas.customforms.webapp.common.model.base.JsonMultipleData;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class SolConvXenExenDynamicDataModel implements Serializable {

    private static final long serialVersionUID = -9157344448877789284L;

    private Number consenttribute;
    private Number declararesponsablesok;

    private Number discapacidade;
    private Number familianumerosa;
    private Number persoaluvigo;
    private Number vitimaterrorxenero;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Number getDiscapacidade() {
        return discapacidade;
    }

    public void setDiscapacidade(Number discapacidade) {
        this.discapacidade = discapacidade;
    }

    public Number getFamilianumerosa() {
        return familianumerosa;
    }

    public void setFamilianumerosa(Number familianumerosa) {
        this.familianumerosa = familianumerosa;
    }

    public Number getPersoaluvigo() {
        return persoaluvigo;
    }

    public void setPersoaluvigo(Number persoaluvigo) {
        this.persoaluvigo = persoaluvigo;
    }

    public Number getVitimaterrorxenero() {
        return vitimaterrorxenero;
    }

    public void setVitimaterrorxenero(Number vitimaterrorxenero) {
        this.vitimaterrorxenero = vitimaterrorxenero;
    }
}

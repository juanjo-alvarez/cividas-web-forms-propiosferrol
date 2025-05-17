package com.cividas.customforms.webapp.common.model.solregistrohidrologico;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name="solRegistroHidrologico")
@ViewScoped
public class SolRegistroHidrologicoModel extends InsertRegistry {

    private static final long serialVersionUID = -7394695331422501649L;

    @XmlElement
    SolRegistroHidrologicoDynamicDataModel registrytypedata;


    public SolRegistroHidrologicoDynamicDataModel getRegistrytypedata() {
        return this.registrytypedata;
    }

    public void setRegistrytypedata(SolRegistroHidrologicoDynamicDataModel registrytypedata) {
        this.registrytypedata = registrytypedata;
    }

}

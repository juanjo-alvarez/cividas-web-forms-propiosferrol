package com.cividas.customforms.webapp.common.model.obmayoresferrol;

import com.cividas.customforms.webapp.common.model.base.InsertRegistryFerrol;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name = "obrasMayoresFerrolModel")
@ViewScoped
public class ObrasMayoresFerrolModel extends InsertRegistryFerrol {

    private static final long serialVersionUID = 3776665948091813472L;

    @XmlElement
    ObrasMayoresFerrolDynamicDataModel registrytypedata;


    public ObrasMayoresFerrolDynamicDataModel getRegistrytypedata() {
        return this.registrytypedata;
    }

    public void setRegistrytypedata(ObrasMayoresFerrolDynamicDataModel registrytypedata) {
        this.registrytypedata = registrytypedata;
    }

}
package com.cividas.customforms.webapp.common.model.obmenonesferrol;

import com.cividas.customforms.webapp.common.model.base.InsertRegistryFerrol;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name = "obrasMenoresFerrolModel")
@ViewScoped
public class ObrasMenoresFerrolModel extends InsertRegistryFerrol {

    private static final long serialVersionUID = 3459903788888612302L;

    @XmlElement
    ObrasMenoresFerrolDynamicDataModel registrytypedata;


    public ObrasMenoresFerrolDynamicDataModel getRegistrytypedata() {
        return this.registrytypedata;
    }

    public void setRegistrytypedata(ObrasMenoresFerrolDynamicDataModel registrytypedata) {
        this.registrytypedata = registrytypedata;
    }

}
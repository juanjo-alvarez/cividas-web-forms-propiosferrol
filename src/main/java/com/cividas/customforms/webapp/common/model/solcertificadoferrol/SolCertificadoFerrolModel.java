package com.cividas.customforms.webapp.common.model.solcertificadoferrol;

import com.cividas.customforms.webapp.common.model.base.InsertRegistryFerrol;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name = "solCertificadoFerrolModel")
@ViewScoped
public class SolCertificadoFerrolModel extends InsertRegistryFerrol {

    private static final long serialVersionUID = 5101492938808481595L;

    @XmlElement
    SolCertificadoFerrolDynamicDataModel registrytypedata;


    public SolCertificadoFerrolDynamicDataModel getRegistrytypedata() {
        return this.registrytypedata;
    }

    public void setRegistrytypedata(SolCertificadoFerrolDynamicDataModel registrytypedata) {
        this.registrytypedata = registrytypedata;
    }

}

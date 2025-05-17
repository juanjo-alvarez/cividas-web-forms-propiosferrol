package com.cividas.customforms.webapp.common.model.certempadronamientoferrol;

import com.cividas.customforms.webapp.common.model.base.InsertRegistryFerrol;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name = "certEmpadronamientoFerrolModel")
@ViewScoped
public class CertEmpadronamientoFerrolModel extends InsertRegistryFerrol {

    private static final long serialVersionUID = 2064878671449168368L;

    @XmlElement
    CertEmpadronamientoFerrolDynamicDataModel registrytypedata;


    public CertEmpadronamientoFerrolDynamicDataModel getRegistrytypedata() {
        return this.registrytypedata;
    }

    public void setRegistrytypedata(CertEmpadronamientoFerrolDynamicDataModel registrytypedata) {
        this.registrytypedata = registrytypedata;
    }

}

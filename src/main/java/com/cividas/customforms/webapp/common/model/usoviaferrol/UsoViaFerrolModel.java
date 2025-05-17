package com.cividas.customforms.webapp.common.model.usoviaferrol;

import com.cividas.customforms.webapp.common.model.base.InsertRegistryFerrol;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name = "usoViaFerrolModel")
@ViewScoped
public class UsoViaFerrolModel extends InsertRegistryFerrol {
    private static final long serialVersionUID = 1284177271465630196L;

    @XmlElement
    UsoViaFerrolDynamicDataModel registrytypedata;

    public UsoViaFerrolDynamicDataModel getRegistrytypedata() {
        return registrytypedata;
    }

    public void setRegistrytypedata(UsoViaFerrolDynamicDataModel registrytypedata) {
        this.registrytypedata = registrytypedata;
    }
}

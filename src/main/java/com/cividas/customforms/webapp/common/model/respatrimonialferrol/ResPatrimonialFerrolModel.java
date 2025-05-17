package com.cividas.customforms.webapp.common.model.respatrimonialferrol;

import com.cividas.customforms.webapp.common.model.base.InsertRegistryFerrol;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name = "resPatrimonialFerrolModel")
@ViewScoped
public class ResPatrimonialFerrolModel extends InsertRegistryFerrol {

    private static final long serialVersionUID = -1146372403794798871L;

    @XmlElement
    ResPatrimonialFerrolDynamicDataModel registrytypedata;

    public ResPatrimonialFerrolDynamicDataModel getRegistrytypedata() {
        return registrytypedata;
    }

    public void setRegistrytypedata(ResPatrimonialFerrolDynamicDataModel registrytypedata) {
        this.registrytypedata = registrytypedata;
    }
}

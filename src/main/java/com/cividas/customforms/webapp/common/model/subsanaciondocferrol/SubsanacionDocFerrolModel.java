package com.cividas.customforms.webapp.common.model.subsanaciondocferrol;

import com.cividas.customforms.webapp.common.model.base.InsertRegistryFerrol;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name = "subsanacionDocFerrolModel")
@ViewScoped
public class SubsanacionDocFerrolModel extends InsertRegistryFerrol {

    private static final long serialVersionUID = 920659529245647915L;

    @XmlElement
    SubsanacionDocFerrolDynamicDataModel registrytypedata;

    public SubsanacionDocFerrolDynamicDataModel getRegistrytypedata() {
        return this.registrytypedata;
    }

    public void setRegistrytypedata(SubsanacionDocFerrolDynamicDataModel registrytypedata) {
        this.registrytypedata = registrytypedata;
    }

}

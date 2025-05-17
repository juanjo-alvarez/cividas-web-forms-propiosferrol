package com.cividas.customforms.webapp.common.model.genericaferrol;

import com.cividas.customforms.webapp.common.model.base.InsertRegistryFerrol;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name = "solGenericaFerrolModel")
@ViewScoped
public class SolGenericaFerrolModel extends InsertRegistryFerrol {

    private static final long serialVersionUID = 904320684255594143L;

    @XmlElement
    SolGenericaFerrolDynamicDataModel registrytypedata;


    public SolGenericaFerrolDynamicDataModel getRegistrytypedata() {
        return this.registrytypedata;
    }

    public void setRegistrytypedata(SolGenericaFerrolDynamicDataModel registrytypedata) {
        this.registrytypedata = registrytypedata;
    }

}

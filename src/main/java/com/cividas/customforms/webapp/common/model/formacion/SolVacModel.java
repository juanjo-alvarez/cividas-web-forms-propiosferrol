package com.cividas.customforms.webapp.common.model.formacion;

import com.cividas.customforms.webapp.common.model.base.InsertRegistryFerrol;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name = "solicitudVacacionesModel")
@ViewScoped
public class SolVacModel extends InsertRegistryFerrol {

    private static final long serialVersionUID = 904320684255594143L;

    @XmlElement
    SolVacDynamicDataModel registrytypedata;


    public SolVacDynamicDataModel getRegistrytypedata() {
        return this.registrytypedata;
    }

    public void setRegistrytypedata(SolVacDynamicDataModel registrytypedata) {
        this.registrytypedata = registrytypedata;
    }

}

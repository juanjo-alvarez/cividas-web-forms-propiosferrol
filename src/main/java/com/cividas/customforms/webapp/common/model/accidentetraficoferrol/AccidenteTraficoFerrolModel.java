package com.cividas.customforms.webapp.common.model.accidentetraficoferrol;

import com.cividas.customforms.webapp.common.model.base.InsertRegistryFerrol;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ManagedBean(name = "accidenteTraficoFerrolModel")
@ViewScoped
public class AccidenteTraficoFerrolModel extends InsertRegistryFerrol {

    private static final long serialVersionUID = 392930068127648963L;

    @XmlElement
    AccidenteTraficoFerrolDynamicDataModel registrytypedata;


    public AccidenteTraficoFerrolDynamicDataModel getRegistrytypedata() {
        return this.registrytypedata;
    }

    public void setRegistrytypedata(AccidenteTraficoFerrolDynamicDataModel registrytypedata) {
        this.registrytypedata = registrytypedata;
    }
}

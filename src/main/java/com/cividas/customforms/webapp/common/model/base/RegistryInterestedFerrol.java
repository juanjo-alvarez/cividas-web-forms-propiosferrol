package com.cividas.customforms.webapp.common.model.base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class RegistryInterestedFerrol {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private Number idindividualsmaster;

    @XmlElement
    protected RegistryInterestedAddress interestedaddress;

    @XmlElement
    private String identificationdocumenttypedesc;

    @XmlElement
    private String identificationnumber;

    @XmlElement
    private String name;

    @XmlElement
    private String surname1;

    @XmlElement
    private String surname2;

    @XmlElement
    private String completename;

    @XmlElement
    private Number idorganism;

    @XmlElement
    private Number iddepartment;

    @XmlElement
    private Number idarea;

    @XmlElement
    private Date birthdate;

    @XmlElement
    private Number idsex;

    @XmlElement
    private Number networknotification;

    @XmlElement
    private String email;

    @XmlElement
    private String telephone;

    @XmlElement
    private String cellphone;

    @XmlElement
    private String fax;

    @XmlElement
    private Number isprincipal;

    @XmlElement
    private Number idregrepresentedby;

    @XmlElement
    private Number idinterestedas;

    @XmlElement
    private boolean savecontactdata;

    @XmlElement
    private Number webvisible;


    public Number getIdindividualsmaster() {
        return idindividualsmaster;
    }

    public void setIdindividualsmaster(Number idindividualsmaster) {
        this.idindividualsmaster = idindividualsmaster;
    }

    public RegistryInterestedAddress getInterestedaddress() {
        return interestedaddress;
    }

    public void setInterestedaddress(RegistryInterestedAddress interestedaddress) {
        this.interestedaddress = interestedaddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public String getCompletename() {
        return completename;
    }

    public void setCompletename(String completename) {
        this.completename = completename;
    }

    public Number getIsprincipal() {
        return isprincipal;
    }

    public void setIsprincipal(Number isprincipal) {
        this.isprincipal = isprincipal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Number getIdregrepresentedby() {
        return idregrepresentedby;
    }

    public String getIdentificationdocumenttypedesc() {
        return identificationdocumenttypedesc;
    }

    public void setIdentificationdocumenttypedesc(String identificationdocumenttypedesc) {
        this.identificationdocumenttypedesc = identificationdocumenttypedesc;
    }

    public void setIdregrepresentedby(Number idregrepresentedby) {
        this.idregrepresentedby = idregrepresentedby;
    }

    public Number getIdinterestedas() {
        return idinterestedas;
    }

    public void setIdinterestedas(Number idinterestedas) {
        this.idinterestedas = idinterestedas;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Number getIdorganism() {
        return idorganism;
    }

    public void setIdorganism(Number idorganism) {
        this.idorganism = idorganism;
    }

    public Number getIddepartment() {
        return iddepartment;
    }

    public void setIddepartment(Number iddepartment) {
        this.iddepartment = iddepartment;
    }

    public Number getIdarea() {
        return idarea;
    }

    public void setIdarea(Number idarea) {
        this.idarea = idarea;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Number getIdsex() {
        return idsex;
    }

    public void setIdsex(Number idsex) {
        this.idsex = idsex;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getIdentificationnumber() {
        return identificationnumber;
    }

    public void setIdentificationnumber(String identificationnumber) {
        this.identificationnumber = identificationnumber;
    }

    public Number getNetworknotification() {
        return networknotification;
    }

    public void setNetworknotification(Number networknotification) {
        this.networknotification = networknotification;
    }

    public boolean isSavecontactdata() {
        return savecontactdata;
    }

    public void setSavecontactdata(boolean savecontactdata) {
        this.savecontactdata = savecontactdata;
    }

    public Number getWebvisible() {
        return webvisible;
    }

    public void setWebvisible(Number webvisible) {
        this.webvisible = webvisible;
    }

}

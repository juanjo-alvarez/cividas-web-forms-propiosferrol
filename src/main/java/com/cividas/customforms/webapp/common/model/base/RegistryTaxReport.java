package com.cividas.customforms.webapp.common.model.base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@XmlRootElement
public class RegistryTaxReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private Integer idattachmentdata;

    @XmlElement
    private String idtransaction;

    @XmlElement
    private Number idindividualmaster;

    @XmlElement
    private String csv;

    @XmlElement
    private Date date_payment;

    @XmlElement
    private String documentmastertypename;

    @XmlElement
    private Number iddocumentmastertype;

    @XmlElement
    private String attachmentfilename;

    @XmlElement
    private String dateFormat;

    public Integer getIdattachmentdata() {
        return idattachmentdata;
    }

    public void setIdattachmentdata(Integer idattachmentdata) {
        this.idattachmentdata = idattachmentdata;
    }

    public String getIdtransaction() {
        return idtransaction;
    }

    public void setIdtransaction(String idtransaction) {
        this.idtransaction = idtransaction;
    }

    public Number getIdindividualmaster() {
        return idindividualmaster;
    }

    public void setIdindividualmaster(Number idindividualmaster) {
        this.idindividualmaster = idindividualmaster;
    }

    public String getCsv() {
        return csv;
    }

    public void setCsv(String csv) {
        this.csv = csv;
    }

    public Date getDate_payment() {
        return date_payment;
    }

    public void setDate_payment(Date date_payment) {
        this.date_payment = date_payment;
    }

    public String getDocumentmastertypename() {
        return documentmastertypename;
    }

    public void setDocumentmastertypename(String documentmastertypename) {
        this.documentmastertypename = documentmastertypename;
    }

    public Number getIddocumentmastertype() {
        return iddocumentmastertype;
    }

    public void setIddocumentmastertype(Number iddocumentmastertype) {
        this.iddocumentmastertype = iddocumentmastertype;
    }

    public String getAttachmentfilename() {
        return attachmentfilename;
    }

    public void setAttachmentfilename(String attachmentfilename) {
        this.attachmentfilename = attachmentfilename;
    }

    public String getDateFormat(){
        return this.dateFormat;
    }

    public void setDateFormat(Date date_payment) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        this.dateFormat = sdf.format(date_payment);;
    }
}
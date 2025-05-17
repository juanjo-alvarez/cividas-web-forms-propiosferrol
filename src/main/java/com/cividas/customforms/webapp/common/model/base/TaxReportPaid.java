package com.cividas.customforms.webapp.common.model.base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@XmlRootElement
public class TaxReportPaid implements Serializable {

    private static final long serialVersionUID = -4742423154019523327L;
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
    private int totalReports;
    @XmlElement
    private String documentmastertypename;
    @XmlElement
    private Number iddocumentmastertype;
    @XmlElement
    private String attachmentfilename;

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

    public int getTotalReports() {
        return totalReports;
    }

    public void setTotalReports(int totalReports) {
        this.totalReports = totalReports;
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(date_payment);
    }
}

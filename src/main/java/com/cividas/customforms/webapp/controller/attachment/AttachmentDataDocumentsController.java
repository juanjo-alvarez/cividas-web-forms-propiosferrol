package com.cividas.customforms.webapp.controller.attachment;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.ListDataModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cividas.customforms.webapp.common.model.attachment.AttachmentModel;
import com.cividas.customforms.webapp.controller.base.BaseController;


@ManagedBean(name = "attachmentDataDocumentsController", eager = true)
@ViewScoped
public class AttachmentDataDocumentsController extends BaseController implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(AttachmentDataDocumentsController.class);
	
	
	private static final boolean SHOW_ONLY_WEBPUBLICABLE = true;

	private Number idattachmentdata;
	private Number idprocedure;
	private String procedurecode;
	private Number idregistry;
	private Number idtask;

	private Number idregulation;

	private Number attachmentfilesize;	
	
	private String attachmentfilename;
	private String attachmentdataname;
	
	private String attachmentdatadesc;

	private Date insertdate;
	private String mimetype;
	
	private String csv;
	private String hash;
	
	private String documentmastertypename;
	private String documentmastertypedesc;
	
	@ManagedProperty("#{attachmentModel}")
	AttachmentModel model;
	
	private Number webpublishabledoc=1;
	
	@Override
	public void init (){
		log.info("Inicializando el controlador: Attachment ..." );
		this.baseModel = model;
		super.init();
		
		attachmentfilename = "name";
		attachmentdatadesc = "description";
		attachmentfilesize = 150;
	}
	
	public void setIdattachmentdata(Number idattachmentdata){
		this.idattachmentdata = idattachmentdata;
	}

	public Number getIdattachmentdata() {
		return this.idattachmentdata;
	}
	
	public void setIdprocedure(Number idprocedure){
		this.idprocedure = idprocedure;
	}

	public String getProcedurecode() {
		return procedurecode;
	}


	public void setProcedurecode(String procedurecode) {
		this.procedurecode = procedurecode;
	}


	public Number getIdprocedure() {
		return this.idprocedure;
	}
	
	
	public void setAttachmentfilesize(Number attachmentfilesize){
		this.attachmentfilesize = attachmentfilesize;
	}

	public Number getAttachmentfilesize() {
		if (this.attachmentfilesize != null) {
			return new Double(this.attachmentfilesize.doubleValue());
		}
				
		return this.attachmentfilesize;
	}
	
	public void setAttachmentfilename(String attachmentfilename){
		this.attachmentfilename = attachmentfilename;
	}

	public String getAttachmentfilename() {
		return this.attachmentfilename;
	}

	public void setAttachmentdataname(String attachmentdataname){
		this.attachmentdataname = attachmentdataname;
	}

	public String getAttachmentdataname() {
		return this.attachmentdataname;
	}

	/**
	 * @return the attachmentdatadesc
	 */
	public String getAttachmentdatadesc() {
		return attachmentdatadesc;
	}

	/**
	 * @param attachmentdatadesc the attachmentdatadesc to set
	 */
	public void setAttachmentdatadesc(String attachmentdatadesc) {
		this.attachmentdatadesc = attachmentdatadesc;
	}


	/**
	 * @return the mimetype
	 */
	public String getMimetype() {
		return mimetype;
	}

	/**
	 * @param mimetype the mimetype to set
	 */
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	/**
	 * @return the documentmastertypedesc
	 */
	public String getDocumentmastertypedesc() {
		return documentmastertypedesc;
	}

	/**
	 * @param documentmastertypedesc the documentmastertypedesc to set
	 */
	public void setDocumentmastertypedesc(String documentmastertypedesc) {
		this.documentmastertypedesc = documentmastertypedesc;
	}


	/**
	 * @return the insertdate
	 */
	public Date getInsertdate() {
		return insertdate==null?null:(Date) insertdate.clone();
	}


	/**
	 * @param insertdate the insertdate to set
	 */
	public void setInsertdate(Date insertdate) {
		this.insertdate = insertdate==null?null:(Date) insertdate.clone();
	}
	public void setInsertdate(String insertdate) {
		// Nothing to do here
	}


	/**
	 * @return the documentmastertypename
	 */
	public String getDocumentmastertypename() {
		return documentmastertypename;
	}


	/**
	 * @param documentmastertypename the documentmastertypename to set
	 */
	public void setDocumentmastertypename(String documentmastertypename) {
		this.documentmastertypename = documentmastertypename;
	}


	/**
	 * @return the idregistry
	 */
	public Number getIdregistry() {
		return idregistry;
	}


	/**
	 * @param idregistry the idregistry to set
	 */
	public void setIdregistry(Number idregistry) {
		this.idregistry = idregistry;
	}


	/**
	 * @return the idtask
	 */
	public Number getIdtask() {
		return idtask;
	}


	/**
	 * @param idtask the idtask to set
	 */
	public void setIdtask(Number idtask) {
		this.idtask = idtask;
	}


	/**
	 * @return the csv
	 */
	public String getCsv() {
		return csv;
	}


	/**
	 * @param csv the csv to set
	 */
	public void setCsv(String csv) {
		this.csv = csv;
	}


	/**
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}


	/**
	 * @param hash the hash to set
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	
	public Number getIdregulation() {
		return idregulation;
	}


	public void setIdregulation(Number idregulation) {
		this.idregulation = idregulation;
	}


	public Number getWebpublishabledoc() {
		return webpublishabledoc;
	}


	public void setWebpublishabledoc(Number webpublishabledoc) {
		this.webpublishabledoc = webpublishabledoc;
	}

	@Override
	public String sendRequest() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public AttachmentModel getModel() {
		return model;
	}
	public void setModel(AttachmentModel model) {
		this.model = model;
	}
	
//	@Override
//	public ListDataModel<?> getInnerListDataModel() {
//		ListDataModel<?> superModel = super.getInnerListDataModel();
//		if (superModel != null && superModel.getWrappedData() != null) {
//			return superModel;
//		}
//		try {
//			// TODO DO SOMETHING queryModel();
//		}
//		catch (Exception e) {
//			log.debug("Error querying AttachmentDataDocumentsBean inner list data model", e);
//		}
//		return super.getInnerListDataModel();
//	}
	
}

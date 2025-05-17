package com.cividas.customforms.webapp.common.model.attachment;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cividas.customforms.webapp.common.model.base.InsertRegistry;
import com.cividas.customforms.webapp.common.model.procedures.ProcedureDetailDynamicDataModel;

@XmlRootElement
@ManagedBean(name="attachmentModel")
@ViewScoped
public class AttachmentModel extends InsertRegistry {

	private static final long serialVersionUID = -5899222873458684038L;

	@XmlElement
	ProcedureDetailDynamicDataModel procedureDetail;
	

	public ProcedureDetailDynamicDataModel getRegistrytypedata() {
		return this.procedureDetail;
	}
	
	public void setRegistrytypedata(ProcedureDetailDynamicDataModel procedureDetail) {
		this.procedureDetail = procedureDetail;
	}
	
}

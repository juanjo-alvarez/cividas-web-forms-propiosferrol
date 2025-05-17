package com.cividas.customforms.webapp.common.model.procedures;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProcedureDocContributionDynamicDataModel implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8984361238256494782L;

	private String				registrycode;
	private String				notificationcode;
	private Date				daterequest;

	public String getRegistrycode() {
		return registrycode;
	}

	public void setRegistrycode(String registrycode) {
		this.registrycode = registrycode;
	}

	public String getNotificationcode() {
		return notificationcode;
	}

	public void setNotificationcode(String notificationcode) {
		this.notificationcode = notificationcode;
	}

	public void setDaterequest(Date daterequest) {
		this.daterequest = daterequest;
	}

	public Date getDaterequest() {
		return daterequest;
	}
}

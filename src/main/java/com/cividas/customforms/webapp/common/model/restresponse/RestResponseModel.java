package com.cividas.customforms.webapp.common.model.restresponse;

public class RestResponseModel {
	
	public enum RestResponseStatus{
        SUCCESS, ERROR;
    }
	
	private RestResponseStatus status = RestResponseStatus.ERROR;
	private Number idRegistry;
	private Number idRegistryReport;

	public RestResponseModel(){
		// Default constructor
	}

	public RestResponseStatus getStatus() {
		return status;
	}

	public void setStatus(RestResponseStatus status) {
		this.status = status;
	}

	public Number getIdRegistry() {
		return idRegistry;
	}

	public void setIdRegistry(Number idRegistry) {
		this.idRegistry = idRegistry;
	}

	public Number getIdRegistryReport() {
		return idRegistryReport;
	}

	public void setIdRegistryReport(Number idRegistryReport) {
		this.idRegistryReport = idRegistryReport;
	}
	
	
	
}

package com.cividas.customforms.webapp.common.model.base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
* [CE-1024] IFrame base para el detalle de los expedientes
* 
* @author juanjo.alvarez
* @since 28/11/2019
*/


@XmlRootElement
public class RegistryTask {

	private static final long serialVersionUID = 1L;
	
	@XmlElement
	private Number idtask;
	
	@XmlElement
	private String tasktypedescweb_lg1;
	
	@XmlElement
	private String tasktypedescweb_lg2;
	
	@XmlElement
	private String taskdescription;
	
	@XmlElement
	private String taskdate;

	public Number getIdtask() {
		return idtask;
	}

	public void setIdtask(Number idtask) {
		this.idtask = idtask;
	}

	public String getTasktypedescweb_lg1() {
		return tasktypedescweb_lg1;
	}

	public void setTasktypedescweb_lg1(String tasktypedescweb_lg1) {
		this.tasktypedescweb_lg1 = tasktypedescweb_lg1;
	}

	public String getTasktypedescweb_lg2() {
		return tasktypedescweb_lg2;
	}

	public void setTasktypedescweb_lg2(String tasktypedescweb_lg2) {
		this.tasktypedescweb_lg2 = tasktypedescweb_lg2;
	}

	public String getTaskdescription() {
		return taskdescription;
	}

	public void setTaskdescription(String taskdescription) {
		this.taskdescription = taskdescription;
	}

	public String getTaskdate() {
		return taskdate;
	}

	public void setTaskdate(String taskdate) {
		this.taskdate = taskdate;
	}
}

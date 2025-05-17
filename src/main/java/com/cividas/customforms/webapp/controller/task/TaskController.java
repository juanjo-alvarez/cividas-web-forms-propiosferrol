package com.cividas.customforms.webapp.controller.task;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cividas.customforms.webapp.controller.base.BaseController;


@ManagedBean(name = "taskBean", eager = true)
@ViewScoped
public class TaskController extends BaseController implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private static final Logger log = LoggerFactory.getLogger(TaskController.class);


	private Number idtask;
	private Number idtasktype;
	private Number idprocedure;
	private Number idregistry;
	private Number idtaskinstance;
	private Number idtaskdata;
	private Date taskdate;
	private Date taskinitdate;
	private Date taskenddate;
	private Number cancelledtask;
	private String tasktypecode;
	private String tasktypedesc;
	private String taskdescription;
	
	private String procedurecode;


	/**
	 * 
	 */
	public TaskController() {
		// NOOP
	}

	public Number getIdtask() {
		return idtask;
	}


	public void setIdtask(Number idtask) {
		this.idtask = idtask;
	}


	public Number getIdtasktype() {
		return idtasktype;
	}


	public void setIdtasktype(Number idtasktype) {
		this.idtasktype = idtasktype;
	}


	public Number getIdprocedure() {
		return idprocedure;
	}


	public void setIdprocedure(Number idprocedure) {
		this.idprocedure = idprocedure;
	}


	public Number getIdregistry() {
		return idregistry;
	}


	public void setIdregistry(Number idregistry) {
		this.idregistry = idregistry;
	}


	public Number getIdtaskinstance() {
		return idtaskinstance;
	}


	public void setIdtaskinstance(Number idtaskinstance) {
		this.idtaskinstance = idtaskinstance;
	}


	public Number getIdtaskdata() {
		return idtaskdata;
	}


	public void setIdtaskdata(Number idtaskdata) {
		this.idtaskdata = idtaskdata;
	}


	public void setTaskdate(Date taskdate) {
		this.taskdate = taskdate==null?null:(Date) taskdate.clone();
	}


	public void setTaskdate(String taskdate) {
		// Nothing to do here
	}


	public Date getTaskinitdate() {
		return taskinitdate==null?null:(Date) taskinitdate.clone();
	}


	public void setTaskinitdate(Date taskinitdate) {
		this.taskinitdate = taskinitdate==null?null:(Date) taskinitdate.clone();
	}


	public void setTaskinitdate(String taskinitdate) {
		// Nothing to do here
	}


	public Date getTaskenddate() {
		return taskenddate==null?null:(Date) taskenddate.clone();
	}


	public void setTaskenddate(Date taskenddate) {
		this.taskenddate = taskenddate==null?null:(Date) taskenddate.clone();
	}


	public void setTaskenddate(String taskenddate) {
		// Nothing to do here
	}


	public Number getCancelledtask() {
		return cancelledtask;
	}


	public void setCancelledtask(Number cancelledtask) {
		this.cancelledtask = cancelledtask;
	}


	public String getTasktypecode() {
		return tasktypecode;
	}


	public void setTasktypecode(String tasktypecode) {
		this.tasktypecode = tasktypecode;
	}


	public String getTasktypedesc() {
		return tasktypedesc;
	}


	public void setTasktypedesc(String tasktypedesc) {
		this.tasktypedesc = tasktypedesc;
	}


	public String getTaskdescription() {
		return taskdescription;
	}


	public void setTaskdescription(String taskdescription) {
		this.taskdescription = taskdescription;
	}
	
	public String getProcedurecode() {
		return procedurecode;
	}


	public void setProcedurecode(String procedurecode) {
		this.procedurecode = procedurecode;
	}

	@Override
	public String sendRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	
}

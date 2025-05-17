package com.cividas.customforms.webapp.controller.solconvxen;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DeclaracionResponsable {
	private String declararesponsablecode;
	private String declararesponsable;
	
	@JsonIgnore
	private boolean checked;

	public String getDeclararesponsablecode() {
		return declararesponsablecode;
	}
	public void setDeclararesponsablecode(String declararesponsablecode) {
		this.declararesponsablecode = declararesponsablecode;
	}
	public String getDeclararesponsable() {
		return declararesponsable;
	}
	public void setDeclararesponsable(String declararesponsable) {
		this.declararesponsable = declararesponsable;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}

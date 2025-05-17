package com.cividas.customforms.webapp.utils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "languageBean", eager = true)
@SessionScoped
public class LanguageBean {

	private String lang = "es";
	
	public String getLang() {
		return lang;
	}
	
	public void setLang(String lang) {
		this.lang = lang;
	}
}

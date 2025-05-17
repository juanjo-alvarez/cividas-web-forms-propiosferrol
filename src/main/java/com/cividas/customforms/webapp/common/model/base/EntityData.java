package com.cividas.customforms.webapp.common.model.base;

import java.util.List;

public class EntityData {

	private String entity;

	private List<?> data;

	public EntityData(String entity, List<?> data) {
		super();
		this.entity = entity;
		this.data = data;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

}
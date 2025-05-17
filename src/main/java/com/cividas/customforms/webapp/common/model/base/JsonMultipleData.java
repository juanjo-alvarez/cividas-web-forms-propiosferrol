package com.cividas.customforms.webapp.common.model.base;

import java.util.List;

public class JsonMultipleData {

	private List<EntityData> entities;

	public JsonMultipleData(List<EntityData> entities) {
		super();
		this.entities = entities;
	}

	public List<EntityData> getEntities() {
		return entities;
	}

	public void setEntities(List<EntityData> entities) {
		this.entities = entities;
	}

}
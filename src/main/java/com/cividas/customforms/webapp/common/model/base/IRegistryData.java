package com.cividas.customforms.webapp.common.model.base;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IRegistryData {

	public String toJSON() throws JsonProcessingException;
}

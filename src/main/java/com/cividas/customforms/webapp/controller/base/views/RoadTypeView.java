package com.cividas.customforms.webapp.controller.base.views;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.cividas.web.common.FieldNamesWeb;

/**
 * The Class RoadTypeView.
 */
public class RoadTypeView implements Serializable {

	private static final long serialVersionUID = -2536784613330318289L;

	/** The code. */
	private String code;
	
	/** The description. */
	private String description;
	
	/**
	 * Instantiates a new roadtype view.
	 */
	public RoadTypeView() {
		super();
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * To hashtable.
	 *
	 * @return the hashtable
	 */
	public Map<String, String> toHashMap() {
		final HashMap<String, String> result = new HashMap<String, String>();
		result.put(FieldNamesWeb.ROADTYPE_CODE, this.code);
		result.put(FieldNamesWeb.ROADTYPE_DESCRIPTION, this.description);
		return result;
	}
	
	/**
	 * Creates the from hashtable.
	 *
	 * @param map the table
	 * @return the roadtype view
	 */
	public static RoadTypeView createFromMap(final Map<?,?> map){
		if (map == null) {
			return null;
		}
		final RoadTypeView result = new RoadTypeView();
		if (map.get(FieldNamesWeb.ROADTYPE_CODE) != null) {
			result.setCode((String) map.get(FieldNamesWeb.ROADTYPE_CODE));
			result.setDescription((String) map.get(FieldNamesWeb.ROADTYPE_DESCRIPTION));
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		RoadTypeView other = (RoadTypeView) obj;
		if (code == null) {
			if (other.code != null){
				return false;
			}
		} else if (!code.equals(other.code)){
			return false;
		}
		return true;
	}
	
}

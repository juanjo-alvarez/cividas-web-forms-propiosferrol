package com.cividas.customforms.webapp.controller.base.views;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.cividas.web.common.FieldNamesWeb;

/**
 * The Class TownView.
 */
public class TownView implements Serializable {

	private static final long serialVersionUID = -2536784613330318289L;

	/** The code. */
	private String code;
	
	/** The description. */
	private String description;
	
	/** The province code. */
	private String provinceCode;
	
	/**
	 * Instantiates a new town view.
	 */
	public TownView() {
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
	 * Gets the province code.
	 *
	 * @return the province code
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 * Sets the province code.
	 *
	 * @param provinceCode the new province code
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	/**
	 * To hashtable.
	 *
	 * @return the hashtable
	 */
	public Map<String, String> toHashMap() {
		final HashMap<String, String> result = new HashMap<String, String>();
		result.put(FieldNamesWeb.TOWN_CODE, this.code);
		result.put(FieldNamesWeb.TOWN_DESCRIPTION, this.description);
		result.put(FieldNamesWeb.TOWN_PROVINCE_CODE, this.provinceCode);
		return result;
	}
	
	/**
	 * Creates the from hashtable.
	 *
	 * @param map the table
	 * @return the town view
	 */
	public static TownView createFromMap(final Map<String,Object> map){
		if (map == null) {
			return null;
		}
		final TownView result = new TownView();
		if (map.get(FieldNamesWeb.TOWN_CODE) != null) {
			result.setCode((String) map.get(FieldNamesWeb.TOWN_CODE));
			result.setDescription((String) map.get(FieldNamesWeb.TOWN_DESCRIPTION));
			result.setProvinceCode((String) map.get(FieldNamesWeb.TOWN_PROVINCE_CODE));
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
		result = prime * result
				+ ((provinceCode == null) ? 0 : provinceCode.hashCode());
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
		TownView other = (TownView) obj;
		if (code == null) {
			if (other.code != null){
				return false;
			}
		} else if (!code.equals(other.code)){
			return false;
		}
		if (provinceCode == null) {
			if (other.provinceCode != null){
				return false;
			}
		} else if (!provinceCode.equals(other.provinceCode)){
			return false;
		}
		return true;
	}
	
}

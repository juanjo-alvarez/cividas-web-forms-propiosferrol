package com.cividas.customforms.webapp.controller.base.views;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.cividas.web.common.FieldNamesWeb;

/**
 * The Class ProvinceView.
 */
public class ProvinceView implements Serializable {

	private static final long serialVersionUID = -4742423154019523327L;
	private static final int PRIME = 31;

	/** The code. */
	private String code;
	
	/** The description. */
	private String description;
	
	/** The country id. */
	private Number countryId;
	
	
	/**
	 * Instantiates a new province view.
	 */
	public ProvinceView() {
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
	 * Gets the country id.
	 *
	 * @return the country id
	 */
	public Number getCountryId() {
		return countryId;
	}

	/**
	 * Sets the country id.
	 *
	 * @param countryId the new country id
	 */
	public void setCountryId(Number countryId) {
		this.countryId = countryId;
	}
	
	/**
	 * To hashtable.
	 *
	 * @return the hashtable
	 */
	public Map<String, Object> toHashMap() {
		final Map<String, Object> result = new HashMap<String, Object>();
		result.put(FieldNamesWeb.PROVINCE_CODE, this.code);
		result.put(FieldNamesWeb.PROVINCE_DESCRIPTION, this.description);
		result.put(FieldNamesWeb.PROVINCE_COUNTRY_ID, this.countryId);
		return result;
	}
	
	/**
	 * Creates the from hashtable.
	 *
	 * @param map the table
	 * @return the province view
	 */
	public static ProvinceView createFromMap(final Map<String,Object> map){
		if (map == null) {
			return null;
		}
		final ProvinceView result = new ProvinceView();

		result.setCode((String) map.get(FieldNamesWeb.PROVINCE_CODE));
		result.setDescription((String) map.get(FieldNamesWeb.PROVINCE_DESCRIPTION));
		
		if (map.get(FieldNamesWeb.PROVINCE_COUNTRY_ID) != null) {
			result.setCountryId(Integer.valueOf(((Number) map.get(FieldNamesWeb.PROVINCE_COUNTRY_ID)).intValue()));
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = 1;
		result = PRIME * result + ((code == null) ? 0 : code.hashCode());
		result = PRIME * result
				+ ((countryId == null) ? 0 : countryId.hashCode());
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
		ProvinceView other = (ProvinceView) obj;
		if (code == null) {
			if (other.code != null){
				return false;
			}
		} else if (!code.equals(other.code)){
			return false;
		}
		if (countryId == null) {
			if (other.countryId != null){
				return false;
			}
		} else if (!countryId.equals(other.countryId)){
			return false;
		}
		return true;
	}
	
}

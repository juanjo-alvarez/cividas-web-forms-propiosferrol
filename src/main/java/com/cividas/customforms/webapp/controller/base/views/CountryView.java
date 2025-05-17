package com.cividas.customforms.webapp.controller.base.views;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.cividas.web.common.FieldNamesWeb;

/**
 * The Class CountryView.
 */
public class CountryView implements Serializable {

	private static final long serialVersionUID = 5042593954345032386L;
	private static final int PRIME = 31;

	/** The id. */
	private Number cid;
	
	/** The description. */
	private String description;
	
	/**
	 * Instantiates a new country view.
	 */
	public CountryView() {
		super();
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Number getCid() {
		return cid;
	}


	/**
	 * Sets the id.
	 *
	 * @param cid the new id
	 */
	public void setCid(Number cid) {
		this.cid = cid;
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
	public Map<String, Object> toHashMap() {
		final HashMap<String, Object> result = new HashMap<String, Object>();
		result.put(FieldNamesWeb.COUNTRY_ID, this.cid);
		result.put(FieldNamesWeb.COUNTRY_DESCRIPTION, this.description);
		return result;
	}
	
	/**
	 * Creates the from hashtable.
	 *
	 * @param map the table
	 * @return the country view
	 */
	public static CountryView createFromMap(final Map<String,Object> map){
		if (map == null) {
			return null;
		}
		final CountryView result = new CountryView();

		if (map.get(FieldNamesWeb.COUNTRY_ID) != null) {
			result.setCid(Integer.valueOf(((Number) map.get(FieldNamesWeb.COUNTRY_ID)).intValue()));
			result.setDescription((String) map.get(FieldNamesWeb.COUNTRY_DESCRIPTION));
		}
		return result;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = PRIME * result + ((cid == null) ? 0 : cid.hashCode());
		return result;
	}

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
		CountryView other = (CountryView) obj;
		if (cid == null) {
			if (other.cid != null){
				return false;
			}
		} else if (!cid.equals(other.cid)){
			return false;
		}
		return true;
	}
	
	
}

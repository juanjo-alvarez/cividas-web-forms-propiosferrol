package com.cividas.customforms.webapp.controller.base.views;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.cividas.web.common.FieldNamesWeb;

/**
 * The Class AddressView.
 */
public class AddressView implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5546140161239370684L;

	/** The id. */
	private Number aid;
	
	/** The province. */
	private ProvinceView province;
	
	/** The town. */
	private TownView town;
	
	/** The town. */
	private String freeTown;
	
	/** The road type. */
	private RoadTypeView roadtype;
	
	/** The country. */
	private CountryView country;
	
	/** The complete address. */
	private String completeAddress;
	
	/** The principal address. */
	private Number principalAddress;
	
	/** The user id. */
	private Number userId;
	
	/** The postal code. */
	private String postalCode;
	
	/** The building number. */
	private String buildingNumber;
	
	/** The building letter. */
	private String letter;
	
	/** The building stair. */
	private String stair;
	
	/** The building floor. */
	private String floor;
	
	/** The building door. */
	private String door;
	
	/** The combo label. */
	private String comboLabel;
	
	/**
	 * Instantiates a new address view.
	 */
	public AddressView() {
		super();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Number getAid() {
		return aid;
	}

	/**
	 * Sets the id.
	 *
	 * @param sid the new id
	 */
	public void setAid(Number sid) {
		this.aid = sid;
	}

	/**
	 * Gets the complete address.
	 *
	 * @return the complete address
	 */
	public String getCompleteAddress() {
		return completeAddress;
	}

	/**
	 * Sets the complete address.
	 *
	 * @param completeAddress the new complete address
	 */
	public void setCompleteAddress(String completeAddress) {
		this.completeAddress = completeAddress;
	}

	/**
	 * Gets the principal address.
	 *
	 * @return the principal address
	 */
	public Number getPrincipalAddress() {
		return principalAddress;
	}

	/**
	 * Sets the principal address.
	 *
	 * @param principalAddress the new principal address
	 */
	public void setPrincipalAddress(Number principalAddress) {
		this.principalAddress = principalAddress;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Number getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(Number userId) {
		this.userId = userId;
	}

	/**
	 * To hashtable.
	 *
	 * @return the hashtable
	 */
	public Map<String, Object> toHashMap() {
		final HashMap<String, Object> result = new HashMap<String, Object>();
		result.put(FieldNamesWeb.USER_ADDRESS_ID, this.aid);
		result.put(FieldNamesWeb.USER_ADDRESS_ADDRESS, this.completeAddress);
		result.put(FieldNamesWeb.USER_ADDRESS_PRINCIPAL_ADDRESS, this.principalAddress);
		result.put(FieldNamesWeb.USER_ADDRESS_USER_ID, this.userId);
		result.put(FieldNamesWeb.USER_ADDRESS_TOWN, this.freeTown);
		
		if (this.getCountry() != null) {
			result.putAll(this.getCountry().toHashMap());
		}
		if (this.getProvince() != null) {
			result.putAll(this.getProvince().toHashMap());
		}
		if (this.getTown() != null) {
			result.putAll(this.getTown().toHashMap());
		}
		if (this.getRoadtype() != null) {
			result.putAll(this.getRoadtype().toHashMap());
		}
		
		return result;
	}
	
	/**
	 * Creates the from hashtable.
	 *
	 * @param map the table
	 * @return the address view
	 */
	public static AddressView createFromMap(final Map<String,Object> map){
		if (map == null) {
			return null;
		}
		final AddressView result = new AddressView();
		
		
		
		if (map.get(FieldNamesWeb.USER_ADDRESS_ID) != null) {
			result.setAid(((Number) map.get(FieldNamesWeb.USER_ADDRESS_ID)).intValue());
			result.setCompleteAddress((String) map.get(FieldNamesWeb.USER_ADDRESS_ADDRESS));
			result.setPrincipalAddress(((Number) map.get(FieldNamesWeb.USER_ADDRESS_PRINCIPAL_ADDRESS)).intValue());
			result.setUserId(((Number) map.get(FieldNamesWeb.USER_ADDRESS_USER_ID)).intValue());
			result.setFreeTown((String) map.get(FieldNamesWeb.USER_ADDRESS_TOWN));
			result.setPostalCode((String) map.get(FieldNamesWeb.USER_ADDRESS_POSTAL_CODE));
			result.setBuildingNumber((String) map.get(FieldNamesWeb.USER_ADDRESS_BUILDING_NUMBER));
			result.setLetter((String) map.get(FieldNamesWeb.USER_ADDRESS_BUILDING_LETTER));
			result.setStair((String) map.get(FieldNamesWeb.USER_ADDRESS_BUILDING_STAIR));
			result.setFloor((String) map.get(FieldNamesWeb.USER_ADDRESS_BUILDING_FLOOR));
			result.setDoor((String) map.get(FieldNamesWeb.USER_ADDRESS_BUILDING_DOOR));
			result.setComboLabel((String) map.get(FieldNamesWeb.USER_ADDRESS_COMPLETE_ADDRESS));

			result.setCountry(CountryView.createFromMap(map));
			result.setProvince(ProvinceView.createFromMap(map));
			result.setTown(TownView.createFromMap(map));
			result.setRoadtype(RoadTypeView.createFromMap(map));
		}
		
		return result;
	}

	/**
	 * Gets the postal code.
	 *
	 * @return the postal code
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * Sets the postal code.
	 *
	 * @param postalCode the new postal code
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * Gets the building number.
	 *
	 * @return the building number
	 */
	public String getBuildingNumber() {
		return buildingNumber;
	}

	/**
	 * Sets the building number.
	 *
	 * @param buildingNumber the new building number
	 */
	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	/**
	 * Gets the building letter.
	 *
	 * @return the building letter
	 */
	public String getLetter() {
		return letter;
	}
	
	/**
	 * Sets the building letter.
	 *
	 * @param buildingLetter the new building letter
	 */
	public void setLetter(String buildingLetter) {
		this.letter = buildingLetter;
	}

	/**
	 * Gets the building stair.
	 *
	 * @return the building stair
	 */
	public String getStair() {
		return stair;
	}

	/**
	 * Sets the building stair.
	 *
	 * @param buildingStair the new building stair
	 */
	public void setStair(String buildingStair) {
		this.stair = buildingStair;
	}

	/**
	 * Gets the building door.
	 *
	 * @return the building door
	 */
	public String getDoor() {
		return door;
	}

	/**
	 * Sets the building door.
	 *
	 * @param buildingDoor the new building door
	 */
	public void setDoor(String buildingDoor) {
		this.door = buildingDoor;
	}

	/**
	 * Gets the building floor.
	 *
	 * @return the building floor
	 */
	public String getFloor() {
		return floor;
	}

	/**
	 * Sets the building floor.
	 *
	 * @param buildingFloor the new building floor
	 */
	public void setFloor(String buildingFloor) {
		this.floor = buildingFloor;
	}

	/**
	 * Gets the combo label.
	 *
	 * @return the combo label
	 */
	public String getComboLabel() {
		return comboLabel;
	}

	/**
	 * Sets the combo label.
	 *
	 * @param comboLabel the new combo label
	 */
	public void setComboLabel(String comboLabel) {
		this.comboLabel = comboLabel;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public CountryView getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(CountryView country) {
		this.country = country;
	}

	/**
	 * Gets the province.
	 *
	 * @return the province
	 */
	public ProvinceView getProvince() {
		return province;
	}

	/**
	 * Sets the province.
	 *
	 * @param province the new province
	 */
	public void setProvince(ProvinceView province) {
		this.province = province;
	}

	/**
	 * Gets the town.
	 *
	 * @return the town
	 */
	public TownView getTown() {
		return town;
	}

	/**
	 * Sets the town.
	 *
	 * @param town the new town
	 */
	public void setTown(TownView town) {
		this.town = town;
	}

	public String getFreeTown() {
		return freeTown;
	}

	public void setFreeTown(String freeTown) {
		this.freeTown = freeTown;
	}

	public RoadTypeView getRoadtype() {
		return roadtype;
	}

	public void setRoadtype(RoadTypeView roadtype) {
		this.roadtype = roadtype;
	}
	
}

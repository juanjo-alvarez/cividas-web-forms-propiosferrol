package com.cividas.customforms.webapp.common.model.base;

import javax.xml.bind.annotation.XmlElement;

public class RegistryInterestedAddress {

	@XmlElement
	private Number idindividualsmasteraddress;
	
	@XmlElement
	private String roadtypecode;
	
	@XmlElement
	private String roadtypedesc;
	
	@XmlElement
	private String address;
	
	@XmlElement
	private String buildingnumber;
	
	@XmlElement
	private String letter;
	
	@XmlElement
	private String stair;
	
	@XmlElement
	private String floor;
	
	@XmlElement
	private String door;
	
	@XmlElement
	private String freetown;
	
	@XmlElement
	private String postalcode;
	
	@XmlElement
	private String towncode;
	
	@XmlElement
	private String towndesc;
	
	@XmlElement
	private String provincecode;
	
	@XmlElement
	private String provincedesc;
	
	@XmlElement
	private Number idcountry;
	
	public Number getIdindividualsmasteraddress() {
		return idindividualsmasteraddress;
	}
	public void setIdindividualsmasteraddress(Number idindividualsmasteraddress) {
		this.idindividualsmasteraddress = idindividualsmasteraddress;
	}
	
	public String getRoadtypecode() {
		return roadtypecode;
	}

	public void setRoadtypecode(String roadtypecode) {
		this.roadtypecode = roadtypecode;
	}

	public String getRoadtypedesc() {
		return roadtypedesc;
	}

	public void setRoadtypedesc(String roadtypedesc) {
		this.roadtypedesc = roadtypedesc;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBuildingnumber() {
		return buildingnumber;
	}

	public void setBuildingnumber(String buildingnumber) {
		this.buildingnumber = buildingnumber;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getStair() {
		return stair;
	}

	public void setStair(String stair) {
		this.stair = stair;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getDoor() {
		return door;
	}

	public void setDoor(String door) {
		this.door = door;
	}

	public String getFreetown() {
		return freetown;
	}

	public void setFreetown(String freetown) {
		this.freetown = freetown;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getTowncode() {
		return towncode;
	}

	public void setTowncode(String towncode) {
		this.towncode = towncode;
	}

	public String getTowndesc() {
		return towndesc;
	}

	public void setTowndesc(String towndesc) {
		this.towndesc = towndesc;
	}

	public String getProvincecode() {
		return provincecode;
	}

	public void setProvincecode(String provincecode) {
		this.provincecode = provincecode;
	}

	public String getProvincedesc() {
		return provincedesc;
	}

	public void setProvincedesc(String provincedesc) {
		this.provincedesc = provincedesc;
	}

	public Number getIdcountry() {
		return idcountry;
	}

	public void setIdcountry(Number idcountry) {
		this.idcountry = idcountry;
	}
}

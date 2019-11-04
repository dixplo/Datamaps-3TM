package fr.tm.datasmap.entity;

import java.util.List;

public class Cplace {

	private String adresse;
	private String lng;
	private String lat;
	private Cplace parent;
	private List<Csite> sites;
	
	
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public Cplace getParent() {
		return parent;
	}
	public void setParent(Cplace parent) {
		this.parent = parent;
	}
	public List<Csite> getSites() {
		return sites;
	}
	public void setSites(List<Csite> sites) {
		this.sites = sites;
	}
	public Cplace(String adresse, String longi, String lati, Cplace parent, List<Csite> sites) {
		super();
		this.adresse = adresse;
		this.lng = longi;
		this.lat = lati;
		this.parent = parent;
		this.sites = sites;
	}
	
	public Cplace() {
		super();
	}
	
	
	
	
}

package fr.tm.datasmap.entity;

import java.util.List;

public class Cplace {

	private String adresse;
	private String longi;
	private String lati;
	private Cplace parent;
	private List<Csite> sites;
	
	
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getLongi() {
		return longi;
	}
	public void setLongi(String longi) {
		this.longi = longi;
	}
	public String getLati() {
		return lati;
	}
	public void setLati(String lati) {
		this.lati = lati;
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
		this.longi = longi;
		this.lati = lati;
		this.parent = parent;
		this.sites = sites;
	}
	
	public Cplace() {
		super();
	}
	
	
	
	
}

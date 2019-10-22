package fr.tm.datasmap.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cuser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String fname;
	private String pwd;
	private Double longi;
	private Double lati;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Cevent> events;
	

	public Cuser(Long id, String name, String fname, String pwd, Double longi, Double lati, List<Cevent> events) {
		super();
		this.id = id;
		this.name = name;
		this.fname = fname;
		this.pwd = pwd;
		this.longi = longi;
		this.lati = lati;
		this.events = events;
	}
	
	public Cuser() {
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Double getLongi() {
		return longi;
	}
	public void setLongi(Double longi) {
		this.longi = longi;
	}
	public Double getLati() {
		return lati;
	}
	public void setLati(Double lati) {
		this.lati = lati;
	}
	public List<Cevent> getEvents() {
		return events;
	}
	public void setEvents(List<Cevent> events) {
		this.events = events;
	}
	
	
}

package fr.tm.datasmap.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Ctype {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "type")
	private List<Cevent> events;

	public Ctype(Long id, String title, List<Cevent> events) {
		super();
		this.id = id;
		this.title = title;
		this.setEvents(events);
	}

	public List<Cevent> getEvents() {
		return events;
	}

	public void setEvents(List<Cevent> events) {
		this.events = events;
	}

	public Ctype() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

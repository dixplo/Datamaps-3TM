package fr.tm.datasmap.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Ctype {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	@OneToMany(mappedBy = "type")
	private List<Cevent> events;

	public Ctype(Long id, String title, List<Cevent> events) {
		super();
		this.id = id;
		this.title = title;
		this.events = events;
	}
	public Ctype(String title) {
		this.title = title;
	}

	public List<Cevent> getEvents() {
		return this.events;
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

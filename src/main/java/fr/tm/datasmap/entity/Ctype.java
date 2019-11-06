package fr.tm.datasmap.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ctype {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;

	public Ctype(Long id, String title) {
		super();
		this.id = id;
		this.title = title;
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

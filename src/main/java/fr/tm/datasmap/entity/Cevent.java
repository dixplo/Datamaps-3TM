package fr.tm.datasmap.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cevent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	private String description;
	private Timestamp start;
	private Timestamp end;
	@JsonIgnore
	@ManyToOne
	private Ctype type;
	private Double lng;
	private Double lat;
	private String address;

	public Cevent(Long id, String title, String description, Timestamp start, Timestamp end, Ctype type, Double lng,
			Double lat, String address) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.start = start;
		this.end = end;
		this.type = type;
		this.lng = lng;
		this.lat = lat;
		this.address = address;
	}

	public Cevent(Long id, String title, String description, Timestamp start, Timestamp end, Ctype type, Double lng,
			Double lat) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.start = start;
		this.end = end;
		this.type = type;
		this.lng = lng;
		this.lat = lat;
	}

	public Cevent(String title, String description, Timestamp start, Timestamp end, Double lng, Double lat) {
		this.title = title;
		this.description = description;
		this.start = start;
		this.end = end;
		this.lng = lng;
		this.lat = lat;
	}

	public Cevent(String title, String description, Timestamp start, Timestamp end, Double lng, Double lat,
			String address) {
		this.title = title;
		this.description = description;
		this.start = start;
		this.end = end;
		this.lng = lng;
		this.lat = lat;
		this.address = address;
	}

	public Cevent(Long id, String title, String description, Timestamp start, Timestamp end, Ctype type) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.start = start;
		this.end = end;
		this.type = type;
	}

	public Cevent(String title, String description, Timestamp start, Timestamp end) {
		this.title = title;
		this.description = description;
		this.start = start;
		this.end = end;
	}

	public Cevent() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getStart() {
		return this.start;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public Timestamp getEnd() {
		return this.end;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}

	public Ctype getType() {
		return this.type;
	}

	public void setType(Ctype type) {
		this.type = type;
	}

	public Double getLng() {
		return this.lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return this.lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}

package fr.tm.datasmap.entity;

import java.util.ArrayList;
import java.util.List;

public class Place {
	private String address;
	private double latitude;
	private double longitude;
	private Place parent;
	private List<Site> sites;
	
	public Place(String address,double latitude,double longitude) {
		this(address,latitude,longitude,null);
	}
	
	public Place(String address,double latitude,double longitude,Place parent) {
		this.address=address;
		this.latitude=latitude;
		this.longitude=longitude;
		this.parent=parent;
		sites=new ArrayList<>();
	}
	
	public void addEvent(String name,String description,String groups,String typeName) {
		
	}
	
	public void addEvent(String name,String description,String groups,Type type) {
		
	}

	public String getAddress() {
		String result=address;
		if(parent!=null) {
			result+=", "+parent.getAddress();
		}
		return result;
	}
	
	public String getAddress(int niveau) {
		String result=address;
		if(parent!=null&& niveau >0) {
			result+=", "+parent.getAddress(--niveau);
		}
		return result;	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Place getParent() {
		return parent;
	}

	public List<Site> getSites() {
		return sites;
	}

}

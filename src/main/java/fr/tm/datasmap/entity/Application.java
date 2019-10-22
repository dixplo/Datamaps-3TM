package fr.tm.datasmap.entity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Application {
	private static List<Place> places;
	private static List<Type> types;
	
	public static List<Type> loadDefaultTypes(){
		return null;
	}
	public static List<Place> loadDefaultPlaces(){
		places=new ArrayList<Place>();
		Place pFrance=new Place("France",46.467196,2.151961);
		Place normandie=new Place("Normandie",48.950632,0.167025,pFrance);
		Place caen=new Place("Caen",49.185402,-0.371057,normandie);
		Place wurz=new Place("Place Würzburg",49.201974,-0.379424,caen);
		Place lycee=new Place("1",49.201771,-0.380820,wurz);
		Collections.addAll(places, pFrance,normandie,caen,wurz,lycee);
		return places;
	}
	
	public List<Place> getPlacesByAddress(String address){
		return null;
	}
	
	public List<Place> getPlacesByGps(double latitude,double longitude){
		return null;
	}
	
	public List<Place> getPlacesByEvtGroups(String groupName){
		return null;
	}
	
	public List<Place> getPlacesByEvtGroups(String groupName,LocalDateTime fromDate){
		return null;
	}
	
	public List<Place> getPlacesByEvtGroups(String groupName,LocalDateTime fromDate,LocalDateTime toDate){
		return null;
	}
}

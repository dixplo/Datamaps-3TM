package fr.tm.datasmap.controller;

import java.util.ArrayList;
import java.util.List;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.tm.datasmap.repository.ICeventRepo;
import fr.tm.datasmap.repository.ICtypeRepo;
import io.github.jeemv.springboot.vuejs.VueJS;
import io.github.jeemv.springboot.vuejs.utilities.Http;
import jdk.internal.net.http.frame.Http2Frame;

@Controller
@RequestMapping("/map/")
public class MapController {

	@Autowired
	private VueJS vue;

	@Autowired
	private ICtypeRepo typeRepo;

	@Autowired
	private ICeventRepo eventRepo;

	@GetMapping("")
	public String map(ModelMap map) {

		// Definition du Profil

		vue.addData("dialogProfil", false);

		// Fin Profil

		vue.addData("dialogStandBy", false);
		vue.addData("loadingRegLog", false);

		vue.addData("dialog", false);
		vue.addData("showPwdLogin", false);
		vue.addData("showPwdRegister", false);

		vue.addData("save", true);
		vue.addData("valid", true);
		vue.addData("validC", true);
		vue.addData("lazy", false);
		vue.addData("tab_connexion", "0");

		vue.addData("allEvent", eventRepo.findAll());
		vue.addData("types", typeRepo.findAll());
		vue.addData("dialogEvent", false);
		vue.addData("validEvent", true);
		vue.addData("lazyEvent", false);
		vue.addData("tab_addVSlngLat", 0);
		vue.addData("searchType", "");
		vue.addDataRaw("event", "{title:'',description:'',start:null,end:null,type:'',lng:null,lat:null,address:''}");
		// regle du formulaire event
		vue.addDataRaw("TitleRules", "[v => !!v || 'Title is empty']");
		vue.addDataRaw("DescriptionRules", "[v => !!v || 'Description is empty']");
		vue.addDataRaw("AddressEventRules", "[v => !!v || 'Address is empty']");
		vue.addDataRaw("TypeRules", "[v => !!v || 'Type is empty']");
		vue.addDataRaw("LngRules", "[v => !!v || 'Longitude is empty']");
		vue.addDataRaw("LatRules", "[v => !!v || 'Latitude is empty']");
		// fin regle
		vue.addMethod("showDialogEvent",
				"if(this.conn){this.dialogEvent=true;}else{alert('You must be logged in to add an event!');this.showLogin();}");
		vue.addMethod("goEvent", "this.map.flyTo([elem.lat, elem.lng], 15);", "elem");
		vue.addMethod("addEvent",
				"let self =this;let $=' ';" + Http.get("'/rest/ctype/'+self.event.type+$",
						"self.event.type=response.data;", "self.event.type=null;") + "if(self.event.type!=null){"
						+ "if(self.tab_addVSlngLat==0){"
						+ Http.get("'/rest/cevent/getlatlng/'+self.event.address+$",
								"if(response.data!=''){self.event.lat=response.data[0];self.event.lng=response.data[1];console.log(self.event);}" + "")
						+ "}else{" + Http.get("'/rest/cevent/getaddress/'+self.event.lng+'/'+self.event.lat+$",
								"console.log(response.data);")
						+ "}" + "}else{alert('erreur avec type')}");//note pour moi reste a faire la verif du get address et les timestamp 

		vue.addData("conn", false);
		vue.addDataRaw("user", "{name:'',fname:'',email:''}");
		// regle du formulaire user
		vue.addDataRaw("NameRules", "[v => !!v || 'Name is empty']");
		vue.addDataRaw("FnameRules", "[v => !!v || 'Firstname is empty']");
		vue.addDataRaw("AddressRules", "[v => !!v || 'Address is empty']");
		vue.addDataRaw("EmailRules",
				"[v => !!v || 'E-mail is empty', v => /.+@.+/. test(v) || 'E-mail must be valid']");
		vue.addDataRaw("PasswordRules", "[v => !!v || 'Password is empty']");
		// fin regle

		vue.addDataRaw("login", "{pwd:'', email:''}");
		vue.addDataRaw("register", "{id:'', name:'', fname: '', pwd:'', email:'',address:'',lng:null,lat:null}");
		vue.addMethod("showLogin", "this.tab_connexion=0;this.dialog=true;");
		vue.addMethod("showRegister", "this.tab_connexion=1;this.dialog=true;");
		vue.addMethod("adduser", "let self=this;let $=' ';self.dialogStandBy=true;self.loadingRegLog=true;" + Http.post(
				"'/rest/cuser/'+self.register.address+$", (Object) "self.register",
				"if(response.data[1]==1){self.dialog=false;"
						+ "self.register={id:'', name:'', fname: '', pwd:'', email:'',address:'',lng:null,lat:null};"
						+ "self.login=response.data[0];self.loadingRegLog=false;"
						+ "self.connexionUser();} else {alert('Address is incorrect!');self.loadingRegLog=false;}"));

		vue.addMethod("connexionUser", "let self=this;self.dialogStandBy=true;" + Http.post("/rest/cuser/one",
				(Object) "self.login",
				"console.log(response.data);if(response.data!=''){console.log(response.data);self.user=response.data;"
						+ "self.dialog=false;this.login={pwd:'', email:''};"
						+ "this.register={id:'', name:'', fname: '', pwd:'', email:'',address:''};self.conn=true;"
						+ "self.map.flyTo([self.user.lat, self.user.lng], 15);"
						+ "var myIcon = L.icon({ iconUrl: '/img/geopoint_home.png', iconSize: [50, 50],iconAnchor: [25, 50],popupAnchor: [-3, -76],});"
						+ "var marker = L.marker([self.user.lat, self.user.lng], { icon: myIcon }).addTo(self.map);self.dialogStandBy=false;"
						+ "}else{alert('Email or password is incorrect!');self.dialogStandBy=false;}",
				"console.log('y a une erreur putain');console.log(response.data);self.dialogStandBy=false;alert('An error as ocurred!');"));
		vue.addMethod("logoutUser", "this.user={name:'',fname:'',email:''};this.conn=false;");

		vue.addMethod("canceldialog",
				"this.login={pwd:'', email:''};this.register={id:'', name:'', fname: '', pwd:'', email:'',address:''};this.dialog=false;");

		vue.addMethod("initmap", "this.map= L.map('map').setView([48.852969, 2.349903], 11);"
				+ "L.tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {" + "attribution: '©3T',"
				+ "minZoom: 1,maxZoom: 20" + "}).addTo(this.map);let self=this;"
				+ Http.get("/rest/cevent/",
						"var eventIcon = L.icon({ iconUrl: '/img/geopoint_events.png', iconSize: [50, 50],iconAnchor: [25, 50],popupAnchor: [-3, -76],});"
								+ "for (var i=0;i<response.data.length;i++) {var marker = L.marker([response.data[i].lat, response.data[i].lng], { icon: eventIcon }).addTo(self.map);}",
						"console.log(response.data);"));

		vue.onMounted("this.initmap();");

		map.put("vue", vue);
		return "map/indexmap";
	}
}
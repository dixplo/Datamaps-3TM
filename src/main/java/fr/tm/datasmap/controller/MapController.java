package fr.tm.datasmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.tm.datasmap.repository.ICeventRepo;
import fr.tm.datasmap.repository.ICtypeRepo;
import io.github.jeemv.springboot.vuejs.VueJS;
import io.github.jeemv.springboot.vuejs.utilities.Http;

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

		// V Dialog ChangeName

		vue.addData("dialogChangeName", false);

		// V Dialog ChangeEmail

		vue.addData("dialogChangeEmail", false);

		// V Dialog ChangePwd

		vue.addData("dialogChangePwd", false);

		// Fin Profil

		vue.addData("navigDrawer", false);

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
		vue.addData("searchType", "");
		vue.addData("searchevent", "");
		vue.addDataRaw("editEvent",
				"{title:'',description:'',start:null,end:null,type:'',lng:null,lat:null,address:''}");
		vue.addDataRaw("objdate", "{startD:null,endD:null,startT:null,endT:null}");
		vue.addDataRaw("currentDate", "new Date().toISOString().substr(0, 10)");
		vue.addDataRaw("currentTime", "new Date().toISOString().substr(11, 8)");
		// regle du formulaire event
		vue.addDataRaw("TitleRules", "[v => !!v || 'Title is empty']");
		vue.addDataRaw("DescriptionRules", "[v => !!v || 'Description is empty']");
		vue.addDataRaw("TypeRules", "[v => !!v || 'Type is empty']");
		vue.addDataRaw("CoordoRules", "[v => !!v || 'The coordinate is empty']");
		// fin regle
		vue.addMethod("showDialogEvent", "this.currentTime=new Date().toISOString().substr(11, 8);"
				+ "if(this.conn){this.dialogEvent=true;}else{alert('You must be logged in to add an event!');this.showLogin();}");
		vue.addMethod("hideDialogEvent",
				"this.dialogEvent=false;this.objdate={startD:null,endD:null,startT:null,endT:null};this.editEvent={title:'',description:'',start:null,end:null,type:'',lng:null,lat:null,address:''}");
		vue.addMethod("goEvent", "this.map.flyTo([elem.lat, elem.lng], 15);", "elem");
		// add event
		vue.addMethod("addEvent", "let self =this;self.dialogStandBy=true;self.eventGetLatLng();");
		vue.addMethod("eventGetLatLng", "let self =this;let $=' ';" + Http.get(
				"'/rest/cevent/getlatlng/'+self.editEvent.address+$",
				"if(response.data!=''){self.editEvent.lat=response.data[0];self.editEvent.lng=response.data[1];console.log(self.editEvent);"
						+ "self.eventGetTime();" + "}else{self.dialogStandBy=false;alert('erreur avec adresse');}"));
		vue.addMethod("eventGetTime", "let self =this;let $=' ';"
				+ "if(self.objdate.startD!=null&&self.objdate.endD!=null&&self.objdate.startT!=null&&self.objdate.endT!=null){"
				+ Http.get(
						"'/rest/cevent/gettimestamp/'+self.objdate.startD+'/'+self.objdate.startT+'/'+self.objdate.endD+'/'+self.objdate.endT+$",
						"self.editEvent.start=response.data[0];self.editEvent.end=response.data[1];console.log(self.editEvent);self.addFinalEvent();")
				+ "}else{self.dialogStandBy=false;alert('The Date and time must be valid!');}");
		vue.addMethod("addFinalEvent", "let self =this;let $=' ';let type =self.editEvent.type;" + Http.post("'/rest/cevent/'+self.editEvent.type+$",
				"self.editEvent",
				"console.log(response.data);"
						+ "var eventIcon = L.icon({ iconUrl: '/img/geopoint_'+type+'.png', iconSize: [50, 50],iconAnchor: [25, 50],popupAnchor: [-3, -76],});"
						+ "var marker = L.marker([response.data.lat, response.data.lng], { icon: eventIcon }).addTo(self.map);self.dialogStandBy=false;self.dialogEvent=false;self.goEvent(response.data);"
						+ "self.allEvent.push(response.data);",
				"console.log(response.data);self.dialogStandBy=false;"));
		// end add event

		vue.addData("conn", false);
		vue.addDataRaw("user", "{name:'',fname:'',email:''}");
		// regle du formulaire user
		vue.addDataRaw("NameRules", "[v => !!v || 'Name is empty']");
		vue.addDataRaw("FnameRules", "[v => !!v || 'Firstname is empty']");
		vue.addDataRaw("AddressRules", "[v => !!v || 'Address is empty']");
		vue.addDataRaw("EmailRules",
				"[v => !!v || 'E-mail is empty', v => /.+@.+\\..+/. test(v) || 'E-mail must be valid']");
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
				+ Http.get("/rest/ctype/", "response.data.forEach(function(element) {"
						+ "var eventIcon = L.icon({ iconUrl: '/img/geopoint_'+element.title+'.png', iconSize: [50, 50],iconAnchor: [25, 50],popupAnchor: [-3, -76],});"
						+ "element.events.forEach(function(event) {"
						+ "var marker = L.marker([event.lat, event.lng], { icon: eventIcon }).addTo(self.map).bindPopup('<b>'+event.title+'</b></br>'+event.description);"
						+ "});" + "});", "console.log(response.data);"));

		vue.onMounted("this.initmap();");

		map.put("vue", vue);
		return "map/indexmap";
	}
}
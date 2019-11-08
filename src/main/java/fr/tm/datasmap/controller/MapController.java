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
		//regle du formulaire event
		vue.addDataRaw("TitleRules", "[v => !!v || 'Title is empty']");
		vue.addDataRaw("DescriptionRules", "[v => !!v || 'Description is empty']");
		vue.addDataRaw("AddressEventRules", "[v => !!v || 'Address is empty']");
		vue.addDataRaw("TypeRules", "[v => !!v || 'Type is empty']");
		vue.addDataRaw("LngRules", "[v => !!v || 'Longitude is empty']");
		vue.addDataRaw("LatRules", "[v => !!v || 'Latitude is empty']");
		//fin regle

		vue.addMethod("","");


		vue.addData("conn", false);
		vue.addDataRaw("user", "{name:'',fname:'',email:''}");
		//regle du formulaire user
		vue.addDataRaw("NameRules", "[v => !!v || 'Name is empty']");
		vue.addDataRaw("FnameRules", "[v => !!v || 'Firstname is empty']");
		vue.addDataRaw("AddressRules", "[v => !!v || 'Address is empty']");
		vue.addDataRaw("EmailRules", "[v => !!v || 'E-mail is empty', v => /.+@.+/. test(v) || 'E-mail must be valid']");
		vue.addDataRaw("PasswordRules", "[v => !!v || 'Password is empty']");
		//fin regle

		vue.addDataRaw("login", "{pwd:'', email:''}");
		vue.addDataRaw("register", "{id:'', name:'', fname: '', pwd:'', email:'',address:'',lng:null,lat:null}");
		vue.addMethod("showLogin", "this.tab_connexion=0;this.dialog=true;");		
		vue.addMethod("showRegister", "this.tab_connexion=1;this.dialog=true;");
		vue.addMethod("adduser", "let self=this;let $=' ';"
				+ Http.post("'/rest/cuser/'+self.register.address+$",(Object) "self.register", "if(response.data[1]==1){self.dialog=false;"
				+ "self.register={id:'', name:'', fname: '', pwd:'', email:'',address:'',lng:null,lat:null};"
				+ "this.conn=true;this.user=response.data[0];"
				+ "self.connexionUser();} else {alert('Address is incorrect!')}"));
		
		vue.addMethod("connexionUser", "let self=this;"+Http.post("/rest/cuser/one",(Object) "self.login", "console.log(response.data);if(response.data!=''){console.log(response.data);self.user=response.data;"
				+ "self.dialog=false;this.login={pwd:'', email:''};"
				+ "this.register={id:'', name:'', fname: '', pwd:'', email:'',address:''};self.conn=true;"
				+ "self.map.flyTo([self.user.lat, self.user.lng], 16);"
				+ "var myIcon = L.icon({ iconUrl: '/img/geopoint_home.png', iconSize: [50, 50],iconAnchor: [25, 50],popupAnchor: [-3, -76],});"
				+ "var marker = L.marker([self.user.lat, self.user.lng], { icon: myIcon }).addTo(self.map);console.log(self.allEvent);"
				+ "}else{alert('Email or password is incorrect!')}", 
				"console.log('y a une erreur putain');console.log(response.data);"));
		vue.addMethod("logoutUser", "this.user={name:'',fname:'',email:''};this.conn=false;");
		
		vue.addMethod("canceldialog", "this.login={pwd:'', email:''};this.register={id:'', name:'', fname: '', pwd:'', email:'',address:''};this.dialog=false;");
		
		vue.addMethod("initmap", "this.map= L.map('map').setView([48.852969, 2.349903], 11);"
		+ "L.tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {"
		+ "attribution: '©3T',"
		+ "minZoom: 1,maxZoom: 20"
		+ "}).addTo(this.map);let self=this;"
		+ Http.get("/rest/cevent/", "var eventIcon = L.icon({ iconUrl: '/img/geopoint_events.png', iconSize: [50, 50],iconAnchor: [25, 50],popupAnchor: [-3, -76],});"
		+ "for (var i=0;i<response.data.length;i++) {var marker = L.marker([response.data[i].lat, response.data[i].lng], { icon: eventIcon }).addTo(self.map);}","console.log(response.data);"));

		vue.onMounted("this.initmap();");

		map.put("vue", vue);
		return "map/indexmap";
	}
}
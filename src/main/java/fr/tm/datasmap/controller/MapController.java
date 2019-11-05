package fr.tm.datasmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.jeemv.springboot.vuejs.VueJS;
import io.github.jeemv.springboot.vuejs.utilities.Http;

@Controller
@RequestMapping("/map/")
public class MapController {
	
	@Autowired
	private VueJS vue;

	@GetMapping("")
	public String map(ModelMap map) {
		vue.addData("dialog", false);
		vue.addData("save", true);
		vue.addData("valid", true);
		vue.addData("validC", true);
		vue.addData("lazy", false);
		vue.addData("tab_connexion", "0");
		
		vue.addData("conn", false);
		vue.addDataRaw("user", "{fname:''}");
		
		vue.addDataRaw("login", "{pwd:'', email:''}");
		vue.addDataRaw("register", "{id:'', name:'', fname: '', pwd:'', email:'',address:'',lng:null,lat:null}");

		//regle du formulaire
		vue.addDataRaw("NameRules", "[v => !!v || 'Name is empty']");
		vue.addDataRaw("FnameRules", "[v => !!v || 'Firstname is empty']");
		vue.addDataRaw("AddressRules", "[v => !!v || 'Address is empty']");
		vue.addDataRaw("EmailRules", "[v => !!v || 'E-mail is empty', v => /.+@.+/. test(v) || 'E-mail must be valid']");
		vue.addDataRaw("PasswordRules", "[v => !!v || 'Password is empty']");
		//fin regle

		vue.addMethod("adduser", "let self=this;"
				+ "var geocoder =  new google.maps.Geocoder();"
				+ "geocoder.geocode( { 'address': self.register.address}, function(results, status) {"
					+ "if (status == google.maps.GeocoderStatus.OK) {"
						+ "self.register.lng = results[0].geometry.location.lng();"
						+ "self.register.lat = results[0].geometry.location.lat();"
						+ Http.post("/rest/cuser/",(Object) "self.register", "self.dialog=false;"
						+ "self.register={id:'', name:'', fname: '', pwd:'', email:'',address:'',lng:null,lat:null};")
					+ "}"
					+ "else{"
						+ "alert('Something got wrong ' + status);console.log(status);"
					+ "};"
				+ "});");
		
		vue.addMethod("connexionUser", "let self=this;"+Http.post("/rest/cuser/one",(Object) "self.login", "console.log(response.data);self.user=response.data;"
				+ "self.dialog=false;self.conn=true;console.log('valeu de conn : '+self.conn);", 
				"console.log('y a une erreur putain');console.log(response.data);"));
		
		vue.addMethod("canceldialog", "this.login={pwd:'', email:''};this.register={id:'', name:'', fname: '', pwd:'', email:'',address:''};this.dialog=false;");
		
		vue.addMethod("showLogin", "this.tab_connexion=0;this.dialog=true;");		
		vue.addMethod("showRegister", "this.tab_connexion=1;this.dialog=true;");
		
		map.put("vue", vue);
		return "map/indexmap";
	}
}
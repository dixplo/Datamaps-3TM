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
		vue.addData("save", true);
		vue.addData("valid", true);
		vue.addData("validC", true);
		vue.addData("lazy", false);
		vue.addData("tab_connexion", "0");

		vue.addData("lng", 2.349903);
		vue.addData("lat", 48.852969);

		
		vue.addData("tabs", typeRepo.findAll());
		vue.addData("tab", eventRepo.findAll());
		
		
		vue.addData("conn", false);
		vue.addDataRaw("user", "{name:'',fname:'',email:''}");


		//regle du formulaire
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
				+ Http.post("'/rest/cuser/'+self.register.address+$",(Object) "self.register", "self.dialog=false;"
				+ "self.register={id:'', name:'', fname: '', pwd:'', email:'',address:'',lng:null,lat:null};"
				+ "this.conn=true;this.user=response.data"));
		
		vue.addMethod("connexionUser", "let self=this;"+Http.post("/rest/cuser/one",(Object) "self.login", "console.log(response.data);self.user=response.data;"
				+ "self.dialog=false;this.login={pwd:'', email:''};"
				+ "this.register={id:'', name:'', fname: '', pwd:'', email:'',address:''};"
				+ "self.conn=true;console.log('valeu de conn : '+self.conn);"
				+ "L.map('map').setView([self.user.lat, self.user.lng], 11);", 
				"console.log('y a une erreur putain');console.log(response.data);"));
		vue.addMethod("logoutUser", "this.user={name:'',fname:'',email:''};this.conn=false;");
		
		vue.addMethod("canceldialog", "this.login={pwd:'', email:''};this.register={id:'', name:'', fname: '', pwd:'', email:'',address:''};this.dialog=false;");

		
		map.put("vue", vue);
		return "map/indexmap";
	}
}
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
		vue.addData("lazy", false);
		vue.addDataRaw("editedItem", "{id:'', name:'', fname: '', pwd:'', email:''}");

		//regle du formulaire
		vue.addDataRaw("EmailRules", "[v => !!v || 'Email is empty']");
		vue.addDataRaw("PasswordRules", "[v => !!v || 'Password is empty']");
		//fin regle

		vue.addMethod("addnounou", "let self=this;"+Http.post("/rest/nounou/", "self.editedItem", "if(self.save){self.items.push(response.data);}self.save=true;"
				+ "self.editedItem={id:'', name:'', fname: '', pwd:'', email:''};"
				+ "self.dialog=false;"));
		vue.addMethod("deletenounou", "let $=' ';let self=this;"+Http.delete("'/rest/nounou/'+nounou.id+$", "self.items.splice(self.items.indexOf(nounou),1)"), "nounou");
		vue.addMethod("editnounou", "this.editedItem=nounou; this.dialog=true; this.save=false;", "nounou");
		vue.addMethod("canceldialog", "this.editedItem={id:'', name:'', fname: '', pwd:'', email:''};this.dialog=false;");
		vue.addMethod("listecontrat", "document.location.href='/contrat/'+item.id", "item");
		
		map.put("vue", vue);
		return "map/indexmap";
	}
}
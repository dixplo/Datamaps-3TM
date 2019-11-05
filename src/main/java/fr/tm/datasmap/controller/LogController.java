package fr.tm.datasmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.jeemv.springboot.vuejs.VueJS;

@Controller
@RequestMapping("/interface/")
public class LogController {

	@Autowired
	private VueJS vue;
	
	@GetMapping("")
	public String connex(ModelMap map) {
		vue.addDataRaw("items", "[{title:'Maps',src:'https://cdn-europe1.lanmedia.fr/var/europe1/storage/images/europe1/technologies/google-maps-se-met-au-globe-en-3d-pour-ses-cartes-3728708/49617644-1-fre-FR/Google-Maps-se-met-au-globe-en-3D-pour-ses-cartes.jpg'},"
				+ "{title:'Statistic',src:'http://mooc.um5.ac.ma/asset-v1:UM5+SD01+2019+type@asset+block@image_de_garde.jpg'}]");
		vue.addMethod("nextpage", "if(i==0){document.location.href='/map/'}"
				+ "else {document.location.href='/stat/'}", "i");
		map.put("vue",vue);
		return "index";
	}
}

package fr.tm.datasmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.jeemv.springboot.vuejs.VueJS;

@Controller
@RequestMapping("")
public class LogController {

	@Autowired
	private VueJS vue;
	
	@GetMapping("")
	public String connex(ModelMap map) {
		vue.addDataRaw("items", "[{title:'Maps',src:'/img/globe.jpg'},"
				+ "{title:'Statistic',src:'/img/stat.png'}]");
		vue.addMethod("nextpage", "if(i==0){document.location.href='/map/'}"
				+ "else {document.location.href='/stat/'}", "i");
		map.put("vue",vue);
		return "index";
	}
}

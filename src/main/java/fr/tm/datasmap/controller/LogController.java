package fr.tm.datasmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.jeemv.springboot.vuejs.VueJS;

@Controller
@RequestMapping("/connec/")
public class LogController {

	@Autowired
	private VueJS vue;
	
	@GetMapping("")
	public String connex(ModelMap map) {
		map.put("vue",vue);
		return "conn/login";
	}
}

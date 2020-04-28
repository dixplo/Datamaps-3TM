package fr.tm.datasmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.jeemv.springboot.vuejs.VueJS;

@Controller
@RequestMapping("/test/")
public class testController {
	
	@Autowired
	private VueJS vue;

	@GetMapping("hello")
	public @ResponseBody String helloW(){
		return "Hello Wolrd !";
	}
	
	@GetMapping("")
	public String maps(ModelMap map){
		map.put("vue", vue);
		return "index";
	}
}

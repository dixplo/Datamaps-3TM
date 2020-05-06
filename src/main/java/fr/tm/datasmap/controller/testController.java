package fr.tm.datasmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.jeemv.springboot.vuejs.VueJS;

@Controller
public class testController {
	@Autowired
	private VueJS vue;

	@ModelAttribute(name = "vue")
	private VueJS getVue() {
		return this.vue;
	}

	@RequestMapping("/test")
	public String test() {
		vue.addData("message", "Hello world");
		return "test";
	}
}

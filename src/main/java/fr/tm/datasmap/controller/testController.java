package fr.tm.datasmap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test/")
public class testController {
	
	@GetMapping("hello")
	public @ResponseBody String helloW(){
		return "Hello Wolrd !";
	}
}

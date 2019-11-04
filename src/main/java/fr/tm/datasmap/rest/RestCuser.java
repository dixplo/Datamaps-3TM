package fr.tm.datasmap.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.tm.datasmap.entity.Cuser;
import fr.tm.datasmap.repository.ICuserRepo;

@RestController
@RequestMapping("/rest/cuser/")
public class RestCuser {

	@Autowired
	private ICuserRepo userRepo;

	@GetMapping("")
	public List<Cuser> getAll(){
		return userRepo.findAll();
	}
	@GetMapping("{id}")
	public Cuser getOne(@PathVariable String id){
		return userRepo.getOne(Long.parseLong(id));
	}
}

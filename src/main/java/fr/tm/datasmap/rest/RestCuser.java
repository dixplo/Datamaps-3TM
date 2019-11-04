package fr.tm.datasmap.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		Optional<Cuser> user =userRepo.findById(Long.parseLong(id));
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

	@PostMapping("one")
	public Cuser getOne(@RequestBody Cuser user) {
		Optional<Cuser> use =userRepo.findOneByEmailAndPwd(user.getEmail(), user.getPwd());
		if (use.isPresent()) {
			return use.get();
		}
		return null;
	}
	@PostMapping("")
	public Cuser addOne(@RequestBody Cuser user) {
		userRepo.saveAndFlush(user);
		return user;
	}
}

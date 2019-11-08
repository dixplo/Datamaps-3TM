package fr.tm.datasmap.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.tm.datasmap.entity.Ctype;
import fr.tm.datasmap.repository.ICtypeRepo;


@RestController
@RequestMapping("/rest/ctype/")
public class RestCtype {

	@Autowired
	private ICtypeRepo typeRepo;

	@GetMapping("")
	public List<Ctype> getAll() {
		return typeRepo.findAll();
	}

	@GetMapping("{title}")
	public Ctype getOne(@PathVariable String title) {
		return typeRepo.findOneByTitle(title);
    }
}

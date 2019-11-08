package fr.tm.datasmap.rest;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.tm.datasmap.entity.Cevent;
import fr.tm.datasmap.repository.ICeventRepo;


@RestController
@RequestMapping("/rest/cevent/")
public class RestCevent {

	@Autowired
	private ICeventRepo eventRepo;

	@GetMapping("")
	public List<Cevent> getAll() {
		return eventRepo.findAll();
	}

	@GetMapping("{id}")
	public Cevent getOne(@PathVariable String id) {
		Optional<Cevent> user = eventRepo.findById(Long.parseLong(id));
		if (user.isPresent()) {
			return user.get();
		}
		return null;
    }
    
    @GetMapping("one")
    public Cevent setOne(){
        double lng =-0.3690815;
		Cevent event = new Cevent("title exemple", "description exemple",
				new Timestamp(Calendar.getInstance().getTimeInMillis()),
				new Timestamp(Calendar.getInstance().getTimeInMillis()), (Double) 49.1828008, lng,
				"Caen");
        eventRepo.saveAndFlush(event);
        return event;
    }

	@PostMapping("one")
	public Cevent getOne(@RequestBody Cevent user) {
		
		return null;
	}
}

package fr.tm.datasmap.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.tm.datasmap.entity.Cevent;
import fr.tm.datasmap.entity.Ctype;
import fr.tm.datasmap.entity.Cuser;
import fr.tm.datasmap.repository.ICeventRepo;
import fr.tm.datasmap.repository.ICuserRepo;

@RestController
@RequestMapping("/rest/cevent/")
public class RestCevent {

	@Autowired
	private ICeventRepo eventRepo;

	@GetMapping("")
	public List<Cevent> getAll(){
		Cevent event = new Cevent("mon titre","ma description",Calendar.getInstance().getTime(),Calendar.getInstance().getTime(), new Ctype("test des truc"));
		eventRepo.save(event);
		return eventRepo.findAll();
	}
	@GetMapping("{id}")
	public Cevent getOne(@PathVariable String id){
		Optional<Cevent> event =eventRepo.findById(Long.parseLong(id));
		if (event.isPresent()) {
			return event.get();
		}
		return null;
	}
	/*@PostMapping("{address}")
	public List<Object> addOne(@RequestBody Cuser user, @PathVariable String address) {
		JOpenCageGeocoder geocoder =new JOpenCageGeocoder("b9798ccd7a31461f8f3396c15ed64160");
		JOpenCageForwardRequest request =new JOpenCageForwardRequest(address);
		request.setMinConfidence(1);
		request.setNoAnnotations(false);
		request.setNoDedupe(true);
		ArrayList<Object> res = new ArrayList<Object>();
		if (!geocoder.forward(request).getResults().isEmpty()) {
		user.setLat(geocoder.forward(request).getResults().get(0).getGeometry().getLat());
		user.setLng(geocoder.forward(request).getResults().get(0).getGeometry().getLng());
		userRepo.saveAndFlush(user);
		res.add(user);
		res.add(1);
		} else {
			user.setLat(48.852969);
			user.setLng(2.349903);
			res.add(user);
			res.add(0);
		}
		return res;
	}*/
}

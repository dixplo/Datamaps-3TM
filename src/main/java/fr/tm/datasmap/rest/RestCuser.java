package fr.tm.datasmap.rest;

import java.util.ArrayList;
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

import fr.tm.datasmap.entity.Cuser;
import fr.tm.datasmap.repository.ICuserRepo;

@RestController
@RequestMapping("/rest/cuser/")
public class RestCuser {

	@Autowired
	private ICuserRepo userRepo;

	@GetMapping("")
	public List<Cuser> getAll() {
		return userRepo.findAll();
	}

	@GetMapping("{id}")
	public Cuser getOne(@PathVariable String id) {
		Optional<Cuser> user = userRepo.findById(Long.parseLong(id));
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

	@PostMapping("one")
	public Cuser getOne(@RequestBody Cuser user) {
		Optional<Cuser> use = userRepo.findOneByEmailAndPwd(user.getEmail(), user.getPwd());
		if (use.isPresent()) {
			return use.get();
		}
		return null;
	}

	@PostMapping("{address}")
	public List<Object> addOne(@RequestBody Cuser user, @PathVariable String address) {
		ArrayList<Object> res = new ArrayList<Object>();
		Boolean deja = false;
		for (Cuser use : userRepo.findAll()) {
			if(user.getEmail() == use.getEmail()){
				deja = true;
			}
		}
		if (!deja) {
			JOpenCageGeocoder geocoder = new JOpenCageGeocoder("b9798ccd7a31461f8f3396c15ed64160");
			JOpenCageForwardRequest request = new JOpenCageForwardRequest(address);
			request.setMinConfidence(1);
			request.setNoAnnotations(false);
			request.setNoDedupe(true);
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
		} else {
			user.setLat(48.852969);
			user.setLng(2.349903);
			res.add(user);
			res.add(0);
		}

		return res;
	}
}

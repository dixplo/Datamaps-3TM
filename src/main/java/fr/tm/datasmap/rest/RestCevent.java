package fr.tm.datasmap.rest;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.tm.datasmap.entity.Cevent;
import fr.tm.datasmap.repository.ICeventRepo;
import fr.tm.datasmap.repository.ICtypeRepo;

@RestController
@RequestMapping("/rest/cevent/")
public class RestCevent {

	@Autowired
	private ICeventRepo eventRepo;

	@Autowired
	private ICtypeRepo typeRepo;

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

	@PostMapping("{type}")
	public Cevent save(@RequestBody Cevent event, @PathVariable String type) {
		event.setType(typeRepo.findOneByTitle(type));
		eventRepo.saveAndFlush(event);
		return event;
	}

	@GetMapping("getaddress/{lng}/{lat}")
	public String getAddress(@PathVariable double lng, @PathVariable double lat) {
		JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("b9798ccd7a31461f8f3396c15ed64160");

		JOpenCageReverseRequest request = new JOpenCageReverseRequest(lat, lng);
		request.setLanguage("fr"); // prioritize results in a specific language using an IETF format language code
		request.setNoDedupe(true); // don't return duplicate results
		request.setLimit(5); // only return the first 5 results (default is 10)
		request.setNoAnnotations(true); // exclude additional info such as calling code, timezone, and currency

		JOpenCageResponse response = jOpenCageGeocoder.reverse(request);

		String formattedAddress = response.getResults().get(0).getFormatted();
		return formattedAddress;
	}

	@GetMapping("getlatlng/{address}")
	public List<Double> getLngLat(@PathVariable String address) {
		JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("b9798ccd7a31461f8f3396c15ed64160");
		JOpenCageForwardRequest request = new JOpenCageForwardRequest(address);

		JOpenCageResponse response = jOpenCageGeocoder.forward(request);
		List<Double> list = new ArrayList<Double>();
		if (!response.getResults().isEmpty()) {
			list.add(response.getResults().get(0).getGeometry().getLat());
			list.add(response.getResults().get(0).getGeometry().getLng());
			return list;
		}
		return null;
	}

	@GetMapping("gettimestamp/{SD}/{ST}/{ED}/{ET}")
	public List<Timestamp> getTimestamp(@PathVariable String SD, @PathVariable String ST, @PathVariable String ED, @PathVariable String ET) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

		List<Timestamp> result = new ArrayList<>();
		result.add(new Timestamp((dateFormat.parse(SD+" "+ST)).getTime()));
		result.add(new Timestamp(dateFormat.parse(ED+" "+ET).getTime()));
		return result;
	}
}

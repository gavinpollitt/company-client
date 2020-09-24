package custq;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

/**
 * 
 * @author regen
 *
 *         curl -i -X POST -d @cc.json -H "Content-Type: application/json" http://localhost:8080/queue/addCompany
 *         curl -i -X GET -H "Content-Type: application/json" http://localhost:8080/queue/getCompany?userName=un 
 *         curl -i -X GET -H "Content-Type: application/json" http://localhost:8080/queue/getCompanies/3?useRName=un
 *		   cc.json--> {"name":"GavWebCo2","description":"The final description", userName="un"}
 */
@RestController
@RequestMapping("/queue")
public class ClientController {

	private Logger logger = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/addCompany", consumes = "application/json", method = RequestMethod.POST)
	public Long add(@RequestBody ClientCompany company) {

		try {
			restTemplate.postForObject(ClientConfig.getUrl() + "/add", company, String.class);
		} catch (Exception qfe) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entry cannot be added to queue at this time:" + qfe.getMessage(),
					qfe);
		}

		logger.debug(company.getUserName() + "-> added the company:" + company.getName() );
		return company.getId();
	}

	/**
	 * API to retrieve the next available company from the queue
	 * @param timeout  A timeout value can be supplied if willing to wait for a entry to appear in the queue
	 * @return the company JSON
	 */
	@RequestMapping(value = "/getCompany", produces = "application/json", method = RequestMethod.GET)
	public ClientCompany get(@RequestParam final String userName) {
		ClientCompany c = null;
		try {
			c = restTemplate.getForObject(ClientConfig.getUrl() + "/get", ClientCompany.class);
		} catch (Exception qfe) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Entries cannot be received from queue at this time", qfe);
		}
		logger.debug(userName + "-> retrieved the company:" + c.getName() + " and id:" + c.getId());
		c.setUserName(userName);
		return c;
	}

	/**
	 * API to retrieve multiple companies from the queue
	 * @param size the maximum number of companies to retrieve
	 * @return the JSON holding the companies retrieved
	 */
	@RequestMapping(value = "/getCompanies/{size}", produces = "application/json", method = RequestMethod.GET)
	public List<ClientCompany> getMany(@PathVariable int size,@RequestParam final String userName) {
		List<ClientCompany> cl = null;

		try {
			cl = restTemplate.getForObject(ClientConfig.getUrl() + "/getGroup/" + size, List.class);
		} catch (Exception qfe) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Entries cannot be received from queue at this time", qfe);
		}

		//cl.stream().forEach(c -> c.setUserName(userName));
		logger.debug(userName + "-> retrieved the companies:" + cl);
		return cl;
	}
}

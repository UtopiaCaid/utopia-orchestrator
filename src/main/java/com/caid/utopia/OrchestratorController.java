package com.caid.utopia;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class OrchestratorController {

	@Autowired
	RestTemplate restTemplate;

	HttpEntity<Object> createHttpEntityWithJwtToken(String jwtToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", jwtToken);
		return new HttpEntity<Object>(headers);
	}

	@GetMapping(path = "/authentication")
	public ResponseEntity<Object> getAuthentication(@RequestHeader(name = "Authorization") String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", token);
		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		return restTemplate.exchange("http://utopiaauthentication/authentication", HttpMethod.GET, entity,
				Object.class);
	}

	@RequestMapping(path = "/authentication", method = RequestMethod.POST)
	public ResponseEntity<Object> postAuthentication(@RequestBody Object jwtRequest) {
		return restTemplate.postForEntity("http://utopiaauthentication/authentication", jwtRequest, Object.class);
	}

	@RequestMapping(path = "/User", method = RequestMethod.POST)
	public ResponseEntity<Object> postUser(@RequestBody Object account) {
		return restTemplate.postForEntity("http://utopiaauthentication/User", account, Object.class);
	}

	@RequestMapping(path = "/Admin", method = RequestMethod.POST)
	public ResponseEntity<Object> postAdmin(@RequestBody Object account) {
		return restTemplate.postForEntity("http://utopiaauthentication/User", account, Object.class);
	}

	@GetMapping(path = "/accounts/{accountNumber}", produces = "application/json")
	public ResponseEntity<Object> getAccount(@PathVariable Integer accountNumber,
			@RequestHeader(name = "Authorization") String token) {
		HttpEntity<Object> entity = createHttpEntityWithJwtToken(token);
		String uri = "http://account-service/accounts/" + accountNumber;
		return restTemplate.exchange(uri, HttpMethod.GET, entity, Object.class);
	}

	@GetMapping(path = "/accounts/users", produces = "application/json")
	public ResponseEntity<Object> getUserAccountsWithFilter(@RequestParam(required = false) Optional<String> nameFilter,
			@RequestHeader(name = "Authorization") String token) {
		HttpEntity<Object> entity = createHttpEntityWithJwtToken(token);
		String queryString = nameFilter.isPresent() ? "?nameFilter=" + nameFilter.get() : "";
		String uri = "http://account-service/accounts/users" + queryString;
		return restTemplate.exchange(uri, HttpMethod.GET, entity, Object.class);
	}

	@RequestMapping(path = "/accounts/{accountNumber}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateAccount(@RequestBody Object account, @PathVariable Integer accountNumber,
			@RequestHeader(name = "Authorization") String token) {
		HttpEntity<Object> entity = createHttpEntityWithJwtToken(token);
		String uri = "http://account-service/accounts" + accountNumber;
		return restTemplate.exchange(uri, HttpMethod.PUT, entity, Object.class);
	}

	@RequestMapping(path = "/accounts/{accountNumber}", method = RequestMethod.DELETE, consumes = "application/json") //
	public ResponseEntity<Object> deactivateAccount(@RequestBody Object account, @PathVariable Integer accountNumber,
			@RequestHeader(name = "Authorization") String jwtToken) {
		HttpEntity<Object> entity = createHttpEntityWithJwtToken(jwtToken);
		String uri = "http://account-service/accounts" + accountNumber;
		return restTemplate.exchange(uri, HttpMethod.DELETE, entity, Object.class);
	}

	@GetMapping(path = "/accounts/{accountNumber}/travelers")
	public ResponseEntity<Object> getTravelersForAccount(@PathVariable Integer accountNumber,
			@RequestHeader(name = "Authorization") String jwtToken) {
		HttpEntity<Object> entity = createHttpEntityWithJwtToken(jwtToken);
		String uri = "http://account-service/accounts" + accountNumber + "/travelers";
		return restTemplate.exchange(uri, HttpMethod.GET, entity, Object.class);
	}

	@RequestMapping(path = "/accounts/{accountNumber}/travelers", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> postNewTraveler(@PathVariable Integer accountNumber, @RequestBody Object traveler,
			@RequestHeader(name = "Authorization") String jwtToken) {
		HttpEntity<Object> entity = createHttpEntityWithJwtToken(jwtToken);
		String uri = "http://account-service/accounts" + accountNumber + "/travelers";
		return restTemplate.exchange(uri, HttpMethod.POST, entity, Object.class);

	}

	@RequestMapping(path = "/accounts/{accountNumber}/travelers/{travelerId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateTravelerForAccount(@PathVariable Integer accountNumber,
			@PathVariable Integer travelerId, @RequestBody Object traveler,
			@RequestHeader(name = "Authorization") String jwtToken) {
		HttpEntity<Object> entity = createHttpEntityWithJwtToken(jwtToken);
		String uri = "http://account-service/accounts" + accountNumber + "/travelers/" + travelerId;
		return restTemplate.exchange(uri, HttpMethod.PUT, entity, Object.class);
	}

	@RequestMapping(path = "/accounts/{accountNumber/travelers/{travelerId}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> deleteTravelerForAccount(@PathVariable Integer accountNumber,
			@PathVariable Integer travelerId, @RequestBody Object traveler,
			@RequestHeader(name = "Authorization") String jwtToken) {
		HttpEntity<Object> entity = createHttpEntityWithJwtToken(jwtToken);
		String uri = "http://account-service/accounts" + accountNumber + "/travelers/" + travelerId;
		return restTemplate.exchange(uri, HttpMethod.DELETE, entity, Object.class);
	}
}

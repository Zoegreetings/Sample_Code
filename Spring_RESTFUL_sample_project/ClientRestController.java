package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ClientRestController {
	
	@Autowired
	ClientRepository clientRepository;

	//retrieve all the clients, using 'http://exampleapi.xyz/medigap/clients' 
	@GetMapping(path = "/medigap/clients")
	public List<Client> retrieveAll() {
		return clientRepository.findAll();
	}
	
	//Extra getting method: retrieve all clients that have a given age, using 'http://exampleapi.xyz/medigap/age/70'
	@GetMapping(path = "/medigap/age/{age}")
	public List<Client> findByAge1(@PathVariable String age){
		return clientRepository.find_By_Age(age);
	}
	
	
	//retrieve all clients that have a given age, using 'http://exampleapi.xyz/medigap?age=70'
	@RequestMapping(value = "/medigap", method = RequestMethod.GET, params = {"age"})
	public List<Client> findByAge(@RequestParam("age") String age) {
		return clientRepository.find_By_Age(age);
	}
	
	//retrieve all clients that have a given zipcode, using 'http://exampleapi.xyz/medigap?zip_code=37024'
	@RequestMapping(value = "/medigap", method = RequestMethod.GET, params = {"zip_code"})
	public List<Client> findByZip(@RequestParam("zip_code") String zip_code) {
		return clientRepository.find_By_Zip(zip_code);
	}
	
	@RequestMapping(value = "/medigap", method = RequestMethod.GET, params = {"gender"})
	public List<Client> findByGender(@RequestParam("gender") String gender) {
		return clientRepository.find_By_Gender(gender);
	}
	
	@RequestMapping(value = "/medigap", method = RequestMethod.GET, params = {"tobacco"})
	public List<Client> findByTobacco (@RequestParam("tobacco") String tobacco) {
		return clientRepository.find_By_Tobacco(tobacco);
	}
	

	//retrieve all clients with given age and gender, using 'http://exampleapi.xyz/medigap?age=70&gender=M'
	@RequestMapping(value = "/medigap", method = RequestMethod.GET, params = {"age", "gender"})
	public List<Client> findByAgeGender(@RequestParam("age") String age, @RequestParam("gender") String gender) {
		return clientRepository.find_By_AgeGender(age, gender);
	}
	
	
	@RequestMapping(value = "/medigap", method = RequestMethod.GET, params = { "zip_code","age"})
	public List<Client> findByAgeZip(@RequestParam("zip_code") String zip_code, @RequestParam("age") String age) {
		return clientRepository.find_By_AgeZip(age, zip_code);
	}
	
	@RequestMapping(value = "/medigap", method = RequestMethod.GET, params = {"age", "tobacco"})
	public List<Client> findByAgeTobacco(@RequestParam("age") String age, @RequestParam("tobacco") String tobacco) {
		return clientRepository.find_By_AgeTobacco(age, tobacco);
	}
	
	@RequestMapping(value = "/medigap", method = RequestMethod.GET, params = {"zip_code", "gender"})
	public List<Client> findByZipGender(@RequestParam("zip_code") String zip_code, @RequestParam("gender") String gender) {
		return clientRepository.find_By_ZipGender(zip_code, gender);
	}
	
	@RequestMapping(value = "/medigap", method = RequestMethod.GET, params = {"zip_code", "tobacco"})
	public List<Client> findByZipTobacco(@RequestParam("zip_code") String zip_code, @RequestParam("tobacco") String tobacco) {
		return clientRepository.find_By_ZipTobacco(zip_code, tobacco);
	}
	
	@RequestMapping(value = "/medigap", method = RequestMethod.GET, params = {"gender", "tobacco"})
	public List<Client> findByGenderTobacco(@RequestParam("gender") String gender, @RequestParam("tobacco") String tobacco) {
		return clientRepository.find_By_GenderTobacco(gender, tobacco);
	}
	
	//retrieve all clients with given age, zip and gender, using 'http://exampleapi.xyz/medigap?age=70&zip_code=12345&gender=M'
	@RequestMapping(value = "/medigap", method = RequestMethod.GET, params = {"age", "zip_code", "gender"})
	public List<Client> findByAgeZipGender(@RequestParam("age") String age, @RequestParam("zip_code") String zip_code, @RequestParam("gender") String gender) {
		return clientRepository.find_By_AgeZipGender(age, zip_code, gender);
	}
	
	
	@RequestMapping(value = "/medigap", method = RequestMethod.GET, params = {"age", "zip_code", "tobacco"})
	public List<Client> findByAgeZipTobacco(@RequestParam("age") String age, @RequestParam("zip_code") String zip_code, @RequestParam("tobacco") String tobacco) {
		return clientRepository.find_By_AgeZipTobacco(age, zip_code, tobacco);
	}
	
	@RequestMapping(value = "/medigap", method = RequestMethod.GET, params = {"age", "gender", "tobacco"})
	public List<Client> findByAgeGenderTobacco(@RequestParam("age") String age, @RequestParam("gender") String gender, @RequestParam("tobacco") String tobacco) {
		return clientRepository.find_By_AgeGenderTobacco(age, gender, tobacco);
	}
	
	@RequestMapping(value = "/medigap", method = RequestMethod.GET, params = {"zip_code", "gender", "tobacco"})
	public List<Client> findByZipGenderTobacco(@RequestParam("zip_code") String zip_code, @RequestParam("gender") String gender, @RequestParam("tobacco") String tobacco) {
		return clientRepository.find_By_ZipGenderTobacco(zip_code, gender, tobacco);
	}
	
	@RequestMapping(value = "/medigap", method = RequestMethod.GET, params = {"age", "zip_code", "gender", "tobacco"})
	public List<Client> findByAgeZipGenderTobacco(@RequestParam("age") String age, @RequestParam("zip_code") String zip_code, @RequestParam("gender") String gender, @RequestParam("tobacco") String tobacco) {
		return clientRepository.find_By_AgeZipGenderTobacco(age, zip_code, gender, tobacco);
	}
	
	
	//another way to get the results
	//retrieve all clients that have a given age, using 'http://exampleapi.xyz/medigap?age=70'
	/*@RequestMapping(value = "/medigap", method = RequestMethod.GET, params = {"age"})
	public List<Client> findByAge(@RequestParam("age") String age) {
		List<Client> clients = clientRepository.findAll();
		List<Client> list = new ArrayList<>();
		for (Client client : clients) {
			if (client.getAge().equals(age)) {
				list.add(client);
			}
		}
		return list;
	}*/
	
	/*@RequestMapping(value = "/medigap", method = RequestMethod.GET, params = {"age", "gender"})
	public List<Client> findByAgeGender(@RequestParam("age") String age, @RequestParam("gender") String gender) {
		List<Client> clients = clientRepository.findAll();
		List<Client> list = new ArrayList<>();
		for (Client client : clients) {
			if (client.getAge().equals(age) && client.getGender().equals(gender)) {
				list.add(client);
			}
		}
		return list;
	}*/

}

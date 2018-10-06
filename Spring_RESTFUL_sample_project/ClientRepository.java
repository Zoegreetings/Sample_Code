package com.example.demo;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public class ClientRepository{
	
	@PersistenceContext
	public EntityManager entityManager;
	
	public List<Client> findAll() {
		TypedQuery<Client> namedQuery = entityManager.createNamedQuery("find_all_clients", Client.class);
		return namedQuery.getResultList();
	}
	
	public Client findById(int id) {
		return entityManager.find(Client.class, id);
	}
	
	public List<Client> find_By_Age(String age) {
		TypedQuery<Client> namedQuery = entityManager.createNamedQuery("find_by_age", Client.class)
				.setParameter("age", age);
		return namedQuery.getResultList();
	}
	
	public List<Client> find_By_Zip(String zip_code) {
		TypedQuery<Client> namedQuery = entityManager.createNamedQuery("find_by_zip", Client.class)
				.setParameter("zip_code", zip_code);
		return namedQuery.getResultList();
	}
	
	public List<Client> find_By_Gender(String gender) {
		TypedQuery<Client> namedQuery = entityManager.createNamedQuery("find_by_gender", Client.class)
				.setParameter("gender", gender);
		return namedQuery.getResultList();
	}
	public List<Client> find_By_Tobacco(String tobacco) {
		TypedQuery<Client> namedQuery = entityManager.createNamedQuery("find_by_tobacco", Client.class)
				.setParameter("tobacco", tobacco);
		return namedQuery.getResultList();
	}
	
	public List<Client> find_By_AgeGender(String age, String gender) {
		TypedQuery<Client> namedQuery = entityManager.createNamedQuery("find_by_age_gender", Client.class)
				.setParameter("age", age)
				.setParameter("gender", gender);
		return namedQuery.getResultList();
	}
	
	public List<Client> find_By_ZipGender(String zip_code, String gender) {
		TypedQuery<Client> namedQuery = entityManager.createNamedQuery("find_by_zip_gender", Client.class)
				.setParameter("zip_code", zip_code)
				.setParameter("gender", gender);
		return namedQuery.getResultList();
	}
	
	public List<Client> find_By_ZipTobacco(String zip_code, String tobacco) {
		TypedQuery<Client> namedQuery = entityManager.createNamedQuery("find_by_zip_tobacco", Client.class)
				.setParameter("zip_code", zip_code)
				.setParameter("tobacco", tobacco);
		return namedQuery.getResultList();
	}
	
	public List<Client> find_By_AgeTobacco(String age, String tobacco) {
		TypedQuery<Client> namedQuery = entityManager.createNamedQuery("find_by_age_tobacco", Client.class)
				.setParameter("age", age)
				.setParameter("tobacco", tobacco);
		return namedQuery.getResultList();
	}
	
	public List<Client> find_By_AgeZip(String age, String zip_code) {
		TypedQuery<Client> namedQuery = entityManager.createNamedQuery("find_by_age_zip", Client.class)
				.setParameter("age", age)
				.setParameter("zip_code", zip_code);
		return namedQuery.getResultList();
	}
	
	public List<Client> find_By_GenderTobacco(String gender, String tobacco) {
		TypedQuery<Client> namedQuery = entityManager.createNamedQuery("find_by_gender_tobacco", Client.class)
				.setParameter("gender", gender)
				.setParameter("tobacco", tobacco);
		return namedQuery.getResultList();
	}
	
	public List<Client> find_By_AgeZipGender (String age, String zip_code, String gender) {
		TypedQuery<Client> namedQuery = entityManager.createNamedQuery("find_by_age_zip_gender", Client.class)
				.setParameter("age", age)
				.setParameter("zip_code", zip_code)
				.setParameter("gender", gender);
		return namedQuery.getResultList();
	}
	
	public List<Client> find_By_AgeZipTobacco(String age, String zip_code, String tobacco) {
		TypedQuery<Client> namedQuery = entityManager.createNamedQuery("find_by_age_zip_tobacco", Client.class)
				.setParameter("age", age)
				.setParameter("zip_code", zip_code)
				.setParameter("tobacco", tobacco);
		return namedQuery.getResultList();
	}
	
	public List<Client> find_By_AgeGenderTobacco(String age, String gender, String tobacco) {
		TypedQuery<Client> namedQuery = entityManager.createNamedQuery("find_by_age_gender_tobacco", Client.class)
				.setParameter("age", age)
				.setParameter("gender", gender)
				.setParameter("tobacco", tobacco);
		return namedQuery.getResultList();
	}
	
	public List<Client> find_By_ZipGenderTobacco (String zip_code, String gender, String tobacco) {
		TypedQuery<Client> namedQuery = entityManager.createNamedQuery("find_by_zip_gender_tobacco", Client.class)
				.setParameter("zip_code", zip_code)
				.setParameter("gender", gender)
				.setParameter("tobacco", tobacco);
		return namedQuery.getResultList();
	}
	
	public List<Client> find_By_AgeZipGenderTobacco (String age, String zip_code, String gender, String tobacco) {
		TypedQuery<Client> namedQuery = entityManager.createNamedQuery("find_by_age_zip_gender_tobacco", Client.class)
				.setParameter("age", age)
				.setParameter("zip_code", zip_code)
				.setParameter("gender", gender)
				.setParameter("tobacco", tobacco);
		return namedQuery.getResultList();
	}
}

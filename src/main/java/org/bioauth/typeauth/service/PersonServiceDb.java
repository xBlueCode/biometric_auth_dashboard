package org.bioauth.typeauth.service;

import org.bioauth.typeauth.domain.Person;
import org.bioauth.typeauth.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceDb implements PersonService {

	private PersonRepository personRepository;

	@Autowired
	public PersonServiceDb(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public Person findPersonByName(String name) {
		return personRepository.findPersonByName(name);
	}

	@Override
	public void save(Person person) {
		personRepository.save(person);
	}

	@Override
	public void update(Person person) {
		personRepository.saveAndFlush(person);
	}

	@Override
	public List<Person> findAll() {
		return personRepository.findAll();
	}

	@Override
	public void delete(Person person) {
		personRepository.delete(person);
	}
}

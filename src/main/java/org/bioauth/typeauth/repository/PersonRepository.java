package org.bioauth.typeauth.repository;

import org.bioauth.typeauth.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	Person findPersonByName(String name);

	List<Person> findAll();
}

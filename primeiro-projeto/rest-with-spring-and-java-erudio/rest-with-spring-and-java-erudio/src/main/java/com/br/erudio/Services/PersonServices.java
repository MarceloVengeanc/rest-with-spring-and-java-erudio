package com.br.erudio.Services;

import com.br.erudio.Model.Person;
import com.br.erudio.Exceptions.ResourceNotFoundException;
import com.br.erudio.Repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<Person> findAll() {
        logger.info("Finding all people");
        return repository.findAll();
    }

    public Person findById(Long id) {

        logger.info("Finding one person!");
        Person person = new Person();
        person.setFirstName("Marcelo");
        person.setLastName("Serrano");
        person.setAddress("Catanduva - São Paulo - Brasil");
        person.setGender("Male");
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    public Person create(Person person) {
        logger.info("Creating one person!");

        return repository.save(person);
    }

    public Person update(Person person) {
        logger.info("Finding one person!");

        var entity = repository.findById(person.getId()
        ).orElseThrow(() -> new ResourceNotFoundException("No reords found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return repository.save(person);
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");

        var entity = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }

}

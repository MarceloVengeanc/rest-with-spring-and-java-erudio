package br.com.erudio.Services;

import java.util.List;

import br.com.erudio.Controllers.PersonController;
import br.com.erudio.Converter.DozerConverter;
import br.com.erudio.Exceptions.RequiredObjectsIsNullException;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;

import br.com.erudio.Data.Model.Person;
import br.com.erudio.Data.VO.V1.PersonVO;
import br.com.erudio.Exceptions.ResourceNotFoundException;
import br.com.erudio.Repositories.PersonRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonServices {

    @Autowired
    PersonRepository repository;

    public List<PersonVO> findAll() {
        var person = DozerConverter.parseListObjects(repository.findAll(), PersonVO.class);
        person
                .stream()
                .forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        return person;
    }

    public PersonVO findById(Long id) {

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        var vo = DozerConverter.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVO create(PersonVO person) {
        if (person == null) throw new RequiredObjectsIsNullException();

        var entity = DozerConverter.parseObject(person, Person.class);
        var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO person) {
        if (person == null) throw new RequiredObjectsIsNullException();

        var entity = repository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    @Transactional
    public PersonVO disablePerson(Long id) {

        repository.disablePerson(id);

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        var vo = DozerConverter.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }

}

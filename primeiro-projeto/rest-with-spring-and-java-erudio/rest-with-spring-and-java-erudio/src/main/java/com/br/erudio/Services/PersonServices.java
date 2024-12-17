package com.br.erudio.Services;

import com.br.erudio.Data.VO.V1.PersonVO;
import com.br.erudio.Data.VO.V2.PersonVOV2;
import com.br.erudio.Exceptions.ResourceNotFoundException;
import com.br.erudio.Mapper.Custom.PersonMapper;
import com.br.erudio.Mapper.DozerMapper;
import com.br.erudio.Model.Person;
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

    @Autowired
    PersonMapper mapper;

    public List<PersonVO> findAll() {
        logger.info("Finding all people");
        return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
    }

    public PersonVO findById(Long id) {

        logger.info("Finding one person!");
        PersonVO person = new PersonVO();
        person.setFirstName("Marcelo");
        person.setLastName("Serrano");
        person.setAddress("Catanduva - São Paulo - Brasil");
        person.setGender("Male");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public PersonVO create(PersonVO person) {
        logger.info("Creating one person!");
        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        return vo;
    }

    public PersonVO update(PersonVO person) {
        logger.info("Finding one person!");

        var entity = repository.findById(person.getId()
        ).orElseThrow(() -> new ResourceNotFoundException("No reords found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");

        var entity = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }

}

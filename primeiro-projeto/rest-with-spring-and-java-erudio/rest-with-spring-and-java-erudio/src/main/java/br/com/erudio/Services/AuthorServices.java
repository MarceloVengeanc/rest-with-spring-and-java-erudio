package br.com.erudio.Services;

import br.com.erudio.Controllers.AuthorController;
import br.com.erudio.Data.Model.Author;
import br.com.erudio.Data.VO.V1.AuthorVO;
import br.com.erudio.Exceptions.RequiredObjectsIsNullException;
import br.com.erudio.Exceptions.ResourceNotFoundException;
import br.com.erudio.Mapper.DozerMapper;
import br.com.erudio.Repositories.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;


import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class AuthorServices {

    @Autowired
    AuthorRepository repository;

    @Autowired
    PagedResourcesAssembler<Author> assembler;

    public List<Author> getAllAuthors() {
        return repository.findAll();
    }

    public Page<Author> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Author create(Author author) {
        if (author == null) throw new RequiredObjectsIsNullException();
        var entity = repository.save(author);
        return entity;
    }

    public void delete(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No authors found for this ID!"));
        repository.delete(entity);
    }

    @Transactional
    public Author disableAuthor(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No authors found for this ID!"));
        repository.save(entity.disableAuthor());

        return entity;
    }

    public AuthorVO update(AuthorVO author) {
        if (author == null) throw new RequiredObjectsIsNullException();

        var entity = repository.findById(author.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setName(author.getName());
        entity.setNationality(author.getNationality());
        entity.setGender(author.getGender());

        var vo = DozerMapper.parseObject(repository.save(entity), AuthorVO.class);
        vo.add(linkTo(methodOn(AuthorController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public Author findById(Long id) {

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return entity;
    }

}

package br.com.erudio.Services;

import br.com.erudio.Controllers.BookController;
import br.com.erudio.Controllers.PersonController;
import br.com.erudio.Converter.DozerConverter;
import br.com.erudio.Data.Model.Book;
import br.com.erudio.Data.VO.V1.BookVO;
import br.com.erudio.Data.VO.V1.PersonVO;
import br.com.erudio.Exceptions.RequiredObjectsIsNullException;
import br.com.erudio.Exceptions.ResourceNotFoundException;
import br.com.erudio.Mapper.DozerMapper;
import br.com.erudio.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServices {

    @Autowired
    BookRepository repository;

    @Autowired
    PagedResourcesAssembler<BookVO> assembler;

    public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {

        var bookPage = repository.findAll(pageable);

        var booksVOPage = bookPage.map(p -> DozerMapper.parseObject(p, BookVO.class));
        booksVOPage.map(
                p -> p.add(
                        linkTo(methodOn(BookController.class)
                                .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(
                methodOn(BookController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(booksVOPage, link);
    }

    public BookVO findById(Integer id) {

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        var vo = DozerConverter.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    public BookVO create(BookVO book) {
        if (book == null) throw new RequiredObjectsIsNullException();

        var entity = DozerConverter.parseObject(book, Book.class);
        var vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book) {
        if (book == null) throw new RequiredObjectsIsNullException();

        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Integer id) {
        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }
}

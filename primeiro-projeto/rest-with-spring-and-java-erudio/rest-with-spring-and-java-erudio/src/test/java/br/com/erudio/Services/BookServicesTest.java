package br.com.erudio.Services;

import br.com.erudio.Converter.Mocks.MockBook;
import br.com.erudio.Converter.Mocks.MockPerson;
import br.com.erudio.Data.Model.Book;
import br.com.erudio.Data.Model.Person;
import br.com.erudio.Data.VO.V1.BookVO;
import br.com.erudio.Data.VO.V1.PersonVO;
import br.com.erudio.Exceptions.RequiredObjectsIsNullException;
import br.com.erudio.Repositories.BookRepository;
import br.com.erudio.Repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

    MockBook input;

    @InjectMocks
    private BookServices services;

    @Mock
    BookRepository repository;

    @BeforeEach
    void setUpMocks() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Book entity = input.mockEntity(1);
        entity.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));

        var result = services.findById(1);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals(new BigDecimal("1"), result.getPrice());
        assertEquals("Title Name Test1", result.getTitle());
        assertNotNull(result.getLaunchDate());
        assertEquals("Initial", result.getAuthor());
    }

    @Test
    void create() {

        Book persisted = input.mockEntity(1);
        persisted.setId(1);

        BookVO vo = input.mockVO(1);
        vo.setKey(1);

        when(repository.save(any(Book.class))).thenReturn(persisted);

        var result = services.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals(new BigDecimal("1"), result.getPrice());
        assertEquals("Title Name Test1", result.getTitle());
        assertNotNull(result.getLaunchDate());
        assertEquals("Initial", result.getAuthor());
    }

    @Test
    void createWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectsIsNullException.class, () -> {
            services.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Book entity = input.mockEntity(1);
        entity.setId(1);

        Book persisted = entity;
        persisted.setId(1);

        BookVO vo = input.mockVO(1);
        vo.setKey(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = services.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals(new BigDecimal("1"), result.getPrice());
        assertEquals("Title Name Test1", result.getTitle());
        assertNotNull(result.getLaunchDate());
        assertEquals("Initial", result.getAuthor());
    }

    @Test
    void updateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectsIsNullException.class, () -> {
            services.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Book entity = input.mockEntity(1);
        entity.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));

        services.delete(1);
    }

    @Test
    void findAll() {
        List<Book> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var books = services.findAll();

        assertNotNull(books);
        assertEquals(14, books.size());

        var bookOne = books.get(1);

        assertNotNull(bookOne);
        assertNotNull(bookOne.getKey());
        assertNotNull(bookOne.getLinks());

        assertTrue(bookOne.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals(new BigDecimal("1"), bookOne.getPrice());
        assertEquals("Title Name Test1", bookOne.getTitle());
        assertNotNull(bookOne.getLaunchDate());
        assertEquals("Initial", bookOne.getAuthor());

        var bookFour = books.get(4);

        assertNotNull(bookFour);
        assertNotNull(bookFour.getKey());
        assertNotNull(bookFour.getLinks());

        assertTrue(bookFour.toString().contains("links: [</api/book/v1/4>;rel=\"self\"]"));
        assertEquals(new BigDecimal("4"), bookFour.getPrice());
        assertEquals("Title Name Test4", bookFour.getTitle());
        assertNotNull(bookFour.getLaunchDate());
        assertEquals("Legend", bookFour.getAuthor());

        var bookSeven = books.get(7);

        assertNotNull(bookSeven);
        assertNotNull(bookSeven.getKey());
        assertNotNull(bookSeven.getLinks());

        assertTrue(bookSeven.toString().contains("links: [</api/book/v1/7>;rel=\"self\"]"));
        assertEquals(new BigDecimal("7"), bookSeven.getPrice());
        assertEquals("Title Name Test7", bookSeven.getTitle());
        assertNotNull(bookSeven.getLaunchDate());
        assertEquals("Initial", bookSeven.getAuthor());
    }
}
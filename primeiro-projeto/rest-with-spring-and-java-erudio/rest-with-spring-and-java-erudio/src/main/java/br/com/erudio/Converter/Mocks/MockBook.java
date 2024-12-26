package br.com.erudio.Converter.Mocks;

import br.com.erudio.Data.Model.Book;
import br.com.erudio.Data.VO.V1.BookVO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBook {
    public Book mockEntity() {
        return mockEntity(0);
    }

    public BookVO mockVO() {
        return mockVO(0);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookVO> mockVOList() {
        List<BookVO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockVO(i));
        }
        return books;
    }

    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(number));
        book.setTitle("Title Name Test" + number);
        book.setAuthor(((number % 2)==0) ? "Legend" : "Initial");
        book.setId(number);
        book.setLaunchDate(new Date());
        return book;
    }

    public BookVO mockVO(Integer number) {
        BookVO book = new BookVO();
        book.setPrice(BigDecimal.valueOf(number));
        book.setTitle("Title Name Test" + number);
        book.setAuthor(((number % 2)==0) ? "Legend" : "Initial");
        book.setKey(number);
        book.setLaunchDate(new Date());
        return book;
    }
}

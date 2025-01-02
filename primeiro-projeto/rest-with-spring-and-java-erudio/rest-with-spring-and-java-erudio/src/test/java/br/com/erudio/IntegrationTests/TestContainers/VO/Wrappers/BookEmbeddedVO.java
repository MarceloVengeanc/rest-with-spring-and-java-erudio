package br.com.erudio.IntegrationTests.TestContainers.VO.Wrappers;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.erudio.IntegrationTests.TestContainers.VO.BookVO;

public class BookEmbeddedVO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JsonProperty("bookVOList")
    private List<BookVO> books;

    public BookEmbeddedVO() {}

    public List<BookVO> getBooks() {
        return books;
    }

    public void setBooks(List<BookVO> books) {
        this.books = books;
    }
}
package br.com.erudio.Repositories;

import br.com.erudio.Data.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}

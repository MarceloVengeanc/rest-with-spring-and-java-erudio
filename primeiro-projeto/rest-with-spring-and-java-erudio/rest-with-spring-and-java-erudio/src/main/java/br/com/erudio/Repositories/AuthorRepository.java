package br.com.erudio.Repositories;

import br.com.erudio.Data.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}

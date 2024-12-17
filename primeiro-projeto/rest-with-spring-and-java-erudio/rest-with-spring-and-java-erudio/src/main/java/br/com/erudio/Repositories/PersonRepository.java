package br.com.erudio.Repositories;

import br.com.erudio.Data.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {}

package com.br.erudio.Repositories;

import com.br.erudio.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {}

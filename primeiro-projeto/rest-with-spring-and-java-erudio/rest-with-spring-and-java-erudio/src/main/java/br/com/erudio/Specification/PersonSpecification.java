package br.com.erudio.Specification;

import br.com.erudio.Data.Model.Person;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PersonSpecification {

    public static Specification<Person> todasPessoasAtivas() {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("enabled"), true)
                ));
    }

    public static Specification<Person> clientesAtivos() {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("author"), false),
                        criteriaBuilder.equal(root.get("enabled"), true)
                ));
    }

    public static Specification<Person> autoresAtivos() {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("author"), true),
                        criteriaBuilder.equal(root.get("enabled"), true)
                ));
    }
}

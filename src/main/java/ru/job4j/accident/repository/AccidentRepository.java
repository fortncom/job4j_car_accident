package ru.job4j.accident.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accident.model.Accident;

import java.util.Optional;

public interface AccidentRepository extends CrudRepository<Accident, Integer> {

    @Override
    @Query("select a from accident a left join fetch a.rules where a.id=?1")
    Optional<Accident> findById(Integer integer);

    @Override
    @Query("select a from accident a left join fetch a.rules")
    Iterable<Accident> findAll();
}
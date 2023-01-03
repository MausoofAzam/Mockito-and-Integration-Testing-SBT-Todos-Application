package com.snort.intelli.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.snort.intelli.app.entites.Todos;

public interface TodosRepository extends CrudRepository<Todos, Long> {
}

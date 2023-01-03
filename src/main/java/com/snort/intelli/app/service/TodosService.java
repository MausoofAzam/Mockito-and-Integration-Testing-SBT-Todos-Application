package com.snort.intelli.app.service;

import com.snort.intelli.app.entites.Todos;

import java.util.List;

public interface TodosService {
    Todos createTask(Todos todos);
    Todos updateTask(Todos todos);
    Todos findOneTodo(Long id);
    String deleteOneTodo(Long id);
    List<Todos> findAll();
}

package com.snort.intelli.app.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snort.intelli.app.entites.Todos;
import com.snort.intelli.app.exceptions.TodosBusinessException;
import com.snort.intelli.app.exceptions.TodosDeleteException;
import com.snort.intelli.app.exceptions.TodosNotFoundException;
import com.snort.intelli.app.exceptions.response.TodosErrorResponse;
import com.snort.intelli.app.repository.TodosRepository;

@Service
public class TodosServiceImpl implements TodosService {

    @Autowired
    private TodosRepository todosRepository;

    @Override
    public Todos createTask(Todos todos) {
        if(todos.getTitle().isEmpty()){
           throw new TodosBusinessException("Cannot create Todos!");
        }else{
            todos.setAssignedDate(new Date());
            Todos newTodos = todosRepository.save(todos);
        }
        return null;
    }

//    @Override
//    public Todos updateTask(Todos todos) {
//
//        if(todosRepository.existsById(todos.getTaskId())){
//            todos.setUpdatedDate(new Date());
//            Todos newTodos = todosRepository.save(todos);
//            return newTodos;
//        }else{
//            throw new TodosBusinessException("Cannot update Todos!");
//        }
//    }

    @Override
    public Todos updateTask(Todos todos) {
        try {
            //todosRepository.findById(todos.getTaskId()).get();
            todos.setUpdatedDate(new Date());
            Todos newTodos = todosRepository.save(todos);
            return newTodos;
        }catch (Exception e){
            throw new TodosBusinessException("Cannot update Todos!");
        }
    }

//    @Override
//    public Todos findOneTodo(Long id) {
//        Optional<Todos> todos = todosRepository.findById(id);
//        if(todos.isPresent()){
//            return todos.get();
//        }else {
//            throw new TodosNotFoundException("Todos not found with id : "+id);
//        }
//    }

    @Override
    public Todos findOneTodo(Long id) {
        try {
            Optional<Todos> todos = todosRepository.findById(id);
            return todos.get();  //
        } catch (Exception e){
            throw new TodosNotFoundException("Todos not found with id : "+id);
        }
    }

    @Override
    public String deleteOneTodo(Long id) {
        Optional<Todos> todos = todosRepository.findById(id);
        if(todos.isPresent()){
            todosRepository.deleteById(id);
            return "Todos with id->"+id+" successfully delete!";
        }else {
            throw new TodosDeleteException("Todos with id->"+id+" failed to delete!",
                    new TodosErrorResponse(LocalDateTime.now(), "Not Found", "404",""));
        }
    }

    @Override
    public List<Todos> findAll() {
        List<Todos> todosList = (List<Todos>) todosRepository.findAll();
        if(todosList.size()>0){
            return todosList;
        }else{
            throw new TodosBusinessException("Zero Todos available!");
        }
    }
}

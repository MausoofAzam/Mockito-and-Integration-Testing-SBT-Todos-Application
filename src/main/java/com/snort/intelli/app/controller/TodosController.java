package com.snort.intelli.app.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.snort.intelli.app.entites.Todos;
import com.snort.intelli.app.service.TodosService;

@RestController
@RequestMapping("/todos")
public class TodosController {

	private Logger log = LoggerFactory.getLogger(TodosController.class);

	//JPA repository
	@Autowired
	private TodosService todosService;

	@PostMapping("/create")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Todos createTask(@RequestBody Todos todos) {
		log.info("TodosController : createTask executed!");
		if(todos.getAssignedDate()==null) todos.setAssignedDate(new Date());
		Todos creTodos1 = todosService.createTask(todos);
		return creTodos1;
	}

	@GetMapping("/")
	public List<Todos> findAll() {
		log.info("TodosController : findAll executed!");
		return (List<Todos>) todosService.findAll();
	}
	
	@GetMapping("/{id}")
	public Todos findOneTodo(@PathVariable Long id) {
		log.info("TodosController : findOneTodo executed!");
		return todosService.findOneTodo(id);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteOneTodo(@PathVariable Long id) {
		log.info("TodosController : deleteOneTodo executed!");
		return todosService.deleteOneTodo(id);
	}
	
	@PutMapping("/update/")
	public Todos updateOneTodo(@RequestBody Todos newTodo) {
		return todosService.updateTask(newTodo);
	}
	
}

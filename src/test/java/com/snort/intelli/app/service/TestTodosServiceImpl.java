package com.snort.intelli.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.snort.intelli.app.entites.Todos;
import com.snort.intelli.app.exceptions.TodosBusinessException;
import com.snort.intelli.app.exceptions.TodosDeleteException;
import com.snort.intelli.app.exceptions.TodosNotFoundException;
import com.snort.intelli.app.repository.TodosRepository;

//Step 1. use @ExtendWith(SpringExtension.class) , I wanted to execute the Class under test with Spring auto configurator features.

@ExtendWith(SpringExtension.class)
public class TestTodosServiceImpl {

	@Mock // Step 2. use @Mock to remove external dependencies
	private TodosRepository todosRepository;

	@InjectMocks // Step 3. Inject mock service object
	private TodosServiceImpl todosServiceImpl;

	// Step 4. setup : create fake request response payload
	// or do any configuration Or get value from properties / yaml file.
	private Todos todos;
	private Optional<Todos> optionalTodos;
	private List<Todos> todosList;

	@BeforeEach
	void setup() {
		todos = new Todos();
		todos.setTaskId(101L);
		todos.setTitle("SQL");
		todos.setDescription("Learn SQL");
		todos.setCompleted(true);
		todos.setAssignedDate(new Date());
		optionalTodos = Optional.of(todos);

		todosList = new ArrayList<>();
		todosList.add(todos);
	}// end of setup(_)

	@Test
	void givenTodosID_WhenFindOneTodo_returnsMatchingTodos() {
		BDDMockito.given(todosRepository.findById(101L)).willReturn(optionalTodos);
		// Mockito.verify(todosRepository, Mockito.times(1)).findById(101L);
		todosServiceImpl.findOneTodo(101L);
		Assertions.assertEquals(optionalTodos.get(), todos);
	}// end of findOneTodo

	@Test
	void givenTodosID_WhenFindOneTodo_returnsEmptyTodos() {
		optionalTodos = Optional.empty();
		BDDMockito.given(todosRepository.findById(Mockito.anyLong())).willReturn(optionalTodos);
		Mockito.verify(todosRepository, Mockito.times(0)).findById(101L);

		assertThrows(TodosNotFoundException.class, () -> {
			todosServiceImpl.findOneTodo(101L);
		});

		Assertions.assertFalse(optionalTodos.isPresent());
	}// end of findOneTodo

	@Test
	void givenTodosID_WhenDeleteTodos_thenReturnsSuccessMessage() {
		// BDDMockito.willDoNothing().given(todosRepository).deleteById(anyLong());
		BDDMockito.given(todosRepository.findById(101L)).willReturn(optionalTodos);
		// Mockito.verify(todosRepository, Mockito.times(1)).deleteById(anyLong());
		todosServiceImpl.deleteOneTodo(101L);
	}// end of deleteOneTodo

	@Test
	void givenTodosID_WhenDeleteTodos_thenReturnsFailedMessage() {
		BDDMockito.willDoNothing().given(todosRepository).deleteById(null);
		Mockito.verify(todosRepository, Mockito.times(0)).deleteById(null);
		assertThrows(TodosDeleteException.class, () -> {
			todosServiceImpl.deleteOneTodo(Mockito.anyLong());
		});
	}// end of deleteOneTodo

	@Test
	void findAll_todos_RetursTodosList() {
		Mockito.when(todosRepository.findAll()).thenReturn(todosList);
		List<Todos> todos = todosServiceImpl.findAll();
		assertEquals(todos.size(), todosList.size());
		assertThat(todos.size()).isGreaterThan(0);
	}//end of findAll()

	@Test
	void findAll_todos_RetursEmptyTodosList() {
		todosList.clear();
		Mockito.when(todosRepository.findAll()).thenReturn(todosList);
		assertThrows(TodosBusinessException.class, () -> {
			todosServiceImpl.findAll();
		});
	}//end of findAll()

	@Test
	void givenTodos_will_be_created() {
		todos.setAssignedDate(new Date());
		BDDMockito.given(todosRepository.save(todos)).willReturn(todos);
		todosServiceImpl.createTask(todos);
		Mockito.verify(todosRepository, Mockito.times(1)).save(todos);
	}//end of createTask

	@Test
	void givenTodos_will_be_created_WhenTitle_Is_empty() {
		todos.setTitle("");
		todos.setAssignedDate(new Date());
		BDDMockito.given(todosRepository.save(todos)).willReturn(todos);
		assertThrows(TodosBusinessException.class, () -> {
			todosServiceImpl.createTask(todos);
		});
	}//end of createTask

	@Test
	void givenTodos_will_be_updated() {
		Todos todos1 = new Todos();
		todos1.setTaskId(1L);
		todos1.setTitle("SQL");
		todos1.setDescription("Learn SQL");
		todos1.setCompleted(true);
		todos1.setAssignedDate(new Date());

		BDDMockito.given(todosRepository.findById(anyLong())).willReturn(optionalTodos.of(todos1));
		BDDMockito.given(todosRepository.save(any(Todos.class))).willReturn(optionalTodos.of(todos1).get());
		// Mockito.verify(todosRepository, Mockito.times(1)).save(todos1);
		todosServiceImpl.updateTask(todos1);
	}//end of updateTask

	@Test
	void givenTodos_wont_be_updated() {
		BDDMockito.given(todosRepository.save(null)).willReturn(null);
		Mockito.verify(todosRepository, Mockito.times(0)).save(todos);
		assertThrows(TodosBusinessException.class, () -> {
			todosServiceImpl.updateTask(null);
		});
	}

}// end of test class

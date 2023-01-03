package com.snort.intelli.app.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.snort.intelli.app.entites.Todos;

@DataJpaTest // Database integration test
@AutoConfigureTestDatabase(replace = Replace.NONE) //don't use default db
@ActiveProfiles("test")
class TodosRepositoryTest {

	@Autowired
	private TodosRepository todosRepository;

	@Order(1)
	@Test
	@Rollback(false) //saving actual todos into db
	void givenTodos_will_be_created() {
		Todos todos = new Todos();
		todos.setTitle("SQL");
		todos.setDescription("Learn SQL");
		todos.setCompleted(true);
		todos.setAssignedDate(new Date());
		todosRepository.save(todos);
		assertNotNull(todos.getTaskId());
	}

	@Test
	void givenTodosID_WhenFindOneTodo_returnsMatchingTodos() {
		Todos todos = todosRepository.findById(1L).get();
		assertNotNull(todos);
	}

	@Test
	@Rollback(false) // update actual todos into db
	void givenTodos_will_be_updated() {
		Todos todos = new Todos();
		todos.setTaskId(1L);
		todos.setTitle("Oracle SQL Developer");
		todos.setDescription("Learn SQL");
		todos.setCompleted(true);
		todos.setUpdatedDate(new Date());
		todosRepository.save(todos);
		assertNotNull(todos.getUpdatedDate());
	}

//	@Test
//	@Rollback(false)
//	void givenTodosID_WhenDeleteOneTodo_returnsNothing() {
//		todosRepository.deleteById(2L);
//		Optional<Todos> toOptional = todosRepository.findById(2L);
//		assertTrue(toOptional.isEmpty());
//	}

}

package com.snort.intelli.app.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snort.intelli.app.entites.Todos;
import com.snort.intelli.app.service.TodosService;

@WebMvcTest(TodosController.class)
class TodosControllerTest {

	@MockBean
	private TodosService todosService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private Todos todos;

	private List<Todos> todosList;

	@BeforeEach
	void setup() {
		todos = new Todos();
		todos.setTaskId(101L);
		todos.setTitle("SQL");
		todos.setDescription("Learn SQL");
		todos.setCompleted(true);
		todos.setAssignedDate(new Date());

		todosList = new ArrayList<>();
		todosList.add(todos);
		todosList.add(new Todos());
	}

	@Test
	void givenTodos_will_be_created() throws Exception {

		Todos requestPayload = todos;

		assertNotNull(requestPayload);

		BDDMockito.given(todosService.createTask(any(Todos.class))).willReturn(todos);
		ResultActions actions = mockMvc.perform(post("/todos/create").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestPayload)));
		actions.andExpect(status().isCreated());
		actions.andDo(print());
		// Match result / response
		actions.andExpect(jsonPath("$.title", is(todos.getTitle())))
				.andExpect(jsonPath("description", is(todos.getDescription())));
	}// end of createTask

	@Test
	void givenTodos_will_be_created_when_Assign_date_Null() throws Exception {

		todos.setAssignedDate(null);
		Todos requestPayload = todos;

		assertNotNull(requestPayload);

		BDDMockito.given(todosService.createTask(any(Todos.class))).willReturn(todos);
		ResultActions actions = mockMvc.perform(post("/todos/create").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestPayload)));
		actions.andExpect(status().isCreated());
		actions.andDo(print());
		// Match result / response
		actions.andExpect(jsonPath("$.title", is(todos.getTitle())))
				.andExpect(jsonPath("description", is(todos.getDescription())));
	}// end of createTask

	@Test
	void givenTodos_will_be_updated() throws Exception {

		todos.setDescription("Learn Oracle Database 12c");

		assertNotNull(todos);

		BDDMockito.given(todosService.createTask(any(Todos.class))).willReturn(todos);
		ResultActions actions = mockMvc.perform(put("/todos/update/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(todos)));
		actions.andExpect(status().isOk());

		actions.andDo(print());
		// Match result / response
//		actions.andExpect(jsonPath("$.title", is(todos.getTitle())))
//				.andExpect(jsonPath("description", is(todos.getDescription())));
	}// end of createTask

	@Test
	void findAll_todos_RetursTodosList() throws Exception {
		BDDMockito.given(todosService.findAll()).willReturn(todosList);
		ResultActions actions = mockMvc.perform(get("/todos/"));
		actions.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", is(todosList.size())));
	}// end of findAll()

	@Test
	void findOneTodos_todos_RetursTodos() throws Exception {
		BDDMockito.given(todosService.findOneTodo(101L)).willReturn(todos);
		ResultActions actions = mockMvc.perform(get("/todos/" + (101)));
		actions.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.title", is(todos.getTitle())))
				.andExpect(jsonPath("description", is(todos.getDescription())));
	}// end of findOneTodo()

	@Test
	void givenTodosID_WhenDeleteTodos_thenReturnsSuccessMessage() throws Exception {
		BDDMockito.given(todosService.deleteOneTodo(101L))
				.willReturn("Todos with id->" + todos.getTaskId() + " successfully delete!");
		ResultActions actions = mockMvc.perform(delete("/todos/delete/" + (101L)));
		actions.andExpect(status().isOk());
	}// end of deleteOneTodo

}// end of class

package com.snort.intelli.app.exceptions.advice;

import com.snort.intelli.app.exceptions.TodosBusinessException;
import com.snort.intelli.app.exceptions.TodosNotFoundException;
import com.snort.intelli.app.exceptions.TodosDeleteException;
import com.snort.intelli.app.exceptions.response.TodosErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class TodosGlobalExceptionHandler {

    @ExceptionHandler(value = TodosBusinessException.class)
    public ResponseEntity<TodosErrorResponse> todosErrorHandler(TodosBusinessException e){
    return new ResponseEntity<>(new TodosErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.getReasonPhrase(),HttpStatus.BAD_REQUEST.toString(),e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = TodosNotFoundException.class)
    public ResponseEntity<TodosErrorResponse> todosErrorHandler(TodosNotFoundException e) {
        return new
                ResponseEntity<>(new
                TodosErrorResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.toString(),e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = TodosDeleteException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public TodosDeleteException todosErrorHandler(TodosDeleteException e){
        return e;
    }

}

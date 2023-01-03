package com.snort.intelli.app.exceptions;

import com.snort.intelli.app.exceptions.response.TodosErrorResponse;

public class TodosDeleteException extends RuntimeException {

    private TodosErrorResponse errorResponse;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public TodosDeleteException(String message, TodosErrorResponse errorResponse) {
        super(message);
        errorResponse.setReason(message);
        this.errorResponse = errorResponse;
    }

    public TodosErrorResponse getErrorResponse() {
        return errorResponse;
    }
}

package com.myproject.task_management.exception;

public class BadRequestException extends Throwable {
    public BadRequestException(String message) {
        super(message);
    }
}

package com.employees.service.exceptions.handler;

import com.employees.service.exceptions.InvalidInputException;
import com.employees.service.exceptions.NotFoundException;
import com.employees.service.exceptions.ServerException;
import com.employees.service.exceptions.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class EmployeesExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeesExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody ExceptionResponse handleNotFoundException(NotFoundException ex, WebRequest request) {
        return createExceptionResponse(HttpStatus.NOT_FOUND, request, ex);
    }

    @ExceptionHandler(InvalidInputException.class)
    public @ResponseBody ExceptionResponse handleInvalidInputException(InvalidInputException ex, WebRequest request) {
        return createExceptionResponse(HttpStatus.UNPROCESSABLE_ENTITY, request, ex);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public @ResponseBody ExceptionResponse handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        return createExceptionResponse(HttpStatus.UNAUTHORIZED, request, ex);
    }

    @ExceptionHandler(ServerException.class)
    public @ResponseBody ExceptionResponse handleServerException(ServerException ex, WebRequest request) {
        return createExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, request, ex);
    }

    private ExceptionResponse createExceptionResponse(HttpStatus httpStatus, WebRequest request, Exception ex) {
        final String path = request.getDescription(false);
        final String message = ex.getMessage();

        LOG.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
        return new ExceptionResponse(httpStatus, path, message);
    }
}
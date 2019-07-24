package com.anz.wholesale.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * A ControllerAdvice class which provides exception handling for the controller
 * @author sumit
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	/**
     * The Logger for this Class.
     */
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
    
    
    /**
     * Handles all Exceptions not addressed by more specific <code>@ExceptionHandler</code> methods. Creates a response
     * with the ExceptionDetail in the response body as JSON and a HTTP status code of 500, internal server error.
     * 
     * @param ex An Exception instance.
     * @return A ResponseEntity containing a the ExceptionDetail in the response body and a HTTP status code 500.
     */
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleAccountNotFoundException(final Exception ex, final WebRequest request) {
        logger.info("> handleAccountNotFoundException");
        logger.error("- Exception: ", ex);
        final ExceptionDetail detail = new ExceptionDetailBuilder().exception(ex)
                .httpStatus(HttpStatus.NOT_FOUND).webRequest(request).build();
        logger.info("< handleAccountNotFoundException");
        return handleExceptionInternal(ex, detail, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
    /**
     * Handles all Exceptions not addressed by more specific <code>@ExceptionHandler</code> methods. Creates a response
     * with the ExceptionDetail in the response body as JSON and a HTTP status code of 500, internal server error.
     * 
     * @param ex An Exception instance.
     * @return A ResponseEntity containing a the ExceptionDetail in the response body and a HTTP status code 500.
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbiddenException(final Exception ex, final WebRequest request) {
        logger.info("> handleForbiddenException");
        logger.error("- Exception: ", ex);
        final ExceptionDetail detail = new ExceptionDetailBuilder().exception(ex)
                .httpStatus(HttpStatus.FORBIDDEN).webRequest(request).build();
        logger.info("< handleForbiddenException");
        return handleExceptionInternal(ex, detail, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }
    
    /**
     * Handles all Exceptions not addressed by more specific <code>@ExceptionHandler</code> methods. Creates a response
     * with the ExceptionDetail in the response body as JSON and a HTTP status code of 500, internal server error.
     * 
     * @param ex An Exception instance.
     * @return A ResponseEntity containing a the ExceptionDetail in the response body and a HTTP status code 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(final Exception ex, final WebRequest request) {
        logger.info("> handleException");
        logger.error("- Exception: ", ex);
        final ExceptionDetail detail = new ExceptionDetailBuilder().exception(ex)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).webRequest(request).build();
        logger.info("< handleException");
        return handleExceptionInternal(ex, detail, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
	
}

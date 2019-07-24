package com.anz.wholesale.exception;

import java.time.Instant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The ExceptionDetail class models information about a web service request which results in an exception.
 * 
 * @author sumit
 *
 */

@Getter @Setter @NoArgsConstructor
public class ExceptionDetail {

	/**
     * The time the exception occurred.
     */
    private Instant timestamp;
    /**
     * The HTTP method (e.g. GET, POST, etc.)
     */
    private String method = "";
    /**
     * The web service context path.
     */
    private String path = "";
    /**
     * The HTTP status code of the response.
     */
    private int status;
    /**
     * The text description of the HTTP status code of the response.
     */
    private String statusText = "";
    /**
     * The fully qualified Class name of the Exception.
     */
    private String exceptionClass = "";
    /**
     * The value of the Exception message attribute.
     */
    private String exceptionMessage = "";
	
}

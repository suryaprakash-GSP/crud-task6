package com.posidex.crud_task6.handler;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.posidex.crud_task6.exception.handler.ResourceNotFoundException;
import com.posidex.crud_task6.utility.ResponseAsJson;

@ControllerAdvice
public class ExceptiinHandlerService extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<Object> realTimeUcicException(
			ResourceNotFoundException exception, HttpServletResponse hResponse) {
		ResponseAsJson<Object, Integer> response = new ResponseAsJson<>();
		response.setMessage("Exception occured");
		response.setData(exception.getMessage());
		response.setStatus(hResponse.getStatus());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}

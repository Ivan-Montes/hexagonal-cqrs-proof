package dev.ime.application.exception;

import java.util.Map;
import java.util.UUID;

import dev.ime.application.config.ApplicationConstant;

public class ResourceNotFoundException extends BasicException{	

	private static final long serialVersionUID = 5436549421040910165L;

	public ResourceNotFoundException(Map<String, String> errors) {
		super(
				UUID.randomUUID(),
				ApplicationConstant.EX_RESOURCE_NOT_FOUND,
				ApplicationConstant.EX_RESOURCE_NOT_FOUND_DESC,
				errors
				);
	}

}

package dev.ime.application.exception;

import java.util.Map;
import java.util.UUID;


public class BasicException extends RuntimeException{

	private static final long serialVersionUID = -1312651884253971091L;

	private final UUID identifier;
	private final String name;
	private final String description;
	
	private final Map<String, String> errors;

	public BasicException(UUID identifier, String name, String description, Map<String, String> errors) {
		super();
		this.identifier = identifier;
		this.name = name;
		this.description = description;
		this.errors = errors;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UUID getIdentifier() {
		return identifier;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	@Override
	public String toString() {
		return "Exception [identifier=" + identifier + ", name=" + name + ", description=" + description
				+ ", errors=" + errors + "]";
	}
	
}

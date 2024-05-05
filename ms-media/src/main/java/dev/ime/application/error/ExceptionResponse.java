package dev.ime.application.error;

import java.util.Map;
import java.util.UUID;

public record ExceptionResponse(UUID identifier, String name, String description, Map<String,String>error) {

}

package dev.ime.domain.port.inbound;

import org.springframework.http.ResponseEntity;

public interface GenericCommandControllerPort<T> {

	ResponseEntity<T>create(T dto);
	ResponseEntity<T>update(Long id, T dto);
	ResponseEntity<Boolean>deleteById(Long id);
}

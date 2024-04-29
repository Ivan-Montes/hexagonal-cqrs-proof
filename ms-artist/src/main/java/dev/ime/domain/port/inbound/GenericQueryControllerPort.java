package dev.ime.domain.port.inbound;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface GenericQueryControllerPort<T> {

	ResponseEntity<List<T>> getAll();
	ResponseEntity<T>getById(Long id);
}

package dev.ime.infrastructure.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ime.config.MediaMapper;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.inbound.GenericQueryControllerPort;
import dev.ime.domain.port.inbound.MediaQueryServicePort;
import dev.ime.infrastructure.dto.MediaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/medias")
@Tag(name = "Media", description="Media Operations")
public class MediaQueryController implements GenericQueryControllerPort<MediaDto>{

	private final MediaQueryServicePort mediaQueryServicePort;
	private final MediaMapper mediaMapper;	
	
	public MediaQueryController(MediaQueryServicePort mediaQueryServicePort, MediaMapper mediaMapper) {
		super();
		this.mediaQueryServicePort = mediaQueryServicePort;
		this.mediaMapper = mediaMapper;
	}

	@GetMapping
	@Override
	@Operation(summary="Get a List of all Media", description="Get a List of all Media, @return an object Response with a List of DTO's")
	public ResponseEntity<List<MediaDto>> getAll() {
		
		List<Media> list = mediaQueryServicePort.getAll();
		
		return ResponseEntity.ok(list.isEmpty()? Collections.emptyList():mediaMapper.fromListDomainToListDto(list));
	}

	@GetMapping("/{id}")
	@Override
	@Operation(summary="Get a Media according to an Id", description="Get a Media according to an Id, @return an object Response with the entity required in a DTO")
	public ResponseEntity<MediaDto> getById(@PathVariable Long id) {
		
		return ResponseEntity.ok(mediaQueryServicePort.getById(id).map(mediaMapper::fromDomainToDto).orElse(new MediaDto()));
	}

}

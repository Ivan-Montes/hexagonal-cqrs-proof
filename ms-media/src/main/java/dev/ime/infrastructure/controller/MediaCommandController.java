package dev.ime.infrastructure.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ime.config.MediaMapper;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.inbound.GenericCommandControllerPort;
import dev.ime.domain.port.inbound.MediaCommandServicePort;
import dev.ime.infrastructure.dto.MediaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medias")
@Tag(name = "Media", description="Media Operations")
public class MediaCommandController implements GenericCommandControllerPort<MediaDto>{

	private final MediaCommandServicePort mediaCommandServicePort;
	private final MediaMapper mediaMapper;
	
	public MediaCommandController(MediaCommandServicePort mediaCommandServicePort, MediaMapper mediaMapper) {
		super();
		this.mediaCommandServicePort = mediaCommandServicePort;
		this.mediaMapper = mediaMapper;
	}

	@PostMapping
	@Override
	@Operation(summary="Create a new Media", description="Create a new Media, @return an object Response with the entity in a DTO")
	public ResponseEntity<MediaDto> create(@Valid @RequestBody MediaDto dto) {
		
		Optional<Media> optMedia = mediaCommandServicePort.create(mediaMapper.fromDtoToDomain(dto));
		
		return optMedia.isPresent()? new ResponseEntity<>(mediaMapper.fromDomainToDto(optMedia.get()), HttpStatus.CREATED)
				:ResponseEntity.ok(new MediaDto());
	}

	@PutMapping("/{id}")
	@Override
	@Operation(summary="Update fields in a Media", description="Update fields in a Media, @return an object Response with the entity modified in a DTO")
	public ResponseEntity<MediaDto> update(@PathVariable Long id, @Valid @RequestBody MediaDto dto) {
		
		Optional<Media> optMedia = mediaCommandServicePort.update(id, mediaMapper.fromDtoToDomain(dto));

		return ResponseEntity.ok(optMedia.map(mediaMapper::fromDomainToDto).orElse(new MediaDto()));
	}

	@DeleteMapping("/{id}")
	@Override
	@Operation(summary="Delete a Media by its Id", description="Delete a Media by its Id, @return an object Response with a message")
	public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
		
		return Boolean.TRUE.equals(mediaCommandServicePort.deleteById(id)) ? new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK)
				:new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
	}

}

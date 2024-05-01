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

import dev.ime.config.ArtistMapper;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.inbound.ArtistCommandServicePort;
import dev.ime.domain.port.inbound.GenericCommandControllerPort;
import dev.ime.infrastructure.dto.ArtistDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/artists")
@Tag(name = "Artist", description="Artist Operations")
public class ArtistCommandController  implements GenericCommandControllerPort<ArtistDto>{

	private final ArtistCommandServicePort artistCommandService;
	private final ArtistMapper artistMapper;	

	public ArtistCommandController(ArtistCommandServicePort artistCommandService, ArtistMapper artistMapper) {
		super();
		this.artistCommandService = artistCommandService;
		this.artistMapper = artistMapper;
	}

	@PostMapping
	@Override
	@Operation(summary="Create a new Artist", description="Create a new Artist, @return an object Response with the entity in a DTO")
	public ResponseEntity<ArtistDto> create(@Valid @RequestBody ArtistDto dto) {
		
		Optional<Artist> optArtist = artistCommandService.create(artistMapper.fromDtoToDomain(dto));
		
		return optArtist.isPresent()? new ResponseEntity<>(artistMapper.fromDomainToDto(optArtist.get()),HttpStatus.CREATED)
				:new ResponseEntity<>(new ArtistDto(), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@Override
	@Operation(summary="Update fields in a Artist", description="Update fields in a Artist, @return an object Response with the entity modified in a DTO")
	public ResponseEntity<ArtistDto> update(@PathVariable Long id, @Valid @RequestBody ArtistDto dto) {
		
		Optional<Artist> optArtist = artistCommandService.update(id, artistMapper.fromDtoToDomain(dto));

		return ResponseEntity.ok(optArtist.map(artistMapper::fromDomainToDto).orElse(new ArtistDto()));		
	}

	@DeleteMapping("/{id}")
	@Override
	@Operation(summary="Delete a Artist by its Id", description="Delete a Artist by its Id, @return an object Response with a message")
	public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
	
		return Boolean.TRUE.equals(artistCommandService.deleteById(id)) ? new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK)
				:new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
	}

}

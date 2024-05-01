package dev.ime.infrastructure.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ime.config.ArtistMapper;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.inbound.ArtistQueryServicePort;
import dev.ime.domain.port.inbound.GenericQueryControllerPort;
import dev.ime.infrastructure.dto.ArtistDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/artists")
@Tag(name = "Artist", description="Artist Operations")
public class ArtistQueryController implements GenericQueryControllerPort<ArtistDto>{

	private final ArtistQueryServicePort artistQueryService;
	private final ArtistMapper artistMapper;	
	
	public ArtistQueryController(ArtistQueryServicePort artistQueryService, ArtistMapper artistMapper) {
		super();
		this.artistQueryService = artistQueryService;
		this.artistMapper = artistMapper;
	}

	@GetMapping
	@Override
	@Operation(summary="Get a List of all Artist", description="Get a List of all Artist, @return an object Response with a List of DTO's")
	public ResponseEntity<List<ArtistDto>> getAll() {
		
		List<Artist>list = artistQueryService.getAll();
		
		return ResponseEntity.ok( !list.isEmpty()? artistMapper.fromListDomainToListDto(list):Collections.emptyList());
	
	}

	@GetMapping("/{id}")
	@Override
	@Operation(summary="Get a Artist according to an Id", description="Get a Artist according to an Id, @return an object Response with the entity required in a DTO")
	public ResponseEntity<ArtistDto> getById(@PathVariable Long id) {
		
		Optional<Artist> optArtist = artistQueryService.getById(id);
		
		return ResponseEntity.ok(optArtist.map(artistMapper::fromDomainToDto).orElse(new ArtistDto()));
	
	}
	
}

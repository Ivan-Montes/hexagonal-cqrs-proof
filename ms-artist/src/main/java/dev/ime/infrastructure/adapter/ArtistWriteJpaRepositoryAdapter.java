package dev.ime.infrastructure.adapter;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import dev.ime.config.ArtistMapper;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistWriteRepositoryPort;
import dev.ime.infrastructure.repository.ArtistWriteJpaRepository;


@Repository
public class ArtistWriteJpaRepositoryAdapter implements ArtistWriteRepositoryPort {

	private final ArtistWriteJpaRepository artistWriteJpaRepository;	
	private final ArtistMapper artistMapper;
	
	public ArtistWriteJpaRepositoryAdapter(ArtistWriteJpaRepository artistWriteJpaRepository, ArtistMapper artistMapper) {
		super();
		this.artistWriteJpaRepository = artistWriteJpaRepository;
		this.artistMapper = artistMapper;
	}

	@Override
	public Optional<Artist> save(Artist artist) {
		
		return Optional.ofNullable(
				artistMapper.fromJpaToDomain(
						artistWriteJpaRepository.save(
								artistMapper.fromDomainToJpa(artist))));
	}

	@Override
	public void deleteById(Long id) {
		
		artistWriteJpaRepository.deleteById(id);
		
	}

	@Override
	public Optional<Artist> findById(Long id) {
		
		return artistWriteJpaRepository.findById(id)
				.map(artistMapper::fromJpaToDomain);
	}

}

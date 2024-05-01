package dev.ime.infrastructure.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import dev.ime.config.ArtistMapper;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistReadRepositoryPort;
import dev.ime.infrastructure.repository.ArtistReadMongoRepository;

@Repository
public class ArtistReadMongoRepositoryAdapter implements ArtistReadRepositoryPort {

	private final ArtistReadMongoRepository artistReadMongoRepository;
	private final ArtistMapper artistMapper;
	
	public ArtistReadMongoRepositoryAdapter(ArtistReadMongoRepository artistReadMongoRepository,
			ArtistMapper artistMapper) {
		super();
		this.artistReadMongoRepository = artistReadMongoRepository;
		this.artistMapper = artistMapper;
	}

	@Override
	public List<Artist> findAll() {
		
		return artistMapper.fromListMongoToListDomain(artistReadMongoRepository.findAll());
	}

	@Override
	public Optional<Artist> findById(Long id) {
				
		return artistReadMongoRepository.findFirstByArtistId(id)
				.map(artistMapper::fromMongoToDomain);
	}

}

package dev.ime.application.handler;

import java.util.Optional;

import org.springframework.stereotype.Component;

import dev.ime.application.usecase.CreateCommand;
import dev.ime.domain.command.Command;
import dev.ime.domain.command.CommandHandler;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistWriteRepositoryPort;

@Component
public class CreateCommandHandler implements CommandHandler<Optional<Artist>>{

	private final ArtistWriteRepositoryPort artistWriteRepositoryPort;	
	
	public CreateCommandHandler(ArtistWriteRepositoryPort artistWriteRepositoryPort) {
		super();
		this.artistWriteRepositoryPort = artistWriteRepositoryPort;
	}

	@Override
	public Optional<Artist> handle(Command command) {
		
		if (command instanceof CreateCommand createCommand) {
			
			Artist artist = createCommand.artist();
			return artistWriteRepositoryPort.save(artist);
			
		} else {
				
			throw new IllegalArgumentException("Command not supported");
			
		}		
	}

}

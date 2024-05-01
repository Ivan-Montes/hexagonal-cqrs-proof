package dev.ime.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.ime.application.dispatch.CommandDispatcher;
import dev.ime.application.usecase.CreateCommand;
import dev.ime.application.usecase.DeleteByIdCommand;
import dev.ime.application.usecase.UpdateCommand;
import dev.ime.domain.command.CommandHandler;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.inbound.ArtistCommandServicePort;
import dev.ime.domain.port.outbound.ArtistPublisherPort;

@Service
public class ArtistCommandService implements ArtistCommandServicePort {

	private final CommandDispatcher commandDispatcher;	
	private final ArtistPublisherPort artistPublisherPort;
	
	public ArtistCommandService(CommandDispatcher commandDispatcher, ArtistPublisherPort artistPublisherPort) {
		super();
		this.commandDispatcher = commandDispatcher;
		this.artistPublisherPort = artistPublisherPort;
	}

	@Override
	public Optional<Artist> create(Artist artist) {
		
		CreateCommand createCommand = new CreateCommand(artist);
		CommandHandler<Optional<Artist>> commandHandler = commandDispatcher.getCommandHandler(createCommand );
		Optional<Artist> optArtistCreated = commandHandler.handle(createCommand);
		optArtistCreated.ifPresent(artistPublisherPort::publishInsertEvent);
		
		return optArtistCreated;
	}

	@Override
	public Optional<Artist> update(Long id, Artist artist) {
		
		UpdateCommand updateCommand = new UpdateCommand(id, artist);
		CommandHandler<Optional<Artist>> commandHandler = commandDispatcher.getCommandHandler(updateCommand);
		Optional<Artist> optArtistUpdated = commandHandler.handle(updateCommand);
		optArtistUpdated.ifPresent(artistPublisherPort::publishUpdateEvent);
		
		return optArtistUpdated;
	}

	@Override
	public Boolean deleteById(Long id) {
		
		DeleteByIdCommand deleteByIdCommand = new DeleteByIdCommand(id);
		CommandHandler<Boolean> commandHandler = commandDispatcher.getCommandHandler(deleteByIdCommand);
		
		boolean resultValue = commandHandler.handle(deleteByIdCommand);
		if (resultValue) { artistPublisherPort.publishDeleteEvent(id);}

		return resultValue;
	}

}

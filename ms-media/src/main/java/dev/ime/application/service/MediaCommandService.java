package dev.ime.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.ime.application.dispatch.CommandDispatcher;
import dev.ime.application.usecase.CreateCommand;
import dev.ime.application.usecase.DeleteByIdCommand;
import dev.ime.application.usecase.UpdateCommand;
import dev.ime.domain.command.CommandHandler;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.inbound.MediaCommandServicePort;
import dev.ime.domain.port.outbound.MediaPublisherPort;

@Service
public class MediaCommandService implements MediaCommandServicePort{

	private final CommandDispatcher commandDispatcher;	
	private final MediaPublisherPort mediaPublisherPort;

	public MediaCommandService(CommandDispatcher commandDispatcher, MediaPublisherPort mediaPublisherPort) {
		super();
		this.commandDispatcher = commandDispatcher;
		this.mediaPublisherPort = mediaPublisherPort;
	}

	@Override
	public Optional<Media> create(Media media) {

		CreateCommand createCommand = new CreateCommand(media);
		CommandHandler<Optional<Media>> commandHandler = commandDispatcher.getCommandHandler(createCommand);
		Optional<Media> optMediaCreated = commandHandler.handle(createCommand);
		optMediaCreated.ifPresent(mediaPublisherPort::publishInsertEvent);
		
		return optMediaCreated;
	}

	@Override
	public Optional<Media> update(Long id, Media media) {
		
		UpdateCommand updateCommand = new UpdateCommand(id, media);
		CommandHandler<Optional<Media>> commandHandler = commandDispatcher.getCommandHandler(updateCommand);
		Optional<Media> optMediaUpdated = commandHandler.handle(updateCommand);
		optMediaUpdated.ifPresent(mediaPublisherPort::publishUpdateEvent);
		
		return optMediaUpdated;
	}

	@Override
	public Boolean deleteById(Long id) {
		
		DeleteByIdCommand deleteByIdCommand = new DeleteByIdCommand(id);
		CommandHandler<Boolean> commandHandler = commandDispatcher.getCommandHandler(deleteByIdCommand);
		
		boolean resultValue = commandHandler.handle(deleteByIdCommand);
		if (resultValue) { mediaPublisherPort.publishDeleteEvent(id);}

		return resultValue;
	}

}

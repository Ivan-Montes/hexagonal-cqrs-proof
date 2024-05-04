package dev.ime.application.dispatch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import dev.ime.application.handler.CreateCommandHandler;
import dev.ime.application.handler.DeleteByIdCommandHandler;
import dev.ime.application.handler.UpdateCommandHandler;
import dev.ime.application.usecase.CreateCommand;
import dev.ime.application.usecase.DeleteByIdCommand;
import dev.ime.application.usecase.UpdateCommand;
import dev.ime.domain.command.Command;
import dev.ime.domain.command.CommandHandler;

@Component
public class CommandDispatcher {

	private final Map<Class<? extends Command>, CommandHandler<?>> commandHandlers  = new HashMap<>();
	
	public CommandDispatcher(CreateCommandHandler createCommandHandler, DeleteByIdCommandHandler deleteByIdCommandHandler, UpdateCommandHandler updateCommandHandler) {
		super();
		commandHandlers.put(CreateCommand.class, createCommandHandler);
		commandHandlers.put(DeleteByIdCommand.class, deleteByIdCommandHandler);
		commandHandlers.put(UpdateCommand.class, updateCommandHandler);
	}
	
	public <U> CommandHandler<U> getCommandHandler(Command command) {

		@SuppressWarnings("unchecked")
		CommandHandler<U> handler = (CommandHandler<U>) commandHandlers.get(command.getClass());
		
		if (handler == null) {
			
			throw new IllegalArgumentException("No handler found for command of type " + command.getClass().getName());
		} 
		
		return handler;
		
	}
	
}

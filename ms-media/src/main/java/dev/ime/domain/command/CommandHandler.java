package dev.ime.domain.command;

public interface CommandHandler<R> {
	
	R handle(Command command);
	
}

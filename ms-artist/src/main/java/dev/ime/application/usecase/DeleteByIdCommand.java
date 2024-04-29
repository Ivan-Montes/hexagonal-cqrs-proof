package dev.ime.application.usecase;

import dev.ime.domain.command.Command;

public record DeleteByIdCommand(
		Long id
		) implements Command {
}

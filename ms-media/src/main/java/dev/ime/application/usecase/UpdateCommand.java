package dev.ime.application.usecase;

import dev.ime.domain.command.Command;
import dev.ime.domain.model.Media;

public record UpdateCommand(Long id, Media media) implements Command {

}

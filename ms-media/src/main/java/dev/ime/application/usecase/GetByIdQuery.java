package dev.ime.application.usecase;

import dev.ime.domain.query.Query;

public record GetByIdQuery(Long id) implements Query{

}

package com.umb.taller.domain.exception;

public class EntityNotFoundException extends DomainException {
    public EntityNotFoundException(String entity, String id) {
        super(entity + " with id " + id + " not found");
    }
}


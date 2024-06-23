package com.homecooked.common.exception;

public class EntityDeletedException extends RuntimeException {

    public EntityDeletedException(String entityName, Integer entityId) {
        super(String.format("% with ID % has been deleted", entityName, entityId));
    }
}
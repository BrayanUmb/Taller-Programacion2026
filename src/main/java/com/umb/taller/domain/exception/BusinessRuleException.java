package com.umb.taller.domain.exception;

public class BusinessRuleException extends DomainException {
    public BusinessRuleException(String rule) {
        super("Business rule violated: " + rule);
    }
}

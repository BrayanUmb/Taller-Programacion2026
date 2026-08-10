package com.umb.taller.domain;

import com.umb.taller.domain.exception.BusinessRuleException;
import java.math.BigDecimal;

public interface PaymentMethod {
    void charge(BigDecimal amount) throws BusinessRuleException;
    String description();
}


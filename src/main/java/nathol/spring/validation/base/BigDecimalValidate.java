package nathol.spring.validation.base;

import java.math.BigDecimal;

import nathol.spring.validation.components.NumberValidator;

public final class BigDecimalValidate extends NumberValidator<BigDecimal> {

    public BigDecimalValidate(BigDecimal value) {
        super(value);
    }

    public BigDecimalValidate(BigDecimal value, BigDecimal defaultValue) {
        super(value, defaultValue);
    }

}

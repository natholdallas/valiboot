package nathol.spring.validation.components;

import java.math.BigDecimal;

import nathol.spring.validation.NumberValidator;

public class BigDecimalValidate extends NumberValidator<BigDecimal> {

    public BigDecimalValidate(BigDecimal value) {
        super(value);
    }

    public BigDecimalValidate(BigDecimal value, BigDecimal defaultValue) {
        super(value);
    }

    @Override
    protected void validate0() {
        if (min != null) {
            int result = value.compareTo(min);
            isTrue(result == 1 || result == 0);
        }
        if (max != null) {
            int result = value.compareTo(max);
            isTrue(result == -1 || result == 0);
        }
        for (BigDecimal forbid : forbids) {
            isFalse(value.compareTo(forbid) == 0);
        }
        boolean haveEnumration = true;
        for (BigDecimal enumration : enumrations) {
            if (value.compareTo(enumration) == 0) {
                haveEnumration = true;
                break;
            }
            haveEnumration = false;
        }
        isTrue(haveEnumration);
    }

}

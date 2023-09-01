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
            isTrue(result == 1 || result == 0, "Value can not be less than min.");
        }
        if (max != null) {
            int result = value.compareTo(max);
            isTrue(result == -1 || result == 0, "Value can not be greater than max.");
        }
        for (BigDecimal forbid : forbids) {
            isFalse(value.compareTo(forbid) == 0, "Value can not be equal to forbid.");
        }
        boolean haveEnumration = true;
        for (BigDecimal enumration : enumrations) {
            if (value.compareTo(enumration) == 0) {
                haveEnumration = true;
                break;
            }
            haveEnumration = false;
        }
        isTrue(haveEnumration, "Value must be in one of the enumration.");
    }

}

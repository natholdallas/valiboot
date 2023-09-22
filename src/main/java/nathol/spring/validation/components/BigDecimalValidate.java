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
        boolean notIn = false;
        for (BigDecimal it : notInValues) {
            if (!value.equals(it))
                continue;
            notIn = true;
            break;
        }
        isFalse(notIn, "Value can not in " + notInValues + " .");
        boolean hasValue = false;
        for (BigDecimal it : hasValues) {
            if (!value.equals(it))
                continue;
            hasValue = true;
            break;
        }
        isTrue(hasValue, "Value must be in" + hasValues + " .");
    }

}

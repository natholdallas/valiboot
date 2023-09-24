package nathol.spring.validation.components;

import nathol.spring.validation.NumberValidator;

public final class LongValidate extends NumberValidator<Long> {

    public LongValidate(Long value) {
        super(value);
    }

    public LongValidate(Long value, Long defaultValue) {
        super(value, defaultValue);
    }

    @Override
    protected void validate0() {
        size: {
            if (min != null && max != null) {
                isTrue(value >= min && value <= max,
                        "Value can not be less than min or greater than max.");
                break size;
            }
            if (min != null) {
                isTrue(value >= min, "Value can not be less than min.");
            }
            if (max != null) {
                isTrue(value <= max, "Value can not be greater than max.");
            }
        }
        if (notInValues != null) {
            boolean notIn = false;
            for (Long it : notInValues) {
                if (value == it) {
                    notIn = true;
                    break;
                }
            }
            isFalse(notIn, "Value can not in " + notInValues + " .");
        }
        if (hasValues != null) {
            boolean hasValue = false;
            for (Long it : hasValues) {
                if (value == it) {
                    hasValue = true;
                    break;
                }
            }
            isTrue(hasValue, "Value must be in " + hasValues + " .");
        }
    }

}

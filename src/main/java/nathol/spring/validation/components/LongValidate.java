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
        if (min != null)
            isTrue(value >= min, "Value can not be less than min.");
        if (max != null)
            isTrue(value <= max, "Value can not be greater than max.");
        boolean notIn = false;
        for (Long it : notInValues) {
            if (!value.equals(it))
                continue;
            notIn = true;
            break;
        }
        isFalse(notIn, "Value can not in " + notInValues + " .");
        boolean hasValue = false;
        for (Long it : hasValues) {
            if (!value.equals(it))
                continue;
            hasValue = true;
            break;
        }
        isTrue(hasValue, "Value must be in" + hasValues + " .");
    }

}

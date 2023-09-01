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
        if (min != null) {
            isTrue(value >= min, "Value can not be less than min.");
        }
        if (max != null) {
            isTrue(value <= max, "Value can not be greater than max.");
        }
        for (Long forbid : forbids) {
            isFalse(value == forbid, "Value can not be equal to forbid.");
        }
        boolean haveEnumration = true;
        for (Long enumration : enumrations) {
            if (value == enumration) {
                haveEnumration = true;
                break;
            }
            haveEnumration = false;
        }
        isTrue(haveEnumration, "Value must be in one of the enumration.");
    }

}

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
            isTrue(value >= min);
        }
        if (max != null) {
            isTrue(value <= max);
        }
        for (Long forbid : forbids) {
            isFalse(value == forbid);
        }
        boolean haveEnumration = true;
        for (Long enumration : enumrations) {
            if (value.compareTo(enumration) == 0) {
                haveEnumration = true;
                break;
            }
            haveEnumration = false;
        }
        isTrue(haveEnumration);
    }

}

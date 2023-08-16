package nathol.spring.validation.base;

import nathol.spring.validation.components.NumberValidator;

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
    }

}

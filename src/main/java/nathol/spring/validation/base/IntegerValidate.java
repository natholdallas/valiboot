package nathol.spring.validation.base;

import java.util.Optional;

import nathol.spring.validation.components.NumberValidator;

public final class IntegerValidate extends NumberValidator<Integer> {

    public IntegerValidate(Integer value) {
        super(value);
    }

    public IntegerValidate(Integer value, Integer defaultValue) {
        super(Optional.ofNullable(value).orElse(defaultValue));
    }

    @Override
    protected void validate0() {
        if (min != null) {
            isTrue(value >= min);
        }
        if (max != null) {
            isTrue(value <= max);
        }
        for (Integer forbid : forbids) {
            isFalse(value == forbid);
        }
    }

}

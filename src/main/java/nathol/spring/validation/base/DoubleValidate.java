package nathol.spring.validation.base;

import java.util.Optional;

import nathol.spring.validation.components.NumberValidator;

public final class DoubleValidate extends NumberValidator<Double> {

    public DoubleValidate(Double value) {
        super(value);
    }

    public DoubleValidate(Double value, Double defaultValue) {
        super(Optional.ofNullable(value).orElse(defaultValue));
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
        for (Double forbid : forbids) {
            isFalse(value.compareTo(forbid) == 0);
        }
    }

}

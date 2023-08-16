package nathol.spring.validation.base;

import nathol.spring.validation.components.NumberValidator;

public final class FloatValidate extends NumberValidator<Float> {

    public FloatValidate(Float value) {
        super(value);
    }

    public FloatValidate(Float value, Float defaultValue) {
        super(value, defaultValue);
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
        for (Float forbid : forbids) {
            isFalse(value.compareTo(forbid) == 0);
        }
    }

}

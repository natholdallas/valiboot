package nathol.spring.validation.components;

import nathol.spring.validation.NumberValidator;

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
            isTrue(result == 1 || result == 0, "Value can not be less than min.");
        }
        if (max != null) {
            int result = value.compareTo(max);
            isTrue(result == -1 || result == 0, "Value can not be greater than max.");
        }
        for (Float forbid : forbids) {
            isFalse(value.compareTo(forbid) == 0, "Value can not be equal to forbid.");
        }
        boolean haveEnumration = true;
        for (Float enumration : enumrations) {
            if (value.compareTo(enumration) == 0) {
                haveEnumration = true;
                break;
            }
            haveEnumration = false;
        }
        isTrue(haveEnumration, "Value must be in one of the enumration.");
    }

}

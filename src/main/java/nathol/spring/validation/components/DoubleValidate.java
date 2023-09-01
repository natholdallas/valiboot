package nathol.spring.validation.components;

import java.util.Optional;

import nathol.spring.validation.NumberValidator;

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
            isTrue(result == 1 || result == 0, "Value can not be less than min.");
        }
        if (max != null) {
            int result = value.compareTo(max);
            isTrue(result == -1 || result == 0, "Value can not be greater than max.");
        }
        for (Double forbid : forbids) {
            isFalse(value.compareTo(forbid) == 0, "Value can not be equal to forbid.");
        }
        boolean haveEnumration = true;
        for (Double enumration : enumrations) {
            if (value.compareTo(enumration) == 0) {
                haveEnumration = true;
                break;
            }
            haveEnumration = false;
        }
        isTrue(haveEnumration, "Value must be in one of the enumration.");
    }

}

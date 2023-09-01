package nathol.spring.validation.components;

import java.util.Optional;

import nathol.spring.validation.NumberValidator;

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
            isTrue(value >= min, "Value can not be less than min.");
        }
        if (max != null) {
            isTrue(value <= max, "Value can not be greater than max.");
        }
        for (Integer forbid : forbids) {
            isFalse(value == forbid, "Value can not be equal to forbid.");
        }
        boolean haveEnumration = true;
        for (Integer enumration : enumrations) {
            if (value == enumration) {
                haveEnumration = true;
                break;
            }
            haveEnumration = false;
        }
        isTrue(haveEnumration, "Value must be in one of the enumration.");
    }

}

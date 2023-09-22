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
        if (min != null)
            isTrue(value >= min, "Value can not be less than min.");
        if (max != null)
            isTrue(value <= max, "Value can not be greater than max.");
        boolean notIn = false;
        for (Integer it : notInValues) {
            if (value != it)
                continue;
            notIn = true;
            break;
        }
        isFalse(notIn, "Value can not in " + notInValues + " .");
        boolean hasValue = false;
        for (Integer it : hasValues) {
            if (value != it)
                continue;
            hasValue = true;
            break;
        }
        isTrue(hasValue, "Value must be in" + hasValues + " .");
    }

}

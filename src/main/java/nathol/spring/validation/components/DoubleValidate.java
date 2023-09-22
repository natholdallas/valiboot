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
        boolean notIn = false;
        for (Double it : notInValues) {
            if (!value.equals(it))
                continue;
            notIn = true;
            break;
        }
        isFalse(notIn, "Value can not in " + notInValues + " .");
        boolean hasValue = false;
        for (Double it : hasValues) {
            if (!value.equals(it))
                continue;
            hasValue = true;
            break;
        }
        isTrue(hasValue, "Value must be in" + hasValues + " .");
    }

}

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
        boolean notIn = false;
        for (Float it : notInValues) {
            if (!value.equals(it))
                continue;
            notIn = true;
            break;
        }
        isFalse(notIn, "Value can not in " + notInValues + " .");
        boolean hasValue = false;
        for (Float it : hasValues) {
            if (!value.equals(it))
                continue;
            hasValue = true;
            break;
        }
        isTrue(hasValue, "Value must be in" + hasValues + " .");
    }

}

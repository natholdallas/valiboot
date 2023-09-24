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
        size: {
            if (min != null && max != null) {
                int minResult = value.compareTo(min);
                int maxResult = value.compareTo(max);
                isTrue((minResult == 1 || minResult == 0) && (maxResult == -1 || maxResult == 0),
                        "Value can not be less than min or greater than max.");
                break size;
            }
            if (min != null) {
                int result = value.compareTo(min);
                isTrue(result == 1 || result == 0, "Value can not be less than min.");
            }
            if (max != null) {
                int result = value.compareTo(max);
                isTrue(result == -1 || result == 0, "Value can not be greater than max.");
            }
        }
        if (notInValues != null) {
            boolean notIn = false;
            for (Float it : notInValues) {
                if (value.equals(it)) {
                    notIn = true;
                    break;
                }
            }
            isFalse(notIn, "Value can not in " + notInValues + " .");
        }
        if (hasValues != null) {
            boolean hasValue = false;
            for (Float it : hasValues) {
                if (value.equals(it)) {
                    hasValue = true;
                    break;
                }
            }
            isTrue(hasValue, "Value must be in " + hasValues + " .");
        }
    }

}

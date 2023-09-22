package nathol.spring.validation.components;

import java.util.regex.Pattern;

import nathol.spring.validation.SequenceValidator;

public final class StringValidate extends SequenceValidator<String> {

    public StringValidate(String value) {
        super(value);
    }

    public StringValidate(String value, String defaultValue) {
        super(value, defaultValue);
    }

    @Override
    protected void validate0() {
        if (minLength != null)
            isTrue(value.length() >= minLength, "Value can not be less than minLength.");
        if (maxLength != null)
            isTrue(value.length() <= maxLength, "Value can not be greater than maxLength.");
        if (notEmpty)
            isTrue(!value.isEmpty(), "Value can not be empty.");
        if (notBlank)
            isTrue(!value.isBlank(), "Value can not be blank.");
        if (regex != null) {
            boolean expression = Pattern.compile(regex)
                    .matcher(value)
                    .matches();
            isTrue(expression, "Value does not match regex.");
        }
        boolean hasValue = false;
        for (String it : hasValues) {
            if (!value.equals(it))
                continue;
            hasValue = true;
            break;
        }
        isTrue(hasValue, "Value must be in" + hasValues + " .");
    }

}

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
        if (minLength != null) {
            isTrue(value.length() >= minLength, "Value can not be less than minLength.");
        }
        if (maxLength != null) {
            isTrue(value.length() <= maxLength, "Value can not be greater than maxLength.");
        }
        if (notEmpty) {
            isTrue(!value.isEmpty(), "Value can not be empty.");
        }
        if (notBlank) {
            isTrue(!value.isBlank(), "Value can not be blank.");
        }
        if (regex != null) {
            boolean expression = Pattern.compile(regex)
                    .matcher(value)
                    .matches();
            isTrue(expression, "Value does not match regex.");
        }
        boolean haveEnumration = true;
        for (String enumration : enumrations) {
            if (value.equals(enumration)) {
                haveEnumration = true;
                break;
            }
            haveEnumration = false;
        }
        isTrue(haveEnumration, "Value must be in one of the enumration.");
    }

}

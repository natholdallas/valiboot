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
            isTrue(value.length() >= minLength);
        }
        if (maxLength != null) {
            isTrue(value.length() <= maxLength);
        }
        if (notEmpty) {
            isTrue(!value.isEmpty());
        }
        if (notBlank) {
            isTrue(!value.isBlank());
        }
        if (regex != null) {
            boolean expression = Pattern.compile(regex)
                    .matcher(value)
                    .matches();
            isTrue(expression);
        }
        boolean haveEnumration = true;
        for (String enumration : enumrations) {
            if (value.equals(enumration)) {
                haveEnumration = true;
                break;
            }
            haveEnumration = false;
        }
        isTrue(haveEnumration);
    }

}

package nathol.spring.validation.base;

import java.util.regex.Pattern;

import nathol.spring.validation.components.SequenceValidator;

public final class StringValidate extends SequenceValidator<String> {

    public StringValidate(String value) {
        super(value);
    }

    public StringValidate(String value, String defaultValue) {
        super(value, defaultValue);
    }

    @Override
    public void validate0() {
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
    }

}

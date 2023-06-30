package nathol.spring.validation;

import java.util.Collection;

import nathol.spring.validation.components.BooleanValidator;
import nathol.spring.validation.components.CollectionValidator;
import nathol.spring.validation.components.NumberValidator;
import nathol.spring.validation.components.SequenceValidator;
import nathol.spring.validation.components.Validator;

public final class Validate {

    private Validate() {
    }

    public static <T> Validator<T> of(T value) {
        return Validator.of(value);
    }

    public static <T> Validator<T> of(T value, T defaultValue) {
        return Validator.of(value, defaultValue);
    }

    public static NumberValidator<Integer> of(Integer value) {
        return NumberValidator.of(value);
    }

    public static NumberValidator<Integer> of(Integer value, Integer defaultValue) {
        return NumberValidator.of(value, defaultValue);
    }

    public static NumberValidator<Long> of(Long value) {
        return NumberValidator.of(value);
    }

    public static NumberValidator<Long> of(Long value, Long defaultValue) {
        return NumberValidator.of(value, defaultValue);
    }

    public static NumberValidator<Float> of(Float value) {
        return NumberValidator.of(value);
    }

    public static NumberValidator<Float> of(Float value, Float defualtValue) {
        return NumberValidator.of(value, defualtValue);
    }

    public static NumberValidator<Double> of(Double value) {
        return NumberValidator.of(value);
    }

    public static NumberValidator<Double> of(Double value, Double defaultValue) {
        return NumberValidator.of(value, defaultValue);
    }

    public static SequenceValidator<String> of(String value) {
        return SequenceValidator.of(value);
    }

    public static SequenceValidator<String> of(String value, String defaultValue) {
        return SequenceValidator.of(value, defaultValue);
    }

    public static <T extends Collection<E>, E> CollectionValidator<T, E> of(T value) {
        return CollectionValidator.of(value);
    }

    public static <T extends Collection<E>, E> CollectionValidator<T, E> of(T value, T defaultValue) {
        return CollectionValidator.of(value, defaultValue);
    }

    public static BooleanValidator of(Boolean value) {
        return BooleanValidator.of(value);
    }

    public static BooleanValidator of(Boolean value, Boolean defaultValue) {
        return BooleanValidator.of(value, defaultValue);
    }

}

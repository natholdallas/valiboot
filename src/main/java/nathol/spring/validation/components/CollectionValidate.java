package nathol.spring.validation.components;

import java.util.Collection;
import java.util.Optional;

import nathol.spring.validation.CollectionValidator;

public final class CollectionValidate<T extends Collection<E>, E> extends CollectionValidator<T, E> {

    public CollectionValidate(T value) {
        super(value);
    }

    public CollectionValidate(T value, T defaultValue) {
        super(Optional.ofNullable(value).orElse(defaultValue));
    }

    @Override
    protected void validate0() {
        if (minSize != null)
            isTrue(value.size() >= minSize, "Value can not be less than min.");
        if (maxSize != null)
            isTrue(value.size() <= maxSize, "Value can not be greater than max.");
        if (contains != null)
            isTrue(value.contains(contains), "The contains element is not in value");
        if (notEmpty)
            isFalse(value.isEmpty(), "Value can not be empty.");
    }

}

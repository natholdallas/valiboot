package nathol.spring.validation.base;

import java.util.Collection;
import java.util.Optional;

import nathol.spring.validation.components.CollectionValidator;

/**
 * CollectionValidator 的实现
 */
public final class CollectionValidate<T extends Collection<E>, E> extends CollectionValidator<T, E> {

    public CollectionValidate(T value) {
        super(value);
    }

    public CollectionValidate(T value, T defaultValue) {
        super(Optional.ofNullable(value).orElse(defaultValue));
    }

    @Override
    public void validate0() {
        if (minSize != null) {
            isTrue(value.size() >= minSize);
        }
        if (maxSize != null) {
            isTrue(value.size() <= maxSize);
        }
    }

}

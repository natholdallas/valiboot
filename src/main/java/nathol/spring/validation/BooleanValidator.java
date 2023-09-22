package nathol.spring.validation;

import java.util.function.Predicate;

public final class BooleanValidator extends Validator<Boolean> {

    public BooleanValidator(Boolean value) {
        super(value);
    }

    public BooleanValidator(Boolean value, Boolean defaultValue) {
        super(value, defaultValue);
    }

    /* --------------- 链式编程 --------------- */

    @Override
    public BooleanValidator wrapper(Predicate<? super Boolean> wrapper) {
        super.wrapper(wrapper);
        return this;
    }

    @Override
    public BooleanValidator required() {
        super.required();
        return this;
    }

    /* --------------- 静态构造 --------------- */

    public static BooleanValidator of(Boolean value) {
        return new BooleanValidator(value);
    }

    public static BooleanValidator of(Boolean value, Boolean defaultValue) {
        return new BooleanValidator(value, defaultValue);
    }

}

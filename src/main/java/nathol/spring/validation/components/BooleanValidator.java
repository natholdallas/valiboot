package nathol.spring.validation.components;

import java.util.function.Predicate;

import nathol.spring.validation.exception.InvalidException;

/**
 * 由于 Boolean 为 final class, 因此无需有多种实现
 */
public final class BooleanValidator extends Validator<Boolean> {

    protected boolean isTrue = false;
    protected boolean isFalse = false;

    /**
     * 传入布尔值
     * @param value 布尔值
     */
    public BooleanValidator(Boolean value) {
        super(value);
    }

    /**
     * 传入的参数允许带有默认值
     * @param value 布尔值
     * @param defaultValue 默认值
     */
    public BooleanValidator(Boolean value, Boolean defaultValue) {
        super(value, defaultValue);
    }

    /**
     * 参阅 {@link Validator}, 此方法重写只为了方便链式调用
     */
    @Override
    public BooleanValidator wrapper(Predicate<? super Boolean> wrapper) {
        super.wrapper(wrapper);
        return this;
    }

    /**
     * 参阅 {@link Validator}, 此方法重写只为了方便链式调用
     */
    @Override
    public BooleanValidator nullable() {
        super.nullable();
        return this;
    }

    /**
     * 设置如果出错的则会抛出的异常对象
     * @param exception 参阅 {@link InvalidException}
     */
    public BooleanValidator except(InvalidException exception) {
        super.except(exception);
        return this;
    }

    /**
     * 校验 value 是否为 True, 如果不是, 则抛出 {@link InvalidException}
     */
    public BooleanValidator isTrue() {
        this.isTrue = true;
        return this;
    }

    /**
     * 校验 value 是否为 False, 如果不是, 则抛出 {@link InvalidException}
     */
    public BooleanValidator isFalse() {
        this.isFalse = true;
        return this;
    }

    /**
     * 校验实现
     */
    @Override
    public void validate0() {
        if (isTrue) {
            isTrue(this.value);
        }
        if (isFalse) {
            isFalse(this.value);
        }
    }

    /**
     * 快捷实例化方法
     * @param value 布尔值
     */
    public static BooleanValidator of(Boolean value) {
        return new BooleanValidator(value);
    }

    /**
     * 快捷实例化方法
     * @param value 布尔值
     * @param defaultValue 默认值
     */
    public static BooleanValidator of(Boolean value, Boolean defaultValue) {
        return new BooleanValidator(value, defaultValue);
    }

}

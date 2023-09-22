package nathol.spring.validation;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

import nathol.spring.validation.components.CollectionValidate;

public abstract class CollectionValidator<T extends Collection<E>, E> extends Validator<T> {

    protected Integer minSize;
    protected Integer maxSize;
    protected boolean notEmpty;
    protected Object contains;

    public CollectionValidator(T value) {
        super(value);
    }

    public CollectionValidator(T value, T defaultValue) {
        super(value, defaultValue);
    }

    /**
     * 设置 value 元素不能为空
     */
    public CollectionValidator<T, E> notEmpty() {
        this.notEmpty = true;
        return this;
    }

    /**
     * 设置 value 的元素中是否有包含传入的值
     */
    public CollectionValidator<T, E> contains(Object value) {
        this.contains = value;
        return this;
    }

    /**
     * 校验 value.size() 的最小值
     */
    public CollectionValidator<T, E> minSize(int minSize) {
        this.minSize = minSize;
        return this;
    }

    /**
     * 校验 value.size() 的最大值
     */
    public CollectionValidator<T, E> maxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    /**
     * 设置 value.size() 元素的最大值和最小值
     * @param minSize 最小值
     * @param maxSize 最大值
     */
    public CollectionValidator<T, E> range(int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        return this;
    }

    /**
     * 在校验之后返回 stream 流
     */
    public Stream<E> stream() {
        validate();
        return value.stream();
    }

    /* --------------- 链式编程 --------------- */

    @Override
    public CollectionValidator<T, E> wrapper(Predicate<? super T> wrapper) {
        super.wrapper(wrapper);
        return this;
    }

    @Override
    public CollectionValidator<T, E> required() {
        super.required();
        return this;
    }

    /* --------------- 静态构造 --------------- */

    public static <T extends Collection<E>, E> CollectionValidator<T, E> of(T value) {
        return new CollectionValidate<>(value);
    }

    public static <T extends Collection<E>, E> CollectionValidator<T, E> of(T value, T defaultValue) {
        return new CollectionValidate<>(value, defaultValue);
    }

}

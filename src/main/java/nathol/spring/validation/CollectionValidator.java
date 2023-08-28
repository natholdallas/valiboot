package nathol.spring.validation;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

import nathol.spring.validation.components.CollectionValidate;
import nathol.spring.validation.err.InvalidException;

/**
 * 集合的校验抽象思路
 */
public abstract class CollectionValidator<T extends Collection<E>, E> extends Validator<T> {

    protected Integer minSize;
    protected Integer maxSize;

    /**
     * 传入参数, 参数类型必须为 Collection 的子类
     * @param value 值
     */
    public CollectionValidator(T value) {
        super(value);
    }

    /**
     * 传入的参数允许带有默认值
     * @param value 值
     * @param defaultValue 默认值
     */
    public CollectionValidator(T value, T defaultValue) {
        super(value, defaultValue);
    }

    /**
     * 参阅 {@link Validator}, 此方法重写只为了方便链式调用
     */
    @Override
    public CollectionValidator<T, E> wrapper(Predicate<? super T> wrapper) {
        super.wrapper(wrapper);
        return this;
    }

    /**
     * 参阅 {@link Validator}, 此方法重写只为了方便链式调用
     */
    @Override
    public CollectionValidator<T, E> nullable() {
        super.nullable();
        return this;
    }

    /**
     * 设置如果出错的则会抛出的异常对象
     * @param exception 参阅 {@link InvalidException}
     */
    public CollectionValidator<T, E> except(InvalidException exception) {
        super.except(exception);
        return this;
    }

    /**
     * 校验 value 的最小值, 如果不符合, 则抛出 {@link InvalidException}
     * @param minSize 值
     */
    public CollectionValidator<T, E> minSize(int minSize) {
        this.minSize = minSize;
        return this;
    }

    /**
     * 校验 value 长度的最大值, 如果不符合, 则抛出 {@link InvalidException}
     * @param minSize 值
     */
    public CollectionValidator<T, E> maxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    /**
     * 指定 value 长度的最大值和最小值, 如果不符合, 则抛出 {@link InvalidException}
     * @param minSize 最小值
     * @param maxSize 最大值
     */
    public CollectionValidator<T, E> range(int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        return this;
    }

    /**
     * 在校验的时候顺带返回值, 并且变成 stream 流
     * @return 值
     */
    public Stream<E> stream() {
        validate();
        return value.stream();
    }

    /**
     * 快捷实例化方法
     * @param <T> 记录传入的参数类型
     * @param <E> 记录 Collection 中的元素类型 (Element Type)
     * @param value 值
     */
    public static <T extends Collection<E>, E> CollectionValidator<T, E> of(T value) {
        return new CollectionValidate<>(value);
    }

    /**
     * 快捷实例化方法
     * @param <T> 记录传入的参数类型
     * @param <E> 记录 Collection 中的元素类型 (Element Type)
     * @param value 值
     * @param defaultValue 默认值
     */
    public static <T extends Collection<E>, E> CollectionValidator<T, E> of(T value, T defaultValue) {
        return new CollectionValidate<>(value, defaultValue);
    }

}

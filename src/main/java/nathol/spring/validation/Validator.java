package nathol.spring.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

import nathol.spring.validation.err.InvalidException;

/**
 * 所有验证器的父类 <br/>
 * 可直接实例化,但我推荐使用位于 nathol.spring.validation 包下的 {@link Validate} <br/>
 * 此验证器为链式调用 <br/>
 * 此验证器的实现思路均使用 Lazy load (惰性加载,又称懒加载), 因此不用考虑链式调用的顺序性
 */
public class Validator<T> {

    protected final T value;
    protected final Collection<Predicate<? super T>> wrappers = new ArrayList<>();
    protected boolean nullable = false;
    protected InvalidException exception = new InvalidException("Invalid Param.");

    /**
     * 传入的参数,任意一个,你可以通过继承此类 <br/>
     * 自己实现一套验证集
     * @param value 值
     */
    public Validator(T value) {
        this.value = value;
    }

    /**
     * 传入的参数允许带有默认值
     * @param value 值
     * @param defaultValue 默认值
     */
    public Validator(T value, T defaultValue) {
        this.value = Optional.ofNullable(value).orElse(defaultValue);
    }

    /**
     * 条件验证,通过读到 value 进行判断返回 boolean
     * @param wrapper 条件
     */
    public Validator<T> wrapper(Predicate<? super T> wrapper) {
        this.wrappers.add(wrapper);
        return this;
    }

    /**
     * 设置此 value 可以为 null 值
     */
    public Validator<T> nullable() {
        nullable = true;
        return this;
    }

    /**
     * 设置如果出错的则会抛出的异常对象
     * @param exception 参阅 {@link InvalidException}
     */
    public Validator<T> except(InvalidException exception) {
        this.exception = exception;
        return this;
    }

    /**
     * 开始校验, 请不要继承此类
     */
    public final void validate() {
        if (this.value == null) {
            isTrue(nullable);
            return;
        }
        isTrue(this.value != null);
        validate0();
        this.wrappers.forEach(it -> isTrue(it.test(value)));
    }

    /**
     * 占位, 为继承此类需实现的方法 <br/>
     * 此类秉持着约定大于配置
     */
    protected void validate0() {
    }

    /**
     * 在校验的时候顺带返回值, 常用的方法
     * @return this.value
     */
    public T extract() {
        validate();
        return this.value;
    }

    /**
     * 在校验的时候顺带返回值, 并且由 java.util 包中的 {@link Optional} 打包获取
     * @return {@link Optional} 类型
     */
    public Optional<T> optional() {
        validate();
        return Optional.ofNullable(this.value);
    }

    /**
     * 校验传入的布尔值是否为 True, 如果不是, 则抛出 {@link InvalidException}
     * @param expression 布尔值
     */
    public void isTrue(boolean expression) {
        if (expression) {
            return;
        }
        throw this.exception;
    }

    /**
     * 校验传入的布尔值是否为 False, 如果不是, 则抛出 {@link InvalidException}
     * @param expression 布尔值
     */
    public void isFalse(boolean expression) {
        if (!expression) {
            return;
        }
        throw this.exception;
    }

    /**
     * 校验传入的对象是否为 null, 如果是, 则抛出 {@link InvalidException}
     * @param object 参数
     */
    public void isNull(Object object) {
        if (object != null) {
            return;
        }
        throw this.exception;
    }

    /**
     * 快捷实例化方法
     * @param <T> 记录传入的参数类型
     * @param value 值
     */
    public static <T> Validator<T> of(T value) {
        return new Validator<>(value);
    }

    /**
     * 快捷实例化方法
     * @param <T> 记录传入的参数类型
     * @param value 值
     * @param defaultValue 默认值
     */
    public static <T> Validator<T> of(T value, T defaultValue) {
        return new Validator<>(value, defaultValue);
    }

}

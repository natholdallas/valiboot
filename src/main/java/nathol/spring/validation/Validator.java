package nathol.spring.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * 此验证器的实现思路均使用 Lazy load (惰性加载,又称懒加载) <br/>
 * 不用考虑链式调用的顺序性
 */
public class Validator<T> {

    protected final T value;

    protected final Collection<Predicate<? super T>> wrappers = new ArrayList<>();

    protected boolean require = false;

    /**
     * 传入的参数,任意一个,你可以通过继承此类 <br/>
     * 自己实现一套验证集
     *
     * @param value 值
     */
    public Validator(T value) {
        this.value = value;
    }

    /**
     * 传入的参数允许带有默认值
     *
     * @param value        值
     * @param defaultValue 默认值
     */
    public Validator(T value, T defaultValue) {
        this.value = Optional.ofNullable(value).orElse(defaultValue);
    }

    /**
     * 自定义条件验证
     *
     * @param wrapper 条件
     */
    public Validator<T> wrapper(Predicate<? super T> wrapper) {
        this.wrappers.add(wrapper);
        return this;
    }

    /**
     * 设置 value 可以为 null
     */
    public Validator<T> required() {
        require = true;
        return this;
    }

    /**
     * 校验实现
     */
    public final void validate() {
        if (this.value == null) {
            isFalse(require, "Value can not be null.");
            return;
        }
        this.wrappers.forEach(it -> isTrue(it.test(value), "Wrappers failed."));
        validate0();
    }

    /**
     * 继承此类需实现的方法
     */
    protected void validate0() {
    }

    public T extract() {
        validate();
        return this.value;
    }

    public Optional<T> optional() {
        validate();
        return Optional.ofNullable(this.value);
    }

    /* --------------- 静态构造 --------------- */

    public static <T> Validator<T> of(T value) {
        return new Validator<>(value);
    }

    public static <T> Validator<T> of(T value, T defaultValue) {
        return new Validator<>(value, defaultValue);
    }

    /* --------------- 工具集 --------------- */

    public static void isTrue(boolean expression) {
        isTrue(expression, "Invalid Param");
    }

    public static void isTrue(boolean expression, String message) {
        if (expression) {
            return;
        }
        throw new IllegalArgumentException(message);
    }

    public static void isFalse(boolean expression) {
        isFalse(expression, "Invalid Param");
    }

    public static void isFalse(boolean expression, String message) {
        if (!expression) {
            return;
        }
        throw new IllegalArgumentException(message);
    }

    public static void notNull(Object object) {
        notNull(object, "Invalid Param");
    }

    public static void notNull(Object object, String message) {
        if (object != null) {
            return;
        }
        throw new IllegalArgumentException(message);
    }

}

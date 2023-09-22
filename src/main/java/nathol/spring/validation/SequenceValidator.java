package nathol.spring.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

import nathol.spring.validation.components.StringValidate;

/**
 * 字符串的校验抽象思路
 */
public abstract class SequenceValidator<T extends CharSequence> extends Validator<T> {

    protected Integer minLength;
    protected Integer maxLength;
    protected boolean notBlank = false;
    protected boolean notEmpty = false;
    protected String regex;
    protected final Collection<T> hasValues = new ArrayList<>();

    public SequenceValidator(T value) {
        super(value);
    }

    public SequenceValidator(T value, T defaultValue) {
        super(value, defaultValue);
    }

    /**
     * 设置 value.length() 的最小值
     * @param minLength 值
     */
    public SequenceValidator<T> minLength(int minLength) {
        this.minLength = minLength;
        return this;
    }

    /**
     * 设置 value.length() 的最大值
     */
    public SequenceValidator<T> maxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    /**
     * 设置 value.length() 的最大值和最小值
     * @param minLength 最小值
     * @param maxLength 最大值
     */
    public SequenceValidator<T> range(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        return this;
    }

    /**
     * 设置 value 不能为空格
     */
    public SequenceValidator<T> notBlank() {
        this.notBlank = true;
        return this;
    }

    /**
     * 设置 value 不能为空
     */
    public SequenceValidator<T> notEmpty() {
        this.notEmpty = true;
        return this;
    }

    /**
     * 设置 value 的正则匹配
     */
    public SequenceValidator<T> regex(String regex) {
        this.regex = regex;
        return this;
    }

    /**
     * 设置 value 是否包含在 hasValues 中
     */
    @SafeVarargs
    public final SequenceValidator<T> hasValue(T... values) {
        for (T value : values) {
            this.hasValues.add(value);
        }
        return this;
    }

    /**
     * 设置 value 是否包含在 hasValues 中
     */
    public SequenceValidator<T> hasValue(Collection<T> values) {
        this.hasValues.addAll(values);
        return this;
    }

    /* --------------- 链式编程 --------------- */

    @Override
    public SequenceValidator<T> wrapper(Predicate<? super T> wrapper) {
        super.wrapper(wrapper);
        return this;
    }

    @Override
    public SequenceValidator<T> required(boolean value) {
        super.required(value);
        return this;
    }

    /* --------------- 静态构造 --------------- */

    public static SequenceValidator<String> of(String value) {
        return new StringValidate(value);
    }

    public static SequenceValidator<String> of(String value, String defaultValue) {
        return new StringValidate(value, defaultValue);
    }

}

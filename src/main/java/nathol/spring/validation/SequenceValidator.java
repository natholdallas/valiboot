package nathol.spring.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

import nathol.spring.validation.components.StringValidate;
import nathol.spring.validation.err.InvalidException;

/**
 * 字符串的校验抽象思路
 */
public abstract class SequenceValidator<T extends CharSequence> extends Validator<T> {

    protected Integer minLength;
    protected Integer maxLength;
    protected boolean notBlank = false;
    protected boolean notEmpty = false;
    protected String regex;
    protected final Collection<T> enumrations = new ArrayList<>();

    /**
     * 传入参数, 参数类型必须为 CharSequence 的子类
     * @param value 值
     */
    public SequenceValidator(T value) {
        super(value);
    }

    /**
     * 传入的参数允许带有默认值
     * @param value 值
     * @param defaultValue 默认值
     */
    public SequenceValidator(T value, T defaultValue) {
        super(value, defaultValue);
    }

    /**
     * 参阅 {@link Validator}, 此方法重写只为了方便链式调用
     */
    @Override
    public SequenceValidator<T> wrapper(Predicate<? super T> wrapper) {
        super.wrapper(wrapper);
        return this;
    }

    /**
     * 参阅 {@link Validator}, 此方法重写只为了方便链式调用
     */
    @Override
    public SequenceValidator<T> nullable() {
        super.nullable();
        return this;
    }

    /**
     * 设置如果出错的则会抛出的异常对象
     * @param exception 参阅 {@link InvalidException}
     */
    public SequenceValidator<T> except(InvalidException exception) {
        super.except(exception);
        return this;
    }

    /**
     * 校验 value 字符长度的最小值, 如果不符合, 则抛出 {@link InvalidException}
     * @param minLength 值
     */
    public SequenceValidator<T> minLength(int minLength) {
        this.minLength = minLength;
        return this;
    }

    /**
     * 校验 value 字符长度的最大值, 如果不符合, 则抛出 {@link InvalidException}
     * @param maxLength 值
     */
    public SequenceValidator<T> maxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    /**
     * 指定 value 字符长度的最大值和最小值, 如果不符合, 则抛出 {@link InvalidException}
     * @param minLength 最小值
     * @param maxLength 最大值
     */
    public SequenceValidator<T> range(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        return this;
    }

    /**
     * value 不能为空格子
     */
    public SequenceValidator<T> notBlank() {
        this.notBlank = true;
        return this;
    }

    /**
     * value 不能为空
     */
    public SequenceValidator<T> notEmpty() {
        this.notEmpty = true;
        return this;
    }

    /**
     * 使用正则引擎校验参数
     * @param regex 正则表达式
     */
    public SequenceValidator<T> regex(String regex) {
        this.regex = regex;
        return this;
    }

    /**
     * 使 value 符合 enumration 中的其中一个值, 如果都没有则抛出异常
     * @param values 符合的数值
     */
    @SafeVarargs
    public final SequenceValidator<T> enumration(T...  values) {
        for (T value : values) {
            this.enumrations.add(value);
        }
        return this;
    }

    /**
     * 使 value 符合 enumration 中的其中一个值, 如果都没有则抛出异常
     * @param values 符合的数值
     */
    public SequenceValidator<T> enumration(Collection<T> values) {
        this.enumrations.addAll(values);
        return this;
    }

    /**
     * 快捷实例化方法
     * @param value 值
     */
    public static SequenceValidator<String> of(String value) {
        return new StringValidate(value);
    }

    /**
     * 快捷实例化方法
     * @param value 值
     * @param defaultValue 默认值
     */
    public static SequenceValidator<String> of(String value, String defaultValue) {
        return new StringValidate(value, defaultValue);
    }

}

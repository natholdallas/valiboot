package nathol.spring.validation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

import nathol.spring.validation.components.BigDecimalValidate;
import nathol.spring.validation.components.DoubleValidate;
import nathol.spring.validation.components.FloatValidate;
import nathol.spring.validation.components.IntegerValidate;
import nathol.spring.validation.components.LongValidate;

public abstract class NumberValidator<T extends Number> extends Validator<T> {

    protected T min;
    protected T max;
    protected Collection<T> notInValues;
    protected Collection<T> hasValues;

    public NumberValidator(T value) {
        super(value);
    }

    public NumberValidator(T value, T defaultValue) {
        super(value, defaultValue);
    }

    /**
     * 设置 value 最小值
     */
    public NumberValidator<T> min(T min) {
        this.min = min;
        return this;
    }

    /**
     * 设置 value 最大值
     */
    public NumberValidator<T> max(T max) {
        this.max = max;
        return this;
    }

    /**
     * 设置 value 最大值和最小值
     *
     * @param min 最小值
     * @param max 最大值
     */
    public NumberValidator<T> range(T min, T max) {
        this.min = min;
        this.max = max;
        return this;
    }

    /**
     * 禁止 value 等于 ....值
     */
    @SafeVarargs
    public final NumberValidator<T> notIn(T... values) {
        if (this.notInValues == null) {
            this.notInValues = new ArrayList<>(values.length);
        }
        for (T value : values) {
            this.notInValues.add(value);
        }
        return this;
    }

    public NumberValidator<T> notIn(Collection<T> values) {
        if (this.notInValues == null) {
            this.notInValues = new ArrayList<>(values.size());
        }
        this.notInValues.addAll(values);
        return this;
    }

    /**
     * 设置 value 是否包含在 hasValues 中
     */
    @SafeVarargs
    public final NumberValidator<T> hasValue(T... values) {
        if (this.hasValues == null) {
            this.hasValues = new ArrayList<>(values.length);
        }
        for (T value : values) {
            this.hasValues.add(value);
        }
        return this;
    }

    public NumberValidator<T> hasValue(Collection<T> values) {
        if (this.hasValues == null) {
            this.hasValues = new ArrayList<>(values.size());
        }
        this.hasValues.addAll(values);
        return this;
    }

    /* --------------- 链式编程 --------------- */

    @Override
    public NumberValidator<T> wrapper(Predicate<? super T> wrapper) {
        super.wrapper(wrapper);
        return this;
    }

    @Override
    public NumberValidator<T> required(boolean value) {
        super.required(value);
        return this;
    }

    /* --------------- 静态构造 --------------- */

    public static NumberValidator<Integer> of(Integer value) {
        return new IntegerValidate(value);
    }

    public static NumberValidator<Integer> of(Integer value, Integer defaultValue) {
        return new IntegerValidate(value, defaultValue);
    }

    public static NumberValidator<Long> of(Long value) {
        return new LongValidate(value);
    }

    public static NumberValidator<Long> of(Long value, Long defaultValue) {
        return new LongValidate(value, defaultValue);
    }

    public static NumberValidator<Float> of(Float value) {
        return new FloatValidate(value);
    }

    public static NumberValidator<Float> of(Float value, Float defualtValue) {
        return new FloatValidate(value, defualtValue);
    }

    public static NumberValidator<Double> of(Double value) {
        return new DoubleValidate(value);
    }

    public static NumberValidator<Double> of(Double value, Double defaultValue) {
        return new DoubleValidate(value, defaultValue);
    }

    public static NumberValidator<BigDecimal> of(BigDecimal value) {
        return new BigDecimalValidate(value);
    }

    public static NumberValidator<BigDecimal> of(BigDecimal value, BigDecimal defaultValue) {
        return new BigDecimalValidate(value, defaultValue);
    }

}

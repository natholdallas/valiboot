package github.natholdallas.valiboot;

import github.natholdallas.valiboot.core.InvalidMsg;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class NumberValidator<T extends Number> extends Validator<T> {

    private T min;
    private T max;
    private final Collection<T> forbidValues = new ArrayList<>();
    private final Collection<T> hasValues = new ArrayList<>();

    public int compare(T a, T b) {
        if (a instanceof Integer) {
            return ((Integer) a).compareTo((Integer) b);
        } else if (a instanceof Long) {
            return ((Long) a).compareTo((Long) b);
        } else if (a instanceof Float) {
            return ((Float) a).compareTo((Float) b);
        } else if (a instanceof Double) {
            return ((Double) a).compareTo((Double) b);
        } else if (a instanceof BigDecimal) {
            return ((BigDecimal) a).compareTo((BigDecimal) b);
        } else if (a instanceof BigInteger) {
            return ((BigInteger) a).compareTo((BigInteger) b);
        } else if (a instanceof Short) {
            return ((Short) a).compareTo((Short) b);
        } else if (a instanceof Byte) {
            return ((Byte) a).compareTo((Byte) b);
        }
        throw new IllegalArgumentException();
    }

    /**
     * 设置 {@code value} 的最小值
     */
    public NumberValidator<T> min(@NotNull T min) {
        this.min = min;
        return this;
    }

    /**
     * 设置 {@code value} 的最大值
     */
    public NumberValidator<T> max(@NotNull T max) {
        this.max = max;
        return this;
    }

    /**
     * 枚举 {@code value} 的禁止值
     */
    @SafeVarargs
    public final NumberValidator<T> forbidValue(T... values) {
        this.forbidValues.addAll(Arrays.asList(values));
        return this;
    }

    /**
     * 枚举 {@code value} 的值
     */
    @SafeVarargs
    public final NumberValidator<T> hasValue(T... values) {
        this.hasValues.addAll(Arrays.asList(values));
        return this;
    }

    @Override
    public @NotNull List<InvalidMsg> validate(T value) {
        List<InvalidMsg> list = super.validate(value);
        validator:
        {
            if (value == null) {
                break validator;
            }
            if (min != null) {
                if (compare(value, min) <= 0) {
                    list.add(new InvalidMsg("Min", "value 不能小于 " + min));
                }
            }
            if (max != null) {
                if (compare(value, max) >= 0) {
                    list.add(new InvalidMsg("Max", "value 不能大于 " + max));
                }
            }
            if (!forbidValues.isEmpty()) {
                if (forbidValues.stream().anyMatch(it -> compare(value, it) == 0)) {
                    list.add(new InvalidMsg("NotIn", "value 不能在 " + forbidValues + " 中"));
                }
            }
            if (!hasValues.isEmpty()) {
                if (hasValues.stream().noneMatch(it -> compare(value, it) == 0)) {
                    list.add(new InvalidMsg("HasValue", "value 必须在 " + hasValues + " 中"));
                }
            }
        }
        return list;
    }

    /* --------------- 链式编程 --------------- */

    @Override
    public NumberValidator<T> validator(@NotNull Function<T, InvalidMsg> validator) {
        super.validator(validator);
        return this;
    }

    @Override
    public NumberValidator<T> required(boolean value) {
        super.required(value);
        return this;
    }

}

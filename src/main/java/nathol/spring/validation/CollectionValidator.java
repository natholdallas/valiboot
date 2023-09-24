package nathol.spring.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class CollectionValidator<T> extends Validator<Collection<T>> {

    private Integer minSize;
    private Integer maxSize;
    private boolean notEmpty;
    private Collection<Object> hasElements;

    public CollectionValidator(Collection<T> value) {
        super(value);
    }

    public CollectionValidator(Collection<T> value, Collection<T> defaultValue) {
        super(value, defaultValue);
    }

    /**
     * 设置 value 元素不能为空
     */
    public CollectionValidator<T> notEmpty() {
        this.notEmpty = true;
        return this;
    }

    /**
     * 校验 value.size() 最小值
     */
    public CollectionValidator<T> minSize(int minSize) {
        this.minSize = minSize;
        return this;
    }

    /**
     * 校验 value.size() 最大值
     */
    public CollectionValidator<T> maxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    /**
     * 设置 value.size() 元素最大值和最小值
     *
     * @param minSize 最小值
     * @param maxSize 最大值
     */
    public CollectionValidator<T> range(int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        return this;
    }

    /**
     * 设置 value 的元素是否包含在 hasElements 中
     */
    public CollectionValidator<T> hasElement(Object... values) {
        if (this.hasElements == null) {
            this.hasElements = new ArrayList<>(values.length);
        }
        for (Object value : values) {
            this.hasElements.add(value);
        }
        return this;
    }

    public CollectionValidator<T> hasElement(Collection<Object> values) {
        if (this.hasElements == null) {
            this.hasElements = new ArrayList<>(values.size());
        }
        this.hasElements.addAll(values);
        return this;
    }

    @Override
    protected void validate0() {
        size: {
            if (minSize != null && maxSize != null) {
                isTrue(value.size() >= minSize && value.size() <= maxSize,
                        "Value can not be less than min or greater than max.");
                break size;
            }
            if (minSize != null) {
                isTrue(value.size() >= minSize, "Value can not be less than min.");
            }
            if (maxSize != null) {
                isTrue(value.size() <= maxSize, "Value can not be greater than max.");
            }
        }
        if (notEmpty) {
            isFalse(value.isEmpty(), "Value can not be empty.");
        }
        if (hasElements != null) {
            boolean hasElement = false;
            for (Object it : hasElements) {
                if (value.contains(it)) {
                    hasElement = true;
                    break;
                }
            }
            isTrue(hasElement, "Value must be in " + hasElements + " .");
        }
    }

    /* --------------- 链式编程 --------------- */

    @Override
    public CollectionValidator<T> wrapper(Predicate<? super Collection<T>> wrapper) {
        super.wrapper(wrapper);
        return this;
    }

    @Override
    public CollectionValidator<T> required(boolean value) {
        super.required(value);
        return this;
    }

    /* --------------- 静态构造 --------------- */

    public static <T> CollectionValidator<T> of(Collection<T> value) {
        return new CollectionValidator<>(value);
    }

    public static <T> CollectionValidator<T> of(Collection<T> value, Collection<T> defaultValue) {
        return new CollectionValidator<>(value, defaultValue);
    }

}

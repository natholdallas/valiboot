package github.natholdallas.valiboot;

import github.natholdallas.valiboot.core.InvalidMsg;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class CollectionValidator<T> extends Validator<Collection<T>> {

    private Integer minSize;
    private Integer maxSize;
    private boolean notEmpty;

    /**
     * 设置 {@code value} 不能为空
     */
    public CollectionValidator<T> notEmpty() {
        this.notEmpty = true;
        return this;
    }

    /**
     * 设置 {@code value.size()} 的最小值
     */
    public CollectionValidator<T> minSize(int minSize) {
        this.minSize = minSize;
        return this;
    }

    /**
     * 设置 {@code value.size()} 的最大值
     */
    public CollectionValidator<T> maxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    @Override
    public @NotNull List<InvalidMsg> validate(Collection<T> value) {
        List<InvalidMsg> list = super.validate(value);
        validator:
        {
            if (value == null) {
                break validator;
            }
            if (minSize != null) {
                if (value.size() <= minSize) {
                    list.add(new InvalidMsg("MinSize", "数组长度不能小于 " + minSize));
                }
            }
            if (maxSize != null) {
                if (value.size() >= maxSize) {
                    list.add(new InvalidMsg("MaxSize", "数组长度不能大于 " + maxSize));
                }
            }
            if (notEmpty && !value.isEmpty()) {
                list.add(new InvalidMsg("NotEmpty", "数组不能为空"));
            }
        }
        return list;
    }

    /* --------------- 链式编程 --------------- */

    @Override
    public CollectionValidator<T> validator(@NotNull Function<Collection<T>, InvalidMsg> validator) {
        super.validator(validator);
        return this;
    }

    @Override
    public CollectionValidator<T> required(boolean value) {
        super.required(value);
        return this;
    }

}

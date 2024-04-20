package github.natholdallas.valiboot;

import github.natholdallas.valiboot.core.InvalidMsg;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

public class StringValidator extends Validator<String> {

    private Integer minLength;
    private Integer maxLength;
    private boolean notBlank;
    private boolean notEmpty;
    private String regex;
    private final Collection<String> enumrations = new ArrayList<>();

    /**
     * 设置 {@code value.length()} 的最小值
     */
    public StringValidator minLength(int minLength) {
        this.minLength = minLength;
        return this;
    }

    /**
     * 设置 {@code value.length()} 的最大值
     */
    public StringValidator maxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    /**
     * 设置 {@code value} 不能为空白字符
     */
    public StringValidator notBlank() {
        this.notBlank = true;
        return this;
    }

    /**
     * 设置 {@code value} 不能为空
     */
    public StringValidator notEmpty() {
        this.notEmpty = true;
        return this;
    }

    /**
     * 正则表达式
     */
    public StringValidator regex(@Nullable String regex) {
        this.regex = regex;
        return this;
    }

    /**
     * 枚举 {@code value} 的值
     */
    public StringValidator enumration(@NotNull String... enumration) {
        this.enumrations.addAll(Arrays.asList(enumration));
        return this;
    }

    @Override
    public @NotNull List<InvalidMsg> validate(@Nullable String value) {
        List<InvalidMsg> list = super.validate(value);
        valid:
        {
            if (value == null) {
                break valid;
            }
            if (minLength != null) {
                if (value.length() <= minLength) {
                    list.add(new InvalidMsg("MinLength", "value 不能小于 " + minLength));
                }
            }
            if (maxLength != null) {
                if (value.length() >= maxLength) {
                    list.add(new InvalidMsg("MaxLength", "value 不能大于 " + maxLength));
                }
            }
            if (notEmpty && value.isEmpty()) {
                list.add(new InvalidMsg("NotEmpty", "value 不能为空"));
            }
            if (notBlank && value.isBlank()) {
                list.add(new InvalidMsg("NotBlank", "value 不能为空白字符"));
            }
            if (regex != null) {
                if (!Pattern.matches(regex, value)) {
                    list.add(new InvalidMsg("Regex", "value 与正则表达式不匹配 " + regex));
                }
            }
            if (!enumrations.isEmpty()) {
                if (enumrations.stream().noneMatch(value::equals)) {
                    list.add(new InvalidMsg("Enumration", "value 必须在 " + enumrations + " 中"));
                }
            }
        }
        return list;
    }

    /* --------------- 链式编程 --------------- */

    @Override
    public StringValidator validator(@NotNull Function<String, InvalidMsg> validator) {
        super.validator(validator);
        return this;
    }

    @Override
    public StringValidator required(boolean value) {
        super.required(value);
        return this;
    }

}

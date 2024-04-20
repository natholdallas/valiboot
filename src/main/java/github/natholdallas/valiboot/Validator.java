package github.natholdallas.valiboot;

import github.natholdallas.valiboot.core.InvalidException;
import github.natholdallas.valiboot.core.InvalidMsg;
import github.natholdallas.valiboot.core.ValidResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class Validator<T> {

    private final Collection<Function<T, InvalidMsg>> validators = new ArrayList<>();
    private boolean required = true;

    public Validator<T> validator(@NotNull Function<T, InvalidMsg> validator) {
        this.validators.add(validator);
        return this;
    }

    public Validator<T> required(boolean required) {
        this.required = required;
        return this;
    }

    public @NotNull List<InvalidMsg> validate(T value) {
        List<InvalidMsg> list = new ArrayList<>();
        valid:
        {
            if (!required && value == null) {
                break valid;
            }
            if (value == null) {
                list.add(new InvalidMsg("Required", "value 不能为 null"));
                break valid;
            }
            for (Function<T, InvalidMsg> i : validators) {
                InvalidMsg msg = i.apply(value);
                if (msg != null) {
                    list.add(msg);
                }
            }
        }
        return list;
    }

    /* --------------- 基于 validate() 封装的方法 --------------- */

    public @Nullable T get(T value) {
        List<InvalidMsg> list = validate(value);
        if (!list.isEmpty()) {
            throw new InvalidException(value, list);
        }
        return value;
    }

    public boolean isValid(T value) {
        return validate(value).isEmpty();
    }

    public @NotNull ValidResult<T> result(T value) {
        return new ValidResult<>(value, validate(value));
    }

    public void orElseThrow(T value) throws InvalidException {
        List<InvalidMsg> msgs = validate(value);
        if (!msgs.isEmpty()) {
            throw new InvalidException(value, msgs);
        }
    }

}

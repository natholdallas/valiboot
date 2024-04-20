package github.natholdallas.valiboot.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ValidResult<T> {

    @Nullable
    private final T value;

    private final int count;

    private final boolean isValid;

    @NotNull
    private final List<InvalidMsg> invalidMsgs;

    public ValidResult(@Nullable T value, @NotNull List<InvalidMsg> invalidMsgs) {
        this.value = value;
        this.invalidMsgs = invalidMsgs;
        this.count = invalidMsgs.size();
        this.isValid = invalidMsgs.isEmpty();
    }

    public T orElse(T other) {
        return isValid ? value : other;
    }

    public T orElseGet(Supplier<? extends T> other) {
        return isValid ? value : other.get();
    }

    public T orElseThrow() throws InvalidException {
        if (!isValid) throw new InvalidException(value, invalidMsgs);
        return value;
    }

    public T orElseThrow(BiFunction<T, List<InvalidMsg>, Throwable> func) throws Throwable {
        if (!isValid) throw func.apply(value, invalidMsgs);
        return value;
    }

    public @Nullable T getValue() {
        return value;
    }

    public @NotNull List<InvalidMsg> getInvalidMsgs() {
        return invalidMsgs;
    }

    public int getCount() {
        return count;
    }

    public boolean isValid() {
        return isValid;
    }
}

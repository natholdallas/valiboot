package github.natholdallas.valiboot.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InvalidException extends IllegalArgumentException {

    @Nullable
    private final Object value;

    @NotNull
    private final List<InvalidMsg> invalidMsgs;

    public InvalidException(@Nullable Object value, @NotNull List<InvalidMsg> invalidMsgs) {
        super();
        this.value = value;
        this.invalidMsgs = invalidMsgs;
    }

    public @Nullable Object getValue() {
        return value;
    }

    public @NotNull List<InvalidMsg> getInvalidMsgs() {
        return invalidMsgs;
    }

}

package github.natholdallas.valiboot.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record InvalidMsg(@NotNull String title, @Nullable String message) {
}

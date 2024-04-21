package com.github.natholdallas.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record InvalidMsg(@NotNull String title, @Nullable String message) {
}

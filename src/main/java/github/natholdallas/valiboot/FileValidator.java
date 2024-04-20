package github.natholdallas.valiboot;

import github.natholdallas.valiboot.core.InvalidMsg;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class FileValidator<T extends File> extends Validator<T> {

    private Long minSize;
    private Long maxSize;
    private boolean isFile;
    private boolean isDirectory;
    private boolean isAbsolute;
    private boolean isHidden;
    private boolean canExecute;
    private boolean canWrite;
    private boolean canRead;
    private final Collection<String> suffixs = new ArrayList<>();

    /**
     * 设置 {@code value} 的最小值 (单位: KB)
     */
    public FileValidator<T> minSize(long minSize) {
        this.minSize = minSize;
        return this;
    }

    /**
     * 设置 {@code value.length()} 的最大值 (单位: KB)
     */
    public FileValidator<T> maxSize(long maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    /**
     * 设置 {@code value} 的类型必须是文件
     */
    public FileValidator<T> isFile() {
        this.isFile = true;
        return this;
    }

    /**
     * 设置 {@code value} 的类型必须是目录
     */
    public FileValidator<T> isDirectory() {
        this.isDirectory = true;
        return this;
    }

    /**
     * 设置 {@code value} 必须可以执行
     */
    public FileValidator<T> canExecute() {
        this.canExecute = true;
        return this;
    }

    /**
     * 设置 {@code value} 必须可以写入
     */
    public FileValidator<T> canWrite() {
        this.canWrite = true;
        return this;
    }

    /**
     * 设置 {@code value} 必须可以读取
     */
    public FileValidator<T> canRead() {
        this.canRead = true;
        return this;
    }

    /**
     * 枚举 {@code value.getName().lastIndexOf('.')} 的值
     */
    public FileValidator<T> hasSuffix(@NotNull String... values) {
        this.suffixs.addAll(Arrays.asList(values));
        return this;
    }

    @Override
    public @NotNull List<InvalidMsg> validate(T value) {
        List<InvalidMsg> list = super.validate(value);
        valid:
        {
            if (value == null) {
                break valid;
            }
            if (minSize != null) {
                if (value.length() <= minSize) {
                    list.add(new InvalidMsg("MinSize", "File 大小不能小于 " + minSize));
                }
            }
            if (maxSize != null) {
                if (value.length() >= maxSize) {
                    list.add(new InvalidMsg("MaxSize", "File 大小不能大于 " + maxSize));
                }
            }
            if (isFile && !value.isFile()) {
                list.add(new InvalidMsg("IsFile", "File 类型不是一个文件"));
            }
            if (isDirectory && !value.isDirectory()) {
                list.add(new InvalidMsg("IsDirectory", "File 类型不是一个目录"));
            }
            if (isAbsolute && !value.isAbsolute()) {
                list.add(new InvalidMsg("IsAbsolute", "File 必须为绝对路径"));
            }
            if (isHidden && !value.isHidden()) {
                list.add(new InvalidMsg("IsHidden", "File 必须为隐藏文件"));
            }
            if (canExecute && !value.canExecute()) {
                list.add(new InvalidMsg("CanExecute", "File 必须可执行"));
            }
            if (canWrite && !value.canWrite()) {
                list.add(new InvalidMsg("CanWrite", "File 必须可写入"));
            }
            if (canRead && !value.canRead()) {
                list.add(new InvalidMsg("CanRead", "File 必须可读取"));
            }
            if (!suffixs.isEmpty()) {
                int index = value.getName().lastIndexOf('.');
                if (index < 0) {
                    list.add(new InvalidMsg("NoSuffix", "File 没有后缀名"));
                }
                if (suffixs.stream().noneMatch(value.getName().substring(index)::equals)) {
                    list.add(new InvalidMsg("Suffix", "File 的后缀名必须在 " + suffixs + " 中"));
                }
            }
        }
        return list;
    }

    /* --------------- 链式编程 --------------- */

    @Override
    public FileValidator<T> validator(@NotNull Function<T, InvalidMsg> validator) {
        super.validator(validator);
        return this;
    }

    @Override
    public FileValidator<T> required(boolean value) {
        super.required(value);
        return this;
    }

}

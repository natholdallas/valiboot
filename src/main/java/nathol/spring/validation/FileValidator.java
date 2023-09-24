package nathol.spring.validation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class FileValidator extends Validator<File> {

    private Long minSize;
    private Long maxSize;
    private boolean file;
    private boolean directory;
    private boolean canExecute;
    private boolean canWrite;
    private boolean canRead;
    private Collection<String> suffixs;

    public FileValidator(File value) {
        super(value);
    }

    public FileValidator(File value, File defaultValue) {
        super(value, defaultValue);
    }

    /**
     * 设置文件最小值 (单位: KB)
     */
    public FileValidator minSize(Long minSize) {
        this.minSize = minSize;
        return this;
    }

    /**
     * 设置文件最大值 (单位: KB)
     */
    public FileValidator maxSize(Long maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    /**
     * 设置文件最大值和最小值
     *
     * @param minSize 最小值
     * @param maxSize 最大值
     */
    public FileValidator range(Long minSize, Long maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        return this;
    }

    /**
     * 设置文件类型必须是文件
     */
    public FileValidator mustFile() {
        this.file = true;
        return this;
    }

    /**
     * 设置文件类型必须是目录
     */
    public FileValidator mustDirectory() {
        this.directory = true;
        return this;
    }

    /**
     * 设置文件必须可以执行
     */
    public FileValidator canExecute() {
        this.canExecute = true;
        return this;
    }

    /**
     * 设置文件必须可以写入
     */
    public FileValidator canWrite() {
        this.canWrite = true;
        return this;
    }

    /**
     * 设置文件必须可以读取
     */
    public FileValidator canRead() {
        this.canRead = true;
        return this;
    }

    /**
     * 设置文件的后缀必须为 hasSuffixs 中的其中一个值
     */
    @SafeVarargs
    public final FileValidator hasSuffix(String... values) {
        if (this.suffixs != null) {
            this.suffixs = new ArrayList<>(values.length);
        }
        for (String value : values) {
            this.suffixs.add(value);
        }
        return this;
    }

    public FileValidator hasSuffix(Collection<String> values) {
        if (this.suffixs != null) {
            this.suffixs = new ArrayList<>(suffixs.size());
        }
        this.suffixs.addAll(values);
        return this;
    }

    @Override
    public void validate0() {
        size: {
            if (minSize != null && maxSize != null) {
                isTrue(value.length() >= minSize && value.length() <= maxSize,
                        "Value can not be less than min or greater than max.");
                break size;
            }
            if (minSize != null) {
                isTrue(value.length() >= minSize, "Value can not be less than min.");
            }
            if (maxSize != null) {
                isTrue(value.length() <= maxSize, "Value can not be greater than max.");
            }
        }
        if (file) {
            isTrue(value.isFile(), "Value must be a file.");
        }
        if (directory) {
            isTrue(value.isDirectory(), "Value must be a directory.");
        }
        if (canExecute) {
            isTrue(value.canExecute(), "Value must be executable.");
        }
        if (canWrite) {
            isTrue(value.canWrite(), "Value must be writable.");
        }
        if (canRead) {
            isTrue(value.canRead(), "Value must be readable.");
        }
        if (suffixs != null) {
            boolean hasSuffix = false;
            suffix: {
                int index = value.getName().lastIndexOf('.');
                if (index < 0) {
                    break suffix;
                }
                for (String suffix : suffixs) {
                    if (value.getName().substring(index).equals(suffix)) {
                        hasSuffix = true;
                        break;
                    }
                }
            }
            isTrue(hasSuffix, "Value's suffix must be in " + suffixs + ".");
        }
    }

    /* --------------- 链式编程 --------------- */

    @Override
    public FileValidator wrapper(Predicate<? super File> wrapper) {
        super.wrapper(wrapper);
        return this;
    }

    @Override
    public FileValidator required(boolean value) {
        super.required(value);
        return this;
    }

    /* --------------- 静态构造 --------------- */

    public static FileValidator of(File value) {
        return new FileValidator(value);
    }

    public static FileValidator of(File value, File defaultValue) {
        return new FileValidator(value, defaultValue);
    }

}

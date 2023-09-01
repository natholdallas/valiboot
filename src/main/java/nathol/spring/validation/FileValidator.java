package nathol.spring.validation;

import java.io.File;

// TODO 完成文件校验
public class FileValidator extends Validator<File> {

    public FileValidator(File value) {
        super(value);
    }

    public FileValidator(File value, File defaultValue) {
        super(value, defaultValue);
    }

}

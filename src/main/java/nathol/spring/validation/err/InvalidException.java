package nathol.spring.validation.err;

/**
 * 非常简单的异常类, 你可以在这个类中抛出信息, 推荐使用 Spring 的全局异常捕获器
 * 这样就可以单独对一个类进行处理
 */
public class InvalidException extends RuntimeException {

    public InvalidException() {
    }

    public InvalidException(String message) {
        super(message);
    }

    public InvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidException(Throwable cause) {
        super(cause);
    }

}

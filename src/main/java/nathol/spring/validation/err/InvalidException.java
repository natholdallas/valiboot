package nathol.spring.validation.err;

/**
 * 非常简单的异常类, 你可以在这个类中抛出信息, 推荐使用 Spring 的全局异常捕获器
 * 这样就可以单独对一个类进行处理
 */
public class InvalidException extends RuntimeException {

    private Object data;

    /**
     * 无参构造器
     */
    public InvalidException() {
    }

    /**
     * 带参构造器, 给 message 赋值,这样在抛出异常后可以通过 getMessage() 获取
     * @param message 异常信息
     */
    public InvalidException(String message) {
        super(message);
    }

    /**
     * 给 message, cause 赋值,这样在抛出异常后可以通过 getMessage() getCause() 获取
     * @param message 异常信息
     * @param cause 异常信息
     */
    public InvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 给 cause 赋值,这样在抛出异常后可以通过 getCause() 获取
     * @param cause 异常信息
     */
    public InvalidException(Throwable cause) {
        super(cause);
    }

    /**
     * 给 data 赋值, 通常用于 spring boot 中, 可序列化,统一接口
     */
    public InvalidException(Object data) {
        this.data = data;
    }

    /**
     * 获取 data 对象
     */
    public Object data() {
        return this.data;
    }

}

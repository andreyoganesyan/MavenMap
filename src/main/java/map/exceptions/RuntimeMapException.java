package map.exceptions;

/**
 * Created by andre_000 on 03-Mar-17.
 */
public class RuntimeMapException extends RuntimeException {
    public RuntimeMapException() {
        super();
    }

    public RuntimeMapException(String mes) {
        super(mes);
    }

    public RuntimeMapException(Throwable cause) {
        super(cause);
    }
}

package map.exceptions;

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

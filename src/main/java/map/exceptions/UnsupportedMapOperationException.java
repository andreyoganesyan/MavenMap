package map.exceptions;

/**
 * Created by andre_000 on 03-Mar-17.
 */
public class UnsupportedMapOperationException extends RuntimeMapException {
    public UnsupportedMapOperationException() {
    }

    public UnsupportedMapOperationException(String mes) {
        super(mes);
    }

    public UnsupportedMapOperationException(Throwable cause) {
        super(cause);
    }
}

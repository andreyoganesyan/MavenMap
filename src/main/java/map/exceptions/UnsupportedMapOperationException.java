package map.exceptions;

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

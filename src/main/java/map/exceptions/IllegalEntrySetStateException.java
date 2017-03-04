package map.exceptions;

public class IllegalEntrySetStateException extends RuntimeMapException {
    public IllegalEntrySetStateException() {
        super();
    }

    public IllegalEntrySetStateException(String mes) {
        super(mes);
    }

    public IllegalEntrySetStateException(Throwable cause) {
        super(cause);
    }
}

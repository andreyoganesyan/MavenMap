package map.exceptions;

/**
 * Created by andre_000 on 03-Mar-17.
 */
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

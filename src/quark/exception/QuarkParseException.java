package quark.exception;

import lombok.NonNull;

public class QuarkParseException extends RuntimeException {
    
    public QuarkParseException(@NonNull final String message) {
        super(message);
    }
    
    public QuarkParseException(@NonNull final String message, @NonNull final Exception e) {
        super(message, e);
    }
    
}

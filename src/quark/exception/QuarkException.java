package quark.exception;

import lombok.NonNull;

public class QuarkException extends RuntimeException {
    
    public QuarkException(@NonNull final String message) {
        super(message);
    }
    
    public QuarkException(@NonNull final String message, @NonNull final Exception e) {
        super(message, e);
    }
    
}

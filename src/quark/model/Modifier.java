package quark.model;

import lombok.NonNull;

public enum Modifier {
    
    PUBLIC,
    PRIVATE,
    STATIC,
    FINAL,
    GET,
    SET,
    NONNULL;
    
    public static boolean isModifier(@NonNull final String value) {
        try {
            Modifier.valueOf(value.toUpperCase());
            return true;
        } catch (final IllegalArgumentException e) {
            return false;
        }
    }
    
}

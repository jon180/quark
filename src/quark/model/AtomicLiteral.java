package quark.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import quark.exception.QuarkParseException;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public enum AtomicLiteral {
    
    STRING(Pattern.compile("(\".*\")|('.*')")),
    INTEGER(Pattern.compile("-?[0-9]+")),
    DECIMAL(Pattern.compile("-?[0-9]+\\.[0-9]+")),
    BOOLEAN(Pattern.compile("(true)|(false)")),
    NULL(Pattern.compile("null"));
    
    @NonNull private final Pattern pattern;
    
    public static boolean isAtomicLiteral(@NonNull final String value) {
        for (final AtomicLiteral literal : AtomicLiteral.values()) {
            if (literal.pattern.matcher(value).matches()) {
                return true;
            }
        }
        return false;
    }
    
    public static AtomicLiteral getType(@NonNull final String value) {
        for (final AtomicLiteral literal : AtomicLiteral.values()) {
            if (literal.pattern.matcher(value).matches()) {
                return literal;
            }
        }
        throw new QuarkParseException("unrecognized literal type");
    }
    
}

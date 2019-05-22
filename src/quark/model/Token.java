package quark.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class Token {
    
    @NonNull private final TokenType type;
    private final String value;
    
    public boolean is(final TokenType type) {
        return this.type.equals(type);
    }
    
    public boolean is(final Operator value) {
        return this.type.equals(TokenType.OPERATOR) && this.value.charAt(0) == value.getSymbol();
    }
    
    public boolean is(final TokenType type, final String value) {
        return this.type.equals(type) && this.value.equals(value);
    }
    
}

package quark.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Operator {
    
    OPEN_BRACKET('['),
    CLOSE_BRACKET(']'),
    OPEN_PARENTHESIS('('),
    CLOSE_PARENTHESIS(')'),
    OPEN_SET_BRACE('{'),
    CLOSE_SET_BRACE('}'),
    SEPARATOR('|'),
    ASSIGNMENT(':');
    
    private static final Set<Character> SYMBOL_SET;
    
    private final char symbol;
    
    static {
        SYMBOL_SET = Arrays.stream(Operator.values())
                .map(o -> o.symbol)
                .collect(Collectors.toSet());
    }
    
    public static boolean isOperator(final char c) {
        return SYMBOL_SET.contains(c);
    }
    
}

package quark.component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import quark.model.AtomicLiteral;
import quark.model.Modifier;
import quark.model.Operator;
import quark.model.Token;
import quark.model.TokenType;

import java.util.ArrayDeque;
import java.util.Queue;

@RequiredArgsConstructor
public class QuarkLexer {

    @NonNull private final String code;
    private StringBuilder sb = new StringBuilder();
    private final Queue<Token> tokenQueue = new ArrayDeque<>();
    
    public Queue<Token> tokenize() {
        boolean isInComment = false;
        char commentOpener = ' ';
        boolean isInStringLiteral = false;
        char stringOpener = ' ';
        char previous = ' ';
        char c = ' ';
        
        for (int index = 0; index < code.length(); index++) {
            previous = c;
            c = code.charAt(index);
            
            if (isInComment) {
                if (commentOpener == '#' && c == '\n') {
                    isInComment = false;
                } else if (commentOpener == '*' && previous == '*' && c == '/') {
                    isInComment = false;
                }
            } else if (isInStringLiteral) {
                sb.append(c);
                if (c == stringOpener) {
                    isInStringLiteral = false;
                    buildToken();
                }
            } else if (Operator.isOperator(c)) {
                buildToken();
                tokenQueue.offer(new Token(TokenType.OPERATOR, String.valueOf(c)));
            } else {
                
                switch (c) {
                    case '\t':
                    case ' ':
                        buildToken();
                        break;
                    case ';':
                    case '\n':
                        buildToken();
                        if (c != '\n' || (previous != '\\' && !ignoreNewline())) {
                            tokenQueue.offer(new Token(TokenType.STATEMENT_BREAK, null));
                        }
                        break;
                    case '"':
                    case '\'':
                        isInStringLiteral = true;
                        stringOpener = c;
                        sb.append(c);
                        break;
                    case '#':
                        buildToken();
                        isInComment = true;
                        commentOpener = c;
                        break;
                    case '*':
                        if (previous == '/') {
                            if (sb.length() > 0) {
                                sb.deleteCharAt(sb.length() - 1);
                            }
                            buildToken();
                            isInComment = true;
                            commentOpener = c;
                        } else {
                            sb.append(c);
                        }
                        break;
                    default:
                        sb.append(c);
                        break;
                }
                
            }
        }
        
        return tokenQueue;
    }
    
    private void buildToken() {
        if (sb.length() == 0) {
            return;
        }
        final String value = sb.toString();
        
        TokenType type;
        if (Modifier.isModifier(value)) {
            type = TokenType.MODIFIER;
        } else if (AtomicLiteral.isAtomicLiteral(value)) {
            type = TokenType.ATOMIC_LITERAL;
        } else {
            type = TokenType.SYMBOL;
        }
        
        tokenQueue.offer(new Token(type, value));
        sb = new StringBuilder();
    }
    
    private boolean ignoreNewline() {
        final Token previousToken = ((ArrayDeque<Token>) tokenQueue).getLast();
        if (previousToken.getType().equals(TokenType.OPERATOR)) {
            final char symbol = previousToken.getValue().charAt(0);
            return symbol == Operator.OPEN_BRACKET.getSymbol() || symbol == Operator.OPEN_PARENTHESIS.getSymbol()
                    || symbol == Operator.OPEN_SET_BRACE.getSymbol();
        } else {
            return previousToken.getType().equals(TokenType.STATEMENT_BREAK);
        }
    }
    
}

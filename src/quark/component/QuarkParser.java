package quark.component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import quark.exception.QuarkParseException;
import quark.model.AtomicLiteral;
import quark.model.MessageSignature;
import quark.model.MessageType;
import quark.model.Modifier;
import quark.model.Operator;
import quark.model.Token;
import quark.model.TokenType;
import quark.model.syntaxtree.AssignmentNode;
import quark.model.syntaxtree.AtomicLiteralNode;
import quark.model.syntaxtree.BlockNode;
import quark.model.syntaxtree.ClassNode;
import quark.model.syntaxtree.ExpressionNode;
import quark.model.syntaxtree.StatementSetNode;
import quark.model.syntaxtree.SymbolNode;
import quark.model.syntaxtree.SyntaxTreeNode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

@RequiredArgsConstructor
public class QuarkParser {
    
    @NonNull private final Queue<Token> tokenQueue;
    
    public SyntaxTreeNode parse() {
        return parseStatementSet(null);
    }
    
    private SyntaxTreeNode parseStatementSet(final Operator close) {
        if (tokenQueue.isEmpty()) {
            return null;
        }
        
        final List<SyntaxTreeNode> statements = new LinkedList<>();
        while (!tokenQueue.isEmpty()) {
            if (isNext(close) || isNext(Operator.SEPARATOR)) {
                break;
            }
            final SyntaxTreeNode statement = parseExpression(close);
            if (statement != null) {
                statements.add(statement);
            }
        }
        
        if (statements.isEmpty()) {
            return null;
        } else if (statements.size() == 1) {
            return statements.get(0);
        } else {
            return new StatementSetNode(statements);
        }
    }
    
    private SyntaxTreeNode parseExpression(final Operator close) {
        if (isNext(TokenType.MODIFIER)) {
            return parseAssignmentWithModifiers(close);
        }
        
        final List<SyntaxTreeNode> elements = new LinkedList<>();
        while (!tokenQueue.isEmpty()) {
            if (close != null && isNext(close)) {
                break;
            } else if (isNext(Operator.SEPARATOR)) {
                break;
            }
            
            final Token current = tokenQueue.remove();
            if (current.is(TokenType.STATEMENT_BREAK)) {
                break;
            } else if (current.is(Operator.ASSIGNMENT)) {
                if (elements.isEmpty()) {
                    throw new QuarkParseException("expressions may not begin with colon");
                } else if (!(elements.get(elements.size() - 1) instanceof SymbolNode)) {
                    throw new QuarkParseException("a colon may only follow a symbol");
                } else if (elements.size() == 1) {
                    final SymbolNode target = (SymbolNode) elements.get(0);
                    return new AssignmentNode(target, parseExpression(close), null);
                } else {
                    ((SymbolNode) elements.get(elements.size() - 1)).setKeyword(true);
                }
            } else if (current.is(Operator.OPEN_BRACKET)) {
                elements.add(parseBlock(Operator.CLOSE_BRACKET));
            } else if (current.is(Operator.OPEN_PARENTHESIS)) {
                elements.add(parseExpression(close));
            } else if (current.is(Operator.OPEN_SET_BRACE)) {
                elements.add(parseClass());
            } else if (current.is(TokenType.SYMBOL)) {
                elements.add(new SymbolNode(current.getValue()));
            } else if (current.is(TokenType.ATOMIC_LITERAL)) {
                elements.add(new AtomicLiteralNode(current.getValue(), AtomicLiteral.getType(current.getValue())));
            } else {
                throw new QuarkParseException("unexpected token type: " + current.toString());
            }
        }
        
        if (elements.isEmpty()) {
            return null;
        } else if (elements.size() == 1) {
            return elements.get(0);
        } else {
            return new ExpressionNode(elements);
        }
    }
    
    private SyntaxTreeNode parseAssignmentWithModifiers(final Operator close) {
        final Set<Modifier> modifiers = new HashSet<>();
        Token current = tokenQueue.remove();
        while (current.is(TokenType.MODIFIER)) {
            modifiers.add(Modifier.valueOf(current.getValue().toUpperCase()));
            current = tokenQueue.remove();
        }
        
        if (!current.is(TokenType.SYMBOL)) {
            throw new QuarkParseException("modifiers may only precede a symbol");
        }
        final SymbolNode target = new SymbolNode(current.getValue());
        
        SyntaxTreeNode source = null;
        if (isNext(Operator.ASSIGNMENT)) {
            tokenQueue.remove();
            source = parseExpression(close);
        }
        
        return new AssignmentNode(target, source, modifiers);
    }
    
    private BlockNode parseBlock(final Operator close) {
        SyntaxTreeNode first = parseStatementSet(close);
        
        boolean foundPipe = false;
        if (isNext(Operator.SEPARATOR)) {
            foundPipe = true;
            tokenQueue.remove();
        }
        
        SyntaxTreeNode second = null;
        if (foundPipe) {
            second = parseStatementSet(close);
        } else {
            second = first;
            first = null;
        }
        
        MessageType type = MessageType.UNARY;
        List<String> keywords = null;
        if (first != null) {
            if (first instanceof AssignmentNode) {
                final SymbolNode target = ((AssignmentNode) first).getTarget();
                if (target.isKeyword()) {
                    type = MessageType.KEYWORD;
                    keywords = new LinkedList<>();
                    keywords.add(target.getSymbol());
                } else {
                    type = MessageType.BINARY;
                }
            } else if (first instanceof SymbolNode) {
                type = MessageType.BINARY;
            } else if (first instanceof StatementSetNode) {
                type = MessageType.KEYWORD;
                keywords = new LinkedList<>();
                for (final SyntaxTreeNode node : ((StatementSetNode) first).getStatements()) {
                    if (node instanceof AssignmentNode) {
                        final SymbolNode target = ((AssignmentNode) node).getTarget();
                        keywords.add(target.getSymbol());
                    } else if (node instanceof ExpressionNode) {
                        for (final SyntaxTreeNode subNode : ((ExpressionNode) node).getElements()) {
                            if (subNode instanceof SymbolNode) {
                                keywords.add(((SymbolNode) subNode).getSymbol());
                            }
                        }
                    }
                }
            } else {
                throw new QuarkParseException("invalid syntax for constructor");
            }
        }
        
        tokenQueue.remove();
        return new BlockNode(first, foundPipe, second, new MessageSignature(type, keywords));
    }
    
    private SyntaxTreeNode parseClass() {
        return new ClassNode(parseBlock(Operator.CLOSE_SET_BRACE));
    }
    
    private boolean isNext(final TokenType type) {
        final Token next = tokenQueue.peek();
        if (next == null) {
            throw new QuarkParseException(String.format("missing expected next token with type %s",
                    type.name()));
        }
        return next.getType().equals(type);
    }
    
    private boolean isNext(final Operator operator) {
        final Token next = tokenQueue.peek();
        if (next == null) {
            throw new QuarkParseException(String.format("missing expected operator %s", operator.getSymbol()));
        }
        return next.getType().equals(TokenType.OPERATOR) && next.getValue().charAt(0) == operator.getSymbol();
    }

}

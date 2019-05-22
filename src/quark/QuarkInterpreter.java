package quark;

import lombok.NonNull;
import quark.component.QuarkLexer;
import quark.component.QuarkParser;
import quark.exception.QuarkParseException;
import quark.model.Token;
import quark.model.syntaxtree.SyntaxTreeNode;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Queue;

public class QuarkInterpreter {
    
    public QuarkInterpreter(@NonNull final String filePath) {
        final String code;
        try {
            code = Files.readString(Paths.get(filePath));
        } catch (final Exception e) {
            throw new QuarkParseException("failed to read code from file: " + filePath, e);
        }
        
        final QuarkLexer lexer = new QuarkLexer(code);
        final Queue<Token> tokenQueue = lexer.tokenize();
        tokenQueue.forEach(token -> System.out.println(token.toString()));
        
        final QuarkParser parser = new QuarkParser(tokenQueue);
        final SyntaxTreeNode syntaxTree = parser.parse();
        System.out.println("\n" + syntaxTree.toString());
    }
    
    public static void main(final String[] args) {
        if (args.length != 1) {
            throw new QuarkParseException("invalid command arguments");
        }
        final String filePath = args[0];
        
        new QuarkInterpreter(filePath);
    }
    
}

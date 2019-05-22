package quark.model.syntaxtree;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import quark.model.datatype.QuarkObject;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class StatementSetNode implements SyntaxTreeNode {
    
    @NonNull private final List<SyntaxTreeNode> statements;
    
    public QuarkObject execute(@NonNull final QuarkObject scope) {
        QuarkObject result = null;
        for (final SyntaxTreeNode statement : statements) {
            result = statement.execute(scope);
        }
        return result;
    }
    
}

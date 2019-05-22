package quark.model.syntaxtree;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import quark.model.Modifier;

import java.util.Set;

@Getter
@ToString
@RequiredArgsConstructor
public class AssignmentNode implements SyntaxTreeNode {
    
    @NonNull private final SymbolNode target;
    private final SyntaxTreeNode source;
    private final Set<Modifier> modifiers;
    
}

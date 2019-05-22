package quark.model.syntaxtree;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class ClassNode implements SyntaxTreeNode {
    
    @NonNull private final BlockNode blockNode;
    
}

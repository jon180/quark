package quark.model.syntaxtree;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import quark.model.MessageSignature;

@Getter
@ToString
@RequiredArgsConstructor
public class BlockNode implements SyntaxTreeNode {
    
    private final SyntaxTreeNode constructor;
    private final boolean hadPipe;
    private final SyntaxTreeNode body;
    @NonNull private final MessageSignature signature;
    
}

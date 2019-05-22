package quark.model.syntaxtree;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class ExpressionNode implements SyntaxTreeNode {
    
    @NonNull private final List<SyntaxTreeNode> elements;
    
}

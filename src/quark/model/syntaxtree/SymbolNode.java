package quark.model.syntaxtree;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import quark.model.datatype.QuarkObject;

@Getter
@ToString
@RequiredArgsConstructor
public class SymbolNode implements SyntaxTreeNode {
    
    @NonNull private final String symbol;
    @Setter private boolean isKeyword = false;
    
    public QuarkObject execute(@NonNull final QuarkObject scope) {
        return scope.resolve(symbol);
    }
    
}

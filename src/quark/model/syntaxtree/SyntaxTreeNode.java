package quark.model.syntaxtree;

import lombok.NonNull;
import quark.model.datatype.QuarkObject;

public interface SyntaxTreeNode {
    
    QuarkObject execute(@NonNull final QuarkObject scope);
    
}

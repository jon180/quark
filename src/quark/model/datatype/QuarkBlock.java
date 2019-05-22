package quark.model.datatype;

import lombok.NonNull;
import quark.model.syntaxtree.BlockNode;

public class QuarkBlock extends QuarkObject {
    
    @NonNull private final BlockNode blockNode;
    
    public QuarkBlock(@NonNull final BlockNode blockNode, final QuarkObject parentScope) {
        super(parentScope);
        this.blockNode = blockNode;
    }
    
}

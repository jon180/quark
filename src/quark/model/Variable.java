package quark.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import quark.model.datatype.QuarkObject;

@Builder
@Getter
public class Variable {
    
    private final Visibility visibility;
    private final boolean isFinal;
    private final boolean isStatic;
    private final boolean canGet;
    private final boolean canSet;
    private final boolean isNonNull;
    @Setter private QuarkObject value;
    
}

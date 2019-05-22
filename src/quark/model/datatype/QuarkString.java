package quark.model.datatype;

import lombok.NonNull;

public class QuarkString extends QuarkObject {
    
    @NonNull private final String value;
    
    public QuarkString(@NonNull final String value, final QuarkObject parentScope) {
        super(parentScope);
        this.value = value;
    }
    
}

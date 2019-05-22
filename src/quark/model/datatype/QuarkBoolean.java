package quark.model.datatype;

public class QuarkBoolean extends QuarkObject {
    
    private final boolean value;
    
    public QuarkBoolean(final boolean value, final QuarkObject parentScope) {
        super(parentScope);
        this.value = value;
    }
    
}

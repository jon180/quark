package quark.model.datatype;

public class QuarkInteger extends QuarkObject {
    
    private final long value;
    
    public QuarkInteger(final long value, final QuarkObject parentScope) {
        super(parentScope);
        this.value = value;
    }
    
}

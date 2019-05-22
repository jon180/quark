package quark.model.datatype;

public class QuarkDecimal extends QuarkObject {
    
    private final double value;
    
    public QuarkDecimal(final double value, final QuarkObject parentScope) {
        super(parentScope);
        this.value = value;
    }
    
}

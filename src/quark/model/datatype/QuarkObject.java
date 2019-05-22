package quark.model.datatype;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import quark.exception.QuarkException;
import quark.model.Variable;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class QuarkObject {
    
    private final QuarkObject parentScope;
    @NonNull private final Map<String, Variable> variableMap = new HashMap<>();
    
    public QuarkObject resolve(@NonNull final String symbol) {
        if (variableMap.containsKey(symbol)) {
            return variableMap.get(symbol).getValue();
        } else if (parentScope != null) {
            return parentScope.resolve(symbol);
        } else {
            throw new QuarkException("cannot resolve symbol: " + symbol);
        }
    }
    
}

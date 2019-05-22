package quark.model.datatype;

import lombok.NonNull;
import quark.model.MessageSignature;
import quark.model.Variable;

import java.util.Map;

public class QuarkClass extends QuarkObject {
    
    @NonNull private final Map<String, Map<MessageSignature, Variable>> messageMap;
    
    public QuarkClass(@NonNull final Map<String, Map<MessageSignature, Variable>> messageMap,
                      final QuarkObject parentScope) {
        super(parentScope);
        this.messageMap = messageMap;
    }
    
}

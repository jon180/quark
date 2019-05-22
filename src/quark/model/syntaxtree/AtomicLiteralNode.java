package quark.model.syntaxtree;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import quark.exception.QuarkParseException;
import quark.model.AtomicLiteral;
import quark.model.datatype.QuarkBoolean;
import quark.model.datatype.QuarkDecimal;
import quark.model.datatype.QuarkInteger;
import quark.model.datatype.QuarkObject;
import quark.model.datatype.QuarkString;

@ToString
@RequiredArgsConstructor
public class AtomicLiteralNode implements SyntaxTreeNode {

    @NonNull private final String value;
    @NonNull private final AtomicLiteral type;
    
    public QuarkObject execute(@NonNull final QuarkObject scope) {
        switch (type) {
            case STRING:
                return new QuarkString(value.substring(1, value.length() - 1), scope);
            case INTEGER:
                return new QuarkInteger(Long.parseLong(value), scope);
            case DECIMAL:
                return new QuarkDecimal(Double.parseDouble(value), scope);
            case BOOLEAN:
                return new QuarkBoolean(value.equals("true"), scope);
            case NULL:
                return null;
            default:
                throw new QuarkParseException("unexpected atomic literal type");
        }
    }

}

package quark.model;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Iterator;
import java.util.List;

@ToString
@RequiredArgsConstructor
public class MessageSignature {
    
    private final MessageType type;
    private final List<String> keywords;
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        } else if (o instanceof MessageSignature) {
            final MessageSignature signature = (MessageSignature) o;
            if (!this.type.equals(signature.type)) {
                return false;
            }
            if (type.equals(MessageType.KEYWORD)) {
                if (this.keywords.size() != signature.keywords.size()) {
                    return false;
                }
                final Iterator<String> it1 = this.keywords.iterator();
                final Iterator<String> it2 = signature.keywords.iterator();
                while (it1.hasNext()) {
                    if (!it1.next().equals(it2.next())) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        final String keywordString = keywords == null ? "" : String.join("|", keywords);
        final String string = String.join("|", type.name(), keywordString);
        return string.hashCode();
    }
    
}

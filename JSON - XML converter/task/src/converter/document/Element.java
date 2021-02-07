package converter.document;

import java.util.Map;

public class Element {
    private final String tag;
    private final Map<String, String> attributes;
    private final Content content;

    public Element(final String tag, final Map<String, String> attributes, final Content content) {
        this.tag = tag;
        this.attributes = attributes;
        this.content = content;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public boolean hasContent() {
        return content.isPresent();
    }

    public boolean hasAttributes() {
        return !attributes.isEmpty();
    }

    public Content getContent() {
        return content;
    }

    public String getTag() {
        return tag;
    }
}

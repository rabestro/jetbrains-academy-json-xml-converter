package converter.domain;

import java.util.Map;

public class Element {
    private final String name;
    private final Map<String, String> attributes;
    private final Content content;

    public Element(final String name, final Map<String, String> attributes, final Content content) {
        this.name = name;
        this.attributes = attributes;
        this.content = content;
    }
}

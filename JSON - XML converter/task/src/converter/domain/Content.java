package converter.domain;

import java.util.Collections;
import java.util.List;

public class Content {
    private final String data;
    private final List<Element> children;

    public Content(final String data) {
        this.data = data;
        children = Collections.emptyList();
    }

    public Content(final List<Element> children) {
        this.children = children;
        data = "";
    }

    public boolean isEmpty() {
        return data.isEmpty() && children.isEmpty();
    }

    public boolean hasData() {
        return !data.isEmpty();
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public boolean isPresent() {
        return hasData() || hasChildren();
    }

    public String getData() {
        return data;
    }
}

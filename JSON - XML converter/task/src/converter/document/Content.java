package converter.document;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;

public class Content {
    private final String data;
    private final List<Element> children;

    public Content() {
        this.data = null;
        children = emptyList();
    }

    public Content(final String data) {
        this.data = data;
        children = emptyList();
    }

    public Content(final List<Element> children) {
        data = null;
        this.children = children;
    }

    public boolean hasData() {
        return Objects.nonNull(data);
    }

    public String getData() {
        return data;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public List<Element> getChildren() {
        return children;
    }

    public boolean isPresent() {
        return hasData() || hasChildren();
    }

}

package converter.domain;

import java.util.Collections;
import java.util.List;

public class Content {
    private final String data;
    private final List<Element> elements;

    public Content(final String data) {
        this.data = data;
        elements = Collections.emptyList();
    }

    public Content(final List<Element> elements) {
        this.elements = elements;
        data = "";
    }

    public boolean isEmpty() {
        return data.isEmpty() && elements.isEmpty();
    }

    public boolean hasData() {
        return !data.isEmpty();
    }

    public boolean hasElements() {
        return !elements.isEmpty();
    }
}

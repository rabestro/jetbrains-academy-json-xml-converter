package converter.service;

import converter.document.Element;

import java.util.Deque;
import java.util.LinkedList;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;

public class DocumentPrinter {
    private final Element document;

    public DocumentPrinter(Element document) {
        this.document = document;
    }

    public String print() {
        return print(document, new LinkedList<>());
    }

    private String print(final Element element, final Deque<Element> path) {
        path.add(element);
        final var output = new StringBuilder("Element:")
                .append(lineSeparator())
                .append("path = ")
                .append(path.stream().map(Element::getTag).collect(joining(", ")))
                .append(lineSeparator());
        final var content = element.getContent();
        if (!content.hasChildren()) {
            output.append("value = ")
                    .append(content.getData() == null ? "null" : "\"" + content.getData() + "\"")
                    .append(lineSeparator());
        }
        if (element.hasAttributes()) {
            output.append("attributes:");
            element.getAttributes()
                    .forEach((key, value) -> output.append(String.format("%n%s = \"%s\"", key, value)));
            output.append(lineSeparator());
        }
        output.append(lineSeparator());
        if (content.hasChildren()) {
            content.getChildren().forEach(childElement -> output.append(print(childElement, path)));
        }
        path.removeLast();
        return output.toString();
    }
}

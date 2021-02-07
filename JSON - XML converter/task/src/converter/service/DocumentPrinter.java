package converter.service;

import converter.document.Element;

import java.util.Deque;
import java.util.LinkedList;

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
                .append(System.lineSeparator())
                .append("path = ")
                .append(path.stream().map(Element::getTag).collect(joining(", ")))
                .append(System.lineSeparator());
        final var content = element.getContent();
        if (!content.hasChildren()) {
            output.append("value = ").append(content.getData());
        }
        if (element.hasAttributes()) {
            output.append("attributes:");
            element.getAttributes()
                    .forEach((key, value) -> output.append(String.format("%n%s = \"%s\"", key, value)));
        }
        output.append(System.lineSeparator());
        if (content.hasChildren()) {
            content.getChildren().forEach(childElement -> output.append(print(childElement, path)));
        }
        return output.toString();
    }
}

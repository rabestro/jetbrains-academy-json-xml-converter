package converter.mapper;

import converter.domain.Content;
import converter.domain.Element;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNullElse;
import static java.util.stream.Collectors.toUnmodifiableMap;

public class JsonMapper implements ObjectMapper {
    private static final Pattern ELEMENT = Pattern.compile("\\{\\s*\"(?<tag>\\w+)\"\\s*:\\s*" +
                    "(:?\"(?<content1>.*)\"|\\{(?<attributes>.*)\"#\\k<tag>\"\\s*:\\s*(?<content2>.*?)\\s*})\\s*}",
            Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
    private static final Pattern ATTRIBUTES = Pattern.compile("\"@(\\w+)\"\\s*:\\s*\"(.*)\"");

    @Override
    public Element read(String data) {
        final var matcher = ELEMENT.matcher(data);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Not a valid json: " + data);
        }
        final var tag = matcher.group("tag");
        final var attributes = parseAttributes(matcher.group("attributes"));
        final var content = parseContent(
                requireNonNullElse(matcher.group("content1"), matcher.group("content2")));
        return new Element(tag, attributes, content);
    }

    @Override
    public String write(Element document) {
        return "{\"" + document.getTag() + "\":" +
                (document.hasAttributes()
                        ? writeAttributes(document)
                        : writeContent(document)) +
                '}';
    }

    private String writeAttributes(final Element document) {
        return document.getAttributes().entrySet().stream()
                .map(entry -> String.format(" \"@%s\" : \"%s\"", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(",",
                        "{", ", \"#" + document.getTag() + "\" : " + writeContent(document) + "}"));
    }

    private String writeContent(final Element document) {
        if (document.getContent().hasData()) {
            return "\"" + document.getContent().getData() + "\"";
        }
        return "null";
    }

    private Content parseContent(String data) {
        var content = Objects.toString(data, "");
        if ("null".equalsIgnoreCase(content)) {
            return new Content("");
        }
        return new Content(removeQuotes(content));
    }

    private Map<String, String> parseAttributes(final String attributes) {
        return ATTRIBUTES.matcher(Objects.toString(attributes, "")).results()
                .collect(toUnmodifiableMap(result -> result.group(1), result -> result.group(2)));
    }

    private String removeQuotes(final String data) {
        return data.startsWith("\"") ? data.substring(1, data.length() - 1) : data;
    }

}

package converter.mapper;

import converter.domain.Content;
import converter.domain.Element;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableMap;

public class XmlMapper implements ObjectMapper {
    private static final Pattern ELEMENT = Pattern.compile(
            "<(?<tag>\\w+)(?<attributes> .*)?(?:/>|>(?<content>.*)</\\k<tag>)>",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern ATTRIBUTES = Pattern.compile("(\\w+)\\s*=\\s*['\"]([^'\"]*)['\"]");

    public Element read(final String data) {
        final var matcher = ELEMENT.matcher(data);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Not a valid xml: " + data);
        }
        final var tag = matcher.group("tag");
        final var attributes = parseAttributes(matcher.group("attributes"));
        final var content = parseContent(matcher.group("content").trim());

        return new Element(tag, attributes, content);
    }

    @Override
    public String write(final Element document) {
        final var output = new StringBuilder();
        output.append('<')
                .append(document.getTag())
                .append(writeAttributes(document));
        if (document.hasContent()) {
            output.append('>')
                    .append(writeContent(document.getContent()))
                    .append("</")
                    .append(document.getTag())
                    .append('>');
        } else {
            output.append(" />");
        }
        return output.toString();
    }

    private String writeAttributes(final Element document) {
        return document.getAttributes().entrySet().stream()
                .map(entry -> String.format(" %s = \"%s\"", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining());
    }

    private String writeContent(final Content content) {
        if (content.hasData()) {
            return content.getData();
        }
        return "";
    }

    private Content parseContent(final String content) {
        return new Content(content);
    }

    private Map<String, String> parseAttributes(final String attributes) {
        return ATTRIBUTES.matcher(attributes).results()
                .collect(toUnmodifiableMap(result -> result.group(1), result -> result.group(2)));
    }

}

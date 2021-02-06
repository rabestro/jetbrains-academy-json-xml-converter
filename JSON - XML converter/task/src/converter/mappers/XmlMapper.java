package converter.mappers;

import converter.domain.Content;
import converter.domain.Element;

import java.util.Map;
import java.util.regex.Pattern;

public class XmlMapper {
    private static final Pattern ELEMENT = Pattern.compile(
            "<(?<tag>\\w+)(?<attributes> .*)?(?:/>|>(?<content>.*)</\\k<tag>)",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern ATTRIBUTES = Pattern.compile("(\\w+) *= *['\"]([^'\"]*)['\"]");

    public Element read(final String data) {
        final var matcher = ELEMENT.matcher(data);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Not a valid xml: " + data);
        }
        final var tag = matcher.group("tag");
        final var attributes = parseAttributes(matcher.group("attributes"));
        final var content = parseContent(matcher.group("content"));

        return new Element(tag, attributes, content);
    }

    private Content parseContent(final String content) {
        return null;
    }

    private Map<String, String> parseAttributes(final String attributes) {

        return null;
    }

    public String toJson() {
        return "";
    }
}

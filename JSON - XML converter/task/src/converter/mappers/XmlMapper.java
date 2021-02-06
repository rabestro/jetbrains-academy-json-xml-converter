package converter.mappers;

import converter.domain.Content;
import converter.domain.Element;

import java.util.Map;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toUnmodifiableMap;

public class XmlMapper implements ObjectMapper {
    private static final Pattern ELEMENT = Pattern.compile(
            "<(?<tag>\\w+)(?<attributes> .*)?(?:/>|>(?<content>.*)</\\k<tag>)",
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

    private Content parseContent(final String content) {
        return new Content(content);
    }

    private Map<String, String> parseAttributes(final String attributes) {
        return ATTRIBUTES.matcher(attributes).results()
                .collect(toUnmodifiableMap(result -> result.group(1), result -> result.group(2)));
    }

    public String toJson() {
        return "";
    }
}

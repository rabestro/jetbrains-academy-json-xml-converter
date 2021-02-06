package converter.mapper;

import converter.domain.Content;
import converter.domain.Element;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNullElse;
import static java.util.stream.Collectors.toUnmodifiableMap;

public class JsonMapper implements ObjectMapper {
    private static final Pattern ELEMENT = Pattern.compile("\\{\\s*\"(?<tag>\\w+)\"\\s*:\\s*" +
                    "(:?\"(?<content1>.*)\"|\\{(?<attributes>.*)\"#\\k<tag>\"\\s*:\\s*\"?(?<content2>.*?)\"?\\s*})\\s*}",
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

    private Content parseContent(String data) {
        return new Content(Objects.toString(data, ""));
    }

    private Map<String, String> parseAttributes(final String attributes) {
        return ATTRIBUTES.matcher(Objects.toString(attributes, "")).results()
                .collect(toUnmodifiableMap(result -> result.group(1), result -> result.group(2)));
    }

    @Override
    public String write(Element document) {
        return null;
    }
}

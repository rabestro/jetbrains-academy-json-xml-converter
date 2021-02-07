package converter.mapper;

import converter.document.Content;
import converter.document.Element;

import java.util.Map;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toUnmodifiableList;
import static java.util.stream.Collectors.toUnmodifiableMap;

public class XmlMapper implements ObjectMapper {
    private static final Pattern ELEMENT = Pattern.compile(
            "<(?<tag>\\w+)(?<attributes> .*)?(:? ?/>|>(?<content>.*)</\\k<tag>>)",
            Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
    private static final Pattern ATTRIBUTES = Pattern.compile("(\\w+)\\s*=\\s*['\"]([^'\"]*)['\"]");
    private static final Pattern CHILDREN = Pattern.compile(
            "(?<child><(?<tag>\\w+)(?=[ />]).*?(/>|</\\k<tag>>))");

    public Element parse(final String data) {
        final var matcher = ELEMENT.matcher(data);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Not a valid xml: " + data);
        }
        final var tag = matcher.group("tag");
        final var attributes = parseAttributes(matcher.group("attributes"));
        final var content = parseContent(matcher.group("content"));

        return new Element(tag, attributes, content);
    }

    @Override
    public String print(final Element document) {
        final var output = new StringBuilder();
        output.append('<')
                .append(document.getTag())
                .append(printAttributes(document));
        if (document.hasContent()) {
            output.append('>')
                    .append(printContent(document.getContent()))
                    .append("</")
                    .append(document.getTag())
                    .append('>');
        } else {
            output.append(" />");
        }
        return output.toString();
    }

    private String printAttributes(final Element document) {
        return document.getAttributes().entrySet().stream()
                .map(entry -> String.format(" %s = \"%s\"", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining());
    }

    private String printContent(final Content content) {
        if (content.hasData()) {
            return content.getData();
        }
        return "";
    }

    private Content parseContent(final String content) {
        if (isNull(content)) {
            return new Content();
        }
        if (!content.startsWith("<")) {
            return new Content(content);
        }
        final var children = CHILDREN
                .matcher(content)
                .results()
                .map(MatchResult::group)
                .map(this::parse)
                .collect(toUnmodifiableList());

        return new Content(children);
    }

    private Map<String, String> parseAttributes(final String attributes) {
        return ATTRIBUTES.matcher(Objects.toString(attributes, "")).results()
                .collect(toUnmodifiableMap(result -> result.group(1), result -> result.group(2)));
    }

}

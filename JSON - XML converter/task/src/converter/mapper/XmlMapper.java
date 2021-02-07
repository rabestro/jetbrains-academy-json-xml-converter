package converter.mapper;

import converter.document.Content;
import converter.document.Element;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toUnmodifiableList;
import static java.util.stream.Collectors.toUnmodifiableMap;

public class XmlMapper implements ObjectMapper {
    private static final Logger log = Logger.getLogger(XmlMapper.class.getName());

    private static final Pattern ELEMENT = Pattern.compile(
            "<(?<tag>\\w+)\\s*(?<attributes>\\s+[^>]*)?\\s*(:?/>|>\\s*(?<content>.*)\\s*</\\k<tag>>)",
            Pattern.DOTALL);
    private static final Pattern CHILDREN = Pattern.compile(
            "(?<child><(?<tag>\\w+)[^/>]*(/>|>.*?</\\k<tag>>))", Pattern.DOTALL);
    private static final Pattern ATTRIBUTES = Pattern.compile("(\\w+)\\s*=\\s*['\"]([^'\"]*)['\"]");

    public Element parse(final String data) {
        log.entering(XmlMapper.class.getName(), "parse", data);

        final var matcher = ELEMENT.matcher(data);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Not a valid xml: " + data);
        }
        final var tag = matcher.group("tag");
        final var attributes = parseAttributes(matcher.group("attributes"));
        final var content = parseContent(matcher.group("content"));
        final var element =  new Element(tag, attributes, content);

        log.exiting(XmlMapper.class.getName(), "parse", element);
        return element;
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
        log.entering(XmlMapper.class.getName(), "parseContent", content);

        if (isNull(content)) {
            return new Content();
        }
        final var data = content.strip();
        if (!data.startsWith("<")) {
            log.exiting(XmlMapper.class.getName(), "parseContent", data);
            return new Content(data);
        }
        final var children = CHILDREN
                .matcher(data)
                .results()
                .map(MatchResult::group)
                .map(this::parse)
                .collect(toUnmodifiableList());

        log.exiting(XmlMapper.class.getName(), "parseContent", children);
        return new Content(children);
    }

    private Map<String, String> parseAttributes(final String attributes) {
        log.entering(XmlMapper.class.getName(), "parseAttributes", attributes);
        final var output =  ATTRIBUTES.matcher(Objects.toString(attributes, "")).results()
                .collect(Collectors.toMap(
                        result -> result.group(1),
                        result -> result.group(2),
                        (v1, v2) -> { throw new IllegalStateException(); },
                        LinkedHashMap::new));
        log.exiting(XmlMapper.class.getName(), "parseAttributes", output);
        return output;
    }

}

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableList;
import static my.Assert.*;

public class Element {
    private static final Pattern PATH_PATTERN = Pattern
            .compile("^path\\s*=\\s*(?<path>\\w+(?:, \\w+)*)\\s*", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALUE_PATTERN = Pattern
            .compile("^value\\s*=\\s*(?<value>null|\".*\")\\s*", Pattern.CASE_INSENSITIVE);

    private static final Pattern ATTRIBUTE_PATTERN = Pattern
            .compile("(?<key>\\w+)\\s*=\\s*\"(?<value>.*)\"", Pattern.CASE_INSENSITIVE);

    private static final Pattern THIRD_PATTERN = Pattern
            .compile("^(?<keyword>value|attributes).*", Pattern.CASE_INSENSITIVE);

    private final String path;
    private final ElementValue value;
    private final Map<String, String> attributes;

    public Element(final String path) {
        this(path, ElementValue.ABSENT);
    }

    public Element(final String path, final ElementValue value) {
        this(path, value, Collections.emptyMap());
    }

    public Element(final String path, final ElementValue value, final Map<String, String> attributes) {
        this.path = path;
        this.value = value;
        this.attributes = attributes;
    }

    public static Element parse(final String data) {
        final var block = data.lines()
                .filter(not(String::isBlank))
                .map(String::strip)
                .collect(toUnmodifiableList());

        assertTrue(block.size() > 1, "minimumTwoLines");

        final var lines = block.iterator();

        final var first = lines.next();
        assertTrue(first.startsWith("Element:"), "startElement", first);

        final var second = lines.next();
        assertTrue(second.startsWith("path"), "startPath", second);

        assertMatches(PATH_PATTERN, second, "pathPattern", second);
        final var path = PATH_PATTERN.matcher(second).group("path");

        if (!lines.hasNext()) {
            return new Element(path);
        }

        final var third = lines.next();
        assertMatches(THIRD_PATTERN, third, "thirdLine", third);

        final var keyword = THIRD_PATTERN.matcher(third).group("keyword");
        final ElementValue value;

        if ("value".equalsIgnoreCase(keyword)) {
            assertMatches(VALUE_PATTERN, third, "valuePattern", third);
            value = ElementValue.parse(VALUE_PATTERN.matcher(third).group("value"));
            if (!lines.hasNext()) {
                return new Element(path, value);
            }
            assertTrue(lines.next().startsWith("attributes"), "startAttributes");
        } else {
            value = ElementValue.ABSENT;
        }

        assertTrue(lines.hasNext(), "noAttributes");

        final var attributes = new HashMap<String, String>();

        final Consumer<String> parseAttribute = line -> {
            final var record = ATTRIBUTE_PATTERN.matcher(line);
            assertTrue(record.matches(), "attributePattern", line);
            attributes.put(record.group("key"), record.group("value"));
        };

        lines.forEachRemaining(parseAttribute);

        return new Element(path, value, attributes);
    }

}

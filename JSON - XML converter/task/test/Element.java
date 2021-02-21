import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableList;
import static my.Assert.*;

public class Element {
    private static final Pattern PATH_PATTERN = Pattern
            .compile("^path\\s*=\\s*(?<path>\\w+(?:, \\w+)*)\\s*", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALUE_PATTERN = Pattern
            .compile("^value\\s*=\\s*(?<value>null|\".*\")\\s*", Pattern.CASE_INSENSITIVE);

    private static final Pattern THIRD_PATTERN = Pattern
            .compile("^(?<keyword>value|attributes).*", Pattern.CASE_INSENSITIVE);

    private final String path;
    private ElementValue value;
    private final Map<String, String> attributes;

    Element(final String path) {
        this.path = path;
        attributes = Collections.emptyMap();
    }

    static Element parse(final String data) {
        final var lines = data.lines()
                .filter(not(String::isBlank))
                .map(String::strip)
                .collect(toUnmodifiableList());

        assertTrue(lines.size() > 1, "minimumTwoLines");

        final var first = lines.get(0);
        assertTrue(first.startsWith("Element:"), "startElement", first);

        final var second = lines.get(1);
        assertTrue(second.startsWith("path"), "startPath", second);

        assertMatches(PATH_PATTERN, second, "pathPattern", second);
        final var pathValue = PATH_PATTERN.matcher(second).group("path");

        if (lines.size() == 2) {
            return new Element(pathValue);
        }

        final var third = lines.get(2);
        assertMatches(THIRD_PATTERN, third, "thirdLine", third);

        final var keyword = THIRD_PATTERN.matcher(third).group("keyword");

        assertMatches(VALUE_PATTERN, second, "valuePattern", second);
        final var value = VALUE_PATTERN.matcher(second).group("value");

        assertEquals("", "", "");
        return new Element(pathValue);
    }
}

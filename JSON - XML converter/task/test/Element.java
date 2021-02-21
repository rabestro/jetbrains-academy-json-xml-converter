import java.util.Map;
import java.util.regex.Pattern;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableList;
import static my.Assert.*;

public class Element {
    private static final Pattern PATH_PATTERN = Pattern
            .compile("^path\\s*=\\s*(?<value>\\w+(, \\w+)*)\\s*", Pattern.CASE_INSENSITIVE);

    private String path;
    private String value;
    private Map<String, String> attributes;

    static Element parse(final String data) {
        final var lines = data.lines()
                .filter(not(String::isBlank))
                .collect(toUnmodifiableList());

        assertTrue(lines.size() > 1, "minimumTwoLines");

        final var firstLine = lines.get(0);
        assertTrue(firstLine.startsWith("Element:"), "startElement", firstLine);

        final var path = lines.get(1);
        assertTrue(path.startsWith("path"), "startPath", path);

        assertMatches(PATH_PATTERN, path, "pathPattern", path);
        final var pathValue = PATH_PATTERN.matcher(path).group("value");

        assertEquals("", "", "");
        return new Element();
    }
}

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toUnmodifiableList;
import static my.Assert.assertEquals;
import static my.Assert.assertFalse;


public class ConverterTest extends StageTest {

    private static final Pattern ELEMENTS_DELIMITER = Pattern
            .compile("\\s+(?=Element:)", Pattern.CASE_INSENSITIVE);

    final int[] testCases = {1, 2, 3, 4};

    @DynamicTest(data = "testCases")
    CheckResult simpleTest(final int testCase) throws IOException {
        Files.copy(
                Path.of("test/data/test" + testCase + ".xml"),
                Path.of("test.txt"),
                StandardCopyOption.REPLACE_EXISTING);

        final var expectedOutput = Files.readString(Path.of("test/data/expected" + testCase + ".txt"));
        final var expected = parseDocument(expectedOutput);

        final var program = new TestedProgram();
        final var actual = parseDocument(program.start());

        assertFalse(actual.size() < expected.size(),
                "lessElements", actual.size(), expected.size());

        assertFalse(actual.size() < expected.size(),
                "moreElements", actual.size(), expected.size());

        for (int i = 0; i < expected.size(); ++i) {
            assertEquals(expected.get(i), expected.get(i),
                    "elementsNotEqual", i + 1, expected.get(i));
        }

        return CheckResult.correct();
    }

    private List<Element> parseDocument(final String data) {
        return ELEMENTS_DELIMITER
                .splitAsStream(data)
                .map(Element::parse)
                .collect(toUnmodifiableList());
    }
}

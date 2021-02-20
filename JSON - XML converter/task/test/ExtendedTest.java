import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

import static java.text.MessageFormat.format;

public abstract class ExtendedTest<T> extends StageTest<T> {
    protected static final ResourceBundle messages = ResourceBundle.getBundle("ErrorMessages");
    private static final DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();

    public static void assertEquals(
            final String expected,
            final String actual,
            final String error,
            final Object... args) {

        if (!expected.equals(actual)) {
            final var feedback = format(messages.getString(error), args);
            throw new WrongAnswer(feedback);
        }
    }

    public static void createFile(final String name, final String content) {
        try {
            Files.writeString(Path.of(name), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void assertFalse(
            final boolean condition,
            final String error,
            final Object... args) {
        if (condition) {
            final var feedback = format(messages.getString(error), args);
            throw new WrongAnswer(feedback);
        }
    }

    public static void assertMatches(
            final String pattern,
            final String actual,
            final String error,
            final Object... args) {

        if (!actual.matches(pattern)) {
            final var feedback = format(messages.getString(error), args);
            throw new WrongAnswer(feedback);
        }
    }

    public static void assertEqualXML(
            final String expected,
            final String actual,
            final String error,
            final Object... args) {

        try {
            final var expectedXML = stringToXML(expected);
            final var actualXML = stringToXML(actual);

        } catch (Exception ex) {
            throw new WrongAnswer("Can't check the output - invalid XML.");
        }
    }

    public static Element stringToXML(final String xmlString) throws Exception {
        final var input = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
        return xmlFactory.newDocumentBuilder().parse(input).getDocumentElement();
    }
}

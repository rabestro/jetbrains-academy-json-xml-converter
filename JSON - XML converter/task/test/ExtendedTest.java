import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

import static java.text.MessageFormat.format;

public abstract class ExtendedTest<T> extends StageTest<T> {
    protected static final ResourceBundle messages = ResourceBundle.getBundle("ErrorMessages");

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
}

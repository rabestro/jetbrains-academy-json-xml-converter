import org.hyperskill.hstest.exception.outcomes.WrongAnswer;

import java.util.ResourceBundle;

import static java.text.MessageFormat.format;

public class Assert {
    private static final ResourceBundle messages = ResourceBundle.getBundle("ErrorMessages");

    public static void assertEquals(
            final Object expected,
            final Object actual,
            final String error,
            final Object... args) {

        if (!expected.equals(actual)) {
            final var feedback = format(messages.getString(error), args);
            throw new WrongAnswer(feedback);
        }
    }
}
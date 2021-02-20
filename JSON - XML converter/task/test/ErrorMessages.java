import java.util.ListResourceBundle;

public class ErrorMessages extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"feedback", "Test: {0}\nAnswer: {1}\nYour output: {2}"}
        };
    }
}

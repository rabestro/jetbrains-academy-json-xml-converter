import java.util.ListResourceBundle;

public class ErrorMessages extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"empty", "Your output is empty line."},
                {"firstSymbol", "Your first symbol is wrong - should be ''{'' or ''<''"}
        };
    }
}

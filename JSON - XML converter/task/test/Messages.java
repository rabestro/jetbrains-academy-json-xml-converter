import java.util.ListResourceBundle;

public class Messages extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"empty", "Your output is empty line."},
                {"minimumTwoLines",
                        "Every element block should contains minimum two lines."},
                {"startElement",
                        "The first line of element block should starts with \"Element\" keyword.\n"
                                + "Found the first line: \"{0}\"."},
                {"startPath",
                        "The second line of element block should starts with \"path\" keyword.\n"
                                + "Found the second line in the block: \"{0}\"."},
                {"pathPattern",
                        "The path of element should be in the format: \"path = tag0, tag1, ..., tagN\"\n"
                                + "Found the path in format: \"{0}\"."}

        };
    }
}
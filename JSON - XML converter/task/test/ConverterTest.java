import my.Assert;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toUnmodifiableList;


public class ConverterTest extends StageTest {

    private static final Pattern ELEMENTS_DELIMITER = Pattern
            .compile("\\s+(?=Element:)", Pattern.CASE_INSENSITIVE);

    private static final String[][] clues = new String[][]{{"" +
            "<transaction>\n" +
            "    <id>6753322</id>\n" +
            "    <number region=\"Russia\">8-900-000-00-00</number>\n" +
            "    <nonattr />\n" +
            "    <nonattr></nonattr>\n" +
            "    <nonattr>text</nonattr>\n" +
            "    <attr id=\"1\" />\n" +
            "    <attr id=\"2\"></attr>\n" +
            "    <attr id=\"3\">text</attr>\n" +
            "    <email>\n" +
            "        <to>to_example@gmail.com</to>\n" +
            "        <from>from_example@gmail.com</from>\n" +
            "        <subject>Project discussion</subject>\n" +
            "        <body font=\"Verdana\">Body message</body>\n" +
            "        <date day=\"12\" month=\"12\" year=\"2018\"/>\n" +
            "    </email>\n" +
            "</transaction>"
            , "" +
            "Element:\n" +
            "path = transaction\n" +
            "\n" +
            "Element:\n" +
            "path = transaction, id\n" +
            "value = \"6753322\"\n" +
            "\n" +
            "Element:\n" +
            "path = transaction, number\n" +
            "value = \"8-900-000-00-00\"\n" +
            "attributes:\n" +
            "region = \"Russia\"\n" +
            "\n" +
            "Element:\n" +
            "path = transaction, nonattr\n" +
            "value = null\n" +
            "\n" +
            "Element:\n" +
            "path = transaction, nonattr\n" +
            "value = \"\"\n" +
            "\n" +
            "Element:\n" +
            "path = transaction, nonattr\n" +
            "value = \"text\"\n" +
            "\n" +
            "Element:\n" +
            "path = transaction, attr\n" +
            "value = null\n" +
            "attributes:\n" +
            "id = \"1\"\n" +
            "\n" +
            "Element:\n" +
            "path = transaction, attr\n" +
            "value = \"\"\n" +
            "attributes:\n" +
            "id = \"2\"\n" +
            "\n" +
            "Element:\n" +
            "path = transaction, attr\n" +
            "value = \"text\"\n" +
            "attributes:\n" +
            "id = \"3\"\n" +
            "\n" +
            "Element:\n" +
            "path = transaction, email\n" +
            "\n" +
            "Element:\n" +
            "path = transaction, email, to\n" +
            "value = \"to_example@gmail.com\"\n" +
            "\n" +
            "Element:\n" +
            "path = transaction, email, from\n" +
            "value = \"from_example@gmail.com\"\n" +
            "\n" +
            "Element:\n" +
            "path = transaction, email, subject\n" +
            "value = \"Project discussion\"\n" +
            "\n" +
            "Element:\n" +
            "path = transaction, email, body\n" +
            "value = \"Body message\"\n" +
            "attributes:\n" +
            "font = \"Verdana\"\n" +
            "\n" +
            "Element:\n" +
            "path = transaction, email, date\n" +
            "value = null\n" +
            "attributes:\n" +
            "day = \"12\"\n" +
            "month = \"12\"\n" +
            "year = \"2018\""}
    };

    static Map<String, String> allTests;

    static {
        allTests = new LinkedHashMap<>();

        allTests.put(
                "<node><child name=\"child_name1\" type=\"chil" +
                        "d_type1\"><subchild id=\"1\" auth=\"auth1\">" +
                        "Value1</subchild></child><child name=\"child" +
                        "_name2\" type=\"child_type2\"><subchild id=\"" +
                        "2\" auth=\"auth1\">Value2</subchild><subchil" +
                        "d id=\"3\" auth=\"auth2\">Value3</subchild><s" +
                        "ubchild id=\"4\" auth=\"auth3\"></subchild><su" +
                        "bchild id=\"5\" auth=\"auth3\"/></child></node>",


                "Element:\n" +
                        "path = node\n" +
                        "\n" +
                        "Element:\n" +
                        "path = node, child\n" +
                        "attributes:\n" +
                        "name = \"child_name1\"\n" +
                        "type = \"child_type1\"\n" +
                        "\n" +
                        "Element:\n" +
                        "path = node, child, subchild\n" +
                        "value = \"Value1\"\n" +
                        "attributes:\n" +
                        "id = \"1\"\n" +
                        "auth = \"auth1\"\n" +
                        "\n" +
                        "Element:\n" +
                        "path = node, child\n" +
                        "attributes:\n" +
                        "name = \"child_name2\"\n" +
                        "type = \"child_type2\"\n" +
                        "\n" +
                        "Element:\n" +
                        "path = node, child, subchild\n" +
                        "value = \"Value2\"\n" +
                        "attributes:\n" +
                        "id = \"2\"\n" +
                        "auth = \"auth1\"\n" +
                        "\n" +
                        "Element:\n" +
                        "path = node, child, subchild\n" +
                        "value = \"Value3\"\n" +
                        "attributes:\n" +
                        "id = \"3\"\n" +
                        "auth = \"auth2\"\n" +
                        "\n" +
                        "Element:\n" +
                        "path = node, child, subchild\n" +
                        "value = \"\"\n" +
                        "attributes:\n" +
                        "id = \"4\"\n" +
                        "auth = \"auth3\"\n" +
                        "\n" +
                        "Element:\n" +
                        "path = node, child, subchild\n" +
                        "value = null\n" +
                        "attributes:\n" +
                        "id = \"5\"\n" +
                        "auth = \"auth3\""
        );

        allTests.put(
                "<transaction>\n" +
                        "    <id>6753322</id>\n" +
                        "    <number region=\"Russia\">8-900-999-00-00</number>\n" +
                        "    <email>\n" +
                        "        <to>to_example@gmail.com</to>\n" +
                        "        <from>from_example@gmail.com</from>\n" +
                        "        <subject>Project discussion</subject>\n" +
                        "        <body font=\"Verdana\">Body message</body>\n" +
                        "        <date day=\"12\" month=\"12\" year=\"2018\"/>\n" +
                        "    </email>\n" +
                        "</transaction>",


                "Element:\n" +
                        "path = transaction\n" +
                        "\n" +
                        "Element:\n" +
                        "path = transaction, id\n" +
                        "value = \"6753322\"\n" +
                        "\n" +
                        "Element:\n" +
                        "path = transaction, number\n" +
                        "value = \"8-900-999-00-00\"\n" +
                        "attributes:\n" +
                        "region = \"Russia\"\n" +
                        "\n" +
                        "Element:\n" +
                        "path = transaction, email\n" +
                        "\n" +
                        "Element:\n" +
                        "path = transaction, email, to\n" +
                        "value = \"to_example@gmail.com\"\n" +
                        "\n" +
                        "Element:\n" +
                        "path = transaction, email, from\n" +
                        "value = \"from_example@gmail.com\"\n" +
                        "\n" +
                        "Element:\n" +
                        "path = transaction, email, subject\n" +
                        "value = \"Project discussion\"\n" +
                        "\n" +
                        "Element:\n" +
                        "path = transaction, email, body\n" +
                        "value = \"Body message\"\n" +
                        "attributes:\n" +
                        "font = \"Verdana\"\n" +
                        "\n" +
                        "Element:\n" +
                        "path = transaction, email, date\n" +
                        "value = null\n" +
                        "attributes:\n" +
                        "day = \"12\"\n" +
                        "month = \"12\"\n" +
                        "year = \"2018\""
        );
    }

    int[] files = {1, 2, 3, 4};

    @DynamicTest(data = "files")
    CheckResult simpleTest(int file) throws IOException {
        Files.copy(
                Path.of("test/data/test" + file + ".txt"),
                Path.of("test.txt"),
                StandardCopyOption.REPLACE_EXISTING);

        final var expectedOutput = Files.readString(Path.of("test/data/expected" + file + ".txt"));
        final var expected = parseDocument(expectedOutput);

        final var program = new TestedProgram();
        final var actual = parseDocument(program.start());

        Assert.assertFalse(actual.size() < expected.size(),
                "lessElements", actual.size(), expected.size());

        Assert.assertFalse(actual.size() < expected.size(),
                "moreElements", actual.size(), expected.size());

        for (int i = 0; i < expected.size(); ++i) {
            Assert.assertEquals(expected.get(i), expected.get(i),
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

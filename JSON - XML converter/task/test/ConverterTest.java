import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.*;
import java.util.stream.Collectors;

class Clue {
    String answer;
    String input;

    Clue(String answer, String input) {
        this.answer = answer.strip();
        this.input = input.strip();
    }
}

public class ConverterTest extends StageTest<Clue> {

    static Map<String, String> allTests;

    static {
        allTests = new LinkedHashMap<>();

        allTests.put(
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
                "year = \"2018\""
        );

        allTests.put(
            "<node>\n" +
                "    <child name = \"child_name1\" type = \"child_type1\">\n" +
                "        <subchild id = \"1\" auth=\"auth1\">Value1</subchild>\n" +
                "    </child>\n" +
                "    <child name = \"child_name2\" type = \"child_type2\">\n" +
                "        <subchild id = \"2\" auth=\"auth1\">Value2</subchild>\n" +
                "        <subchild id = \"3\" auth=\"auth2\">Value3</subchild>\n" +
                "        <subchild id = \"4\" auth=\"auth3\"></subchild>\n" +
                "        <subchild id = \"5\" auth=\"auth3\"/>\n" +
                "    </child>\n" +
                "</node>",


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

    @Override
    public List<TestCase<Clue>> generate() {

        List<TestCase<Clue>> tests = new ArrayList<>();

        for (String input : allTests.keySet()) {
            String answer = allTests.get(input);

            TestCase<Clue> test = new TestCase<>();
            test.addFile("test.txt", input);
            test.setAttach(new Clue(answer, input));
            tests.add(test);
        }

        return tests;
    }

    @Override
    public CheckResult check(String reply, Clue clue) {

        String user = reply.strip();
        String answer = clue.answer.strip();

        List<String> userLines = user
            .lines()
            .map(String::strip)
            .map(e -> e.replaceAll("\\s+", " "))
            .filter(e -> e.length() > 0)
            .collect(Collectors.toList());

        List<String> answerLines = answer
            .lines()
            .map(String::strip)
            .map(e -> e.replaceAll("\\s+", " "))
            .filter(e -> e.length() > 0)
            .collect(Collectors.toList());

        if (userLines.size() < answerLines.size()) {

            LinkedHashSet<String> answerSet = new LinkedHashSet<>();
            answerSet.addAll(answerLines);
            for (String line : userLines) {
                answerSet.remove(line);
            }
            if (!answerSet.isEmpty()) {
                String notFoundLine = answerSet.stream().findFirst().get();
                return new CheckResult(false,
                    "The following line is not found in output:\n" + notFoundLine);
            }
            return new CheckResult(false);

        } else if (userLines.size() > answerLines.size()) {

            LinkedHashSet<String> userSet = new LinkedHashSet<>();
            userSet.addAll(answerLines);
            for (String line : answerLines) {
                userSet.remove(line);
            }
            if (!userSet.isEmpty()) {
                String excessLine = userSet.stream().findFirst().get();
                return new CheckResult(false,
                    "The following line is not needed in output:\n" + excessLine);
            }
            return new CheckResult(false);

        } else {

            for (int i = 0; i < userLines.size(); i++) {

                String userLine = userLines.get(i);
                String answerLine = answerLines.get(i);

                if (!userLine.equals(answerLine)) {
                    return new CheckResult(false,
                        "The following line was expected:\n" + answerLine + "\n" +
                            "The following line was given:\n" + userLine);
                }
            }

            return CheckResult.correct();
        }
    }
}

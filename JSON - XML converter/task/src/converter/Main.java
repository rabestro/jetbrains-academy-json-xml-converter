package converter;

import converter.mappers.JsonMapper;
import converter.mappers.XmlMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        final var data = Files.readString(Path.of("test.txt"));
        final var isJson = data.startsWith("{");
        final var mapper = isJson ? new JsonMapper() : new XmlMapper();
        final var document = mapper.read(data);

        //(isJson ? new XmlMapper() : new JsonMapper())

        final var scanner = new Scanner(System.in);
        final var converter = new Converter(scanner.nextLine());

        System.out.println(converter.isJson() ? converter.toXml() : converter.toJson());
    }

}

class Converter {
    private final String data;

    Converter(String data) {
        this.data = data;
    }

    boolean isJson() {
        return data.startsWith("{");
    }

    String toJson() {
        if (data.endsWith("/>")) {
            return data.replaceFirst("<(\\w+)/>", "{\"$1\":null}");
        }
        return data.replaceFirst("<(\\w+)>(.+)</\\1>", "{\"$1\":\"$2\"}");
    }

    String toXml() {
        if (data.endsWith(":null}")) {
            return data.replaceFirst("\\{\"(\\w+)\":null}", "<$1/>");
        }
        return data.replaceFirst("\\{\"(\\w+)\":\"(.+)\"}","<$1>$2</$1>");
    }
}
package converter;

import converter.mapper.JsonMapper;
import converter.mapper.XmlMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        final var data = Files.readString(Path.of("test.txt"));
        final var isJson = data.startsWith("{");
        final var reader = isJson ? new JsonMapper() : new XmlMapper();
        final var writer = isJson ? new XmlMapper() : new JsonMapper();

        final var document = reader.parse(data);

        System.out.println(writer.print(document));
    }

}


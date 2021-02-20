package converter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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
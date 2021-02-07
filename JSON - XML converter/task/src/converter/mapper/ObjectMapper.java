package converter.mapper;

import converter.document.Element;

public interface ObjectMapper {
    Element read(final String data);

    String write(final Element document);
}

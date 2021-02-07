package converter.mapper;

import converter.document.Element;

public interface ObjectMapper {
    Element parse(final String data);

    String print(final Element document);
}

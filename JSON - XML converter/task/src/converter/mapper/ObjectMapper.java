package converter.mapper;

import converter.domain.Element;

public interface ObjectMapper {
    Element read(final String data);

    String write(final Element document);
}

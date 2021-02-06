package converter.mappers;

import converter.domain.Element;

public interface ObjectMapper {
    Element read(final String data);
}

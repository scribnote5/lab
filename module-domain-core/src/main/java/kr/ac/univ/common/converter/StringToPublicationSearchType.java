package kr.ac.univ.common.converter;

import kr.ac.univ.publication.dto.enums.PublicationSearchType;
import org.springframework.core.convert.converter.Converter;

public class StringToPublicationSearchType implements Converter<String, PublicationSearchType> {
    @Override
    public PublicationSearchType convert(String source) {
        return PublicationSearchType.valueOf(source.toUpperCase());
    }

}
package kr.ac.univ.common.converter;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import org.springframework.core.convert.converter.Converter;

public class StringToActiveStatus implements Converter<String, ActiveStatus> {
    @Override
    public ActiveStatus convert(String source) {
        return ActiveStatus.valueOf(source.toUpperCase());
    }

}
package kr.ac.univ.common.converter;

import kr.ac.univ.maintenance.domain.enums.ReceiverType;
import org.springframework.core.convert.converter.Converter;

public class StringToReceiverType implements Converter<String, ReceiverType> {
    @Override
    public ReceiverType convert(String source) {
        return ReceiverType.valueOf(source.toUpperCase());
    }
}
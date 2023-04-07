package com.estancia.juventudes.entities.enums.converters;


import com.estancia.juventudes.entities.enums.GenderType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@Converter(autoApply=true)
public class GenderTypeConverter implements AttributeConverter<GenderType, String> {
    @Override
    public String convertToDatabaseColumn(GenderType genderType) {
        if(genderType==null) return null;
        return  genderType.getType();
    }

    @Override
    public GenderType convertToEntityAttribute(String gender) {
        if(gender==null) return  null;
        return  Stream.of(GenderType.values())
                .filter(t-> t.getType().equals(gender))
                .findFirst().orElseThrow(RuntimeException::new);
    }
}

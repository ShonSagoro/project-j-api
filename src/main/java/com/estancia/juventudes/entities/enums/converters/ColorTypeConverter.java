package com.estancia.juventudes.entities.enums.converters;

import com.estancia.juventudes.entities.enums.ColorType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@Converter(autoApply = true)
public class ColorTypeConverter implements AttributeConverter<ColorType, String> {
    @Override
    public String convertToDatabaseColumn(ColorType colorType) {
        if(colorType==null) return null;
        return colorType.getColorName();
    }

    @Override
    public ColorType convertToEntityAttribute(String name) {
        if(name==null) return null;
        return Stream.of(ColorType.values())
                .filter(t-> t.getColorName().equals(name))
                .findFirst().orElseThrow(RuntimeException::new);
    }
}

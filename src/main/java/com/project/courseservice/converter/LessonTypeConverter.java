package com.project.courseservice.converter;

import com.project.courseservice.entity.LessonType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class LessonTypeConverter implements AttributeConverter<LessonType, String> {

    @Override
    public String convertToDatabaseColumn(final LessonType lessonType) {
        if (lessonType == null)
            return null;
        return lessonType.getType();
    }

    @Override
    public LessonType convertToEntityAttribute(final String s) {
        if (s == null)
            return null;

      return Stream.of(LessonType.values())
            .filter(c -> c.getType().equals(s))
            .findFirst().orElseThrow(IllegalArgumentException::new);
        }
}

package de.andrena.extension;

import org.junit.jupiter.api.extension.*;

import java.lang.annotation.*;
import java.lang.reflect.Parameter;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class FixedClockExtension implements ParameterResolver {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    public @interface FixedClockAnnotation {
        String date();
        String time();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return parameterContext.isAnnotated(FixedClockAnnotation.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Optional<FixedClockAnnotation> annotationOptional
                = parameterContext.findAnnotation(FixedClockAnnotation.class);
        if(annotationOptional.isPresent()){
            return getFixedClock(parameterContext.getParameter(), annotationOptional.get());
        }
        throw new ParameterResolutionException("I am confused...");
    }

    private Object getFixedClock(Parameter parameter, FixedClockAnnotation fixedClockAnnotation) {
        Class<?> type = parameter.getType();

        if (Clock.class.equals(type)) {
            String str = fixedClockAnnotation.date().trim() + " " + fixedClockAnnotation.time().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime fixedDate = LocalDateTime.parse(str, formatter);
            return Clock.fixed(fixedDate.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
        }
        throw new ParameterResolutionException("No fixedDateTime implemented for " + type + " only Clock supported.");
    }
}


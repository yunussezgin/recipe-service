package com.crediteurope.recipe.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomOffsetDeserializer extends JsonDeserializer<LocalDateTime> {

    private DateTimeFormatter formatter;

    public CustomOffsetDeserializer() {
        this.formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    }
    
    public CustomOffsetDeserializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        return LocalDateTime.parse(parser.getText(), this.formatter);
    }
}
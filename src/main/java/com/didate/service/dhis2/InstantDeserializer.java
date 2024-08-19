package com.didate.service.dhis2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class InstantDeserializer extends JsonDeserializer<Instant> {

    @Override
    public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String date = p.getText();
        try {
            return LocalDateTime.parse(date).toInstant(ZoneOffset.UTC);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

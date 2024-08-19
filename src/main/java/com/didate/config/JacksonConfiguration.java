package com.didate.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module.Feature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

    /**
     * Support for Java date and time API.
     * @return the corresponding Jackson module.
     */
    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    @Bean
    public Jdk8Module jdk8TimeModule() {
        return new Jdk8Module();
    }

    /*
     * Support for Hibernate types in Jackson.
     */
    @Bean
    public Hibernate6Module hibernate6Module() {
        return new Hibernate6Module().configure(Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Register JavaTimeModule with custom Instant deserializer
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(
            Instant.class,
            new JsonDeserializer<Instant>() {
                @Override
                public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                    String date = p.getText();
                    try {
                        return LocalDateTime.parse(date).toInstant(ZoneOffset.UTC);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse Instant: " + date, e);
                    }
                }
            }
        );
        objectMapper.registerModule(javaTimeModule);

        // Register other modules
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new Hibernate6Module().configure(Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true));

        return objectMapper;
    }
}

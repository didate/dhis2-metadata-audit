package com.didate.deserialize;

import com.didate.domain.DataElement;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DataElementSetDeserializer extends JsonDeserializer<Set<DataElement>> {

    @Override
    public Set<DataElement> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        Set<DataElement> dataElements = new HashSet<>();

        for (JsonNode elementNode : node) {
            JsonNode dataElementNode = elementNode.get("dataElement");
            DataElement dataElement = p.getCodec().treeToValue(dataElementNode, DataElement.class);
            dataElements.add(dataElement);
        }

        return dataElements;
    }
}

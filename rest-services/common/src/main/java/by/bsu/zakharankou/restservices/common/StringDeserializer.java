
package by.bsu.zakharankou.restservices.common;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Deserializer for string.
 * Performs strict parsing of type for the deserialized value. Throws exception
 * during deserialization if value is not a string.
 */
public class StringDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        if (jp.getCurrentToken() != JsonToken.VALUE_STRING) {
            throw new JsonMappingException("JSON string is expected.", jp.getTokenLocation());
        }

        return jp.getText();
    }

}


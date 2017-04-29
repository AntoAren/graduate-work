package by.bsu.zakharankou.restservices.controller.util;

import by.bsu.zakharankou.restservices.controller.WebException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import java.io.IOException;
import java.util.*;

import static org.springframework.util.StringUtils.hasText;
import static by.bsu.zakharankou.restservices.service.serviceapi.Messages.ERROR_JSON_INVALID;
import static by.bsu.zakharankou.restservices.service.serviceapi.Messages.ERROR_JSON_UNEXPECTED_TYPE;

public class ObjectMapper extends com.fasterxml.jackson.databind.ObjectMapper {

    private static final TypeReference<HashMap<String, Object>> MAP_TYPE_REFERENCE = new TypeReference<HashMap<String, Object>>() {
    };
    private static final ObjectMapper MAPPER = new ObjectMapper();
    /* Is used to set private fields values without setters. */
    private static final VisibilityChecker SOFT_VISIBILITY_CHECKER = new VisibilityChecker.Std(
            JsonAutoDetect.Visibility.PUBLIC_ONLY, JsonAutoDetect.Visibility.PUBLIC_ONLY,
            JsonAutoDetect.Visibility.ANY, JsonAutoDetect.Visibility.ANY, JsonAutoDetect.Visibility.ANY
    );

    /**
     * Convert parameters from the specified JSON string to map.
     *
     * @param <T>    type of parameters value
     * @param params string in JSON format
     * @return map
     */
    public static <T> Map<String, T> read(final String params) {
        if (hasText(params)) {
            try {
                return MAPPER.readValue(params, MAP_TYPE_REFERENCE);
            } catch (JsonParseException e) {
                throw new InvalidJsonException(ERROR_JSON_INVALID);
            } catch (JsonMappingException e) {
                //E.g., array is not a valid JSON object and cannot be converted to a map.
                throw new InvalidJsonException(ERROR_JSON_UNEXPECTED_TYPE);
            } catch (IOException e) {
                throw new WebException(e);
            }
        }
        return Collections.emptyMap();
    }

    /**
     * Convert parameters from the specified JSON string to typed object.
     *
     * @param <T>    type of parameters value
     * @param params string in JSON format
     * @return map
     */
    public static <T> T read(final String params, final Class<T> valueType) {
        if (hasText(params)) {
            try {
                final VisibilityChecker visibilityChecker = MAPPER.getVisibilityChecker();
                MAPPER.setVisibilityChecker(SOFT_VISIBILITY_CHECKER);
                final T value = MAPPER.readValue(params, valueType);
                MAPPER.setVisibilityChecker(visibilityChecker);
                return value;
            } catch (JsonParseException e) {
                throw new InvalidJsonException(ERROR_JSON_INVALID);
            } catch (JsonMappingException e) {
                throw new InvalidJsonException(ERROR_JSON_UNEXPECTED_TYPE);
            } catch (IOException e) {
                throw new WebException(e);
            }
        }
        return null;
    }

    /**
     * Serialize object as string in JSON format.
     *
     * @param value object to serialize
     * @return string representation in JSON format
     */
    public static String write(final Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonParseException e) {
            throw new InvalidJsonException(ERROR_JSON_INVALID);
        } catch (JsonMappingException e) {
            throw new InvalidJsonException(ERROR_JSON_UNEXPECTED_TYPE);
        } catch (IOException e) {
            throw new WebException(e);
        }
    }
}
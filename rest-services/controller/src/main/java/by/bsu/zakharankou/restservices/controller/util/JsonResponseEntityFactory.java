package by.bsu.zakharankou.restservices.controller.util;

import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Creates json responses. By default created responses have "{@value MediaType#APPLICATION_JSON_VALUE}" Content-Type header.
 */
@Component(value = "jsonResponseEntityFactory")
public class JsonResponseEntityFactory {

    private static final MediaType MEDIA_TYPE_APPLICATION_JSON = new MediaType(APPLICATION_JSON.getType(), APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    private static final HttpHeaders EMPTY_HEADERS = new HttpHeaders();

    /**
     * Creates a response entity with a message and specific status code.
     *
     * @param message    text message
     * @param statusCode response status code
     * @return response entity
     */
    public ResponseEntity<String> createMessageResponse(final String message, final HttpStatus statusCode) {
        return createResponse(new Message(message), statusCode);
    }

    /**
     * Creates a response entity with a message, additional http headers and specific status code.
     *
     * @param message     text message
     * @param httpHeaders http headers
     * @param statusCode  response status code
     * @return response entity
     */
    public ResponseEntity<String> createMessageResponse(final String message, final HttpHeaders httpHeaders, final HttpStatus statusCode) {
        return createResponse(new Message(message), httpHeaders, statusCode);
    }

    /**
     * Creates a response entity with a serialized object fields and specific status code.
     *
     * @param entity     object message
     * @param statusCode response status code
     * @return response entity
     */
    public ResponseEntity<String> createResponse(final Object entity, final HttpStatus statusCode) {
        return createResponse(entity, EMPTY_HEADERS, statusCode);
    }

    /**
     * Creates a response entity with a serialized object fields, additional http headers and specific status code.
     *
     * @param entity      object message
     * @param httpHeaders http headers
     * @param statusCode  response status code
     * @return response entity
     */
    public ResponseEntity<String> createResponse(final Object entity, final HttpHeaders httpHeaders, final HttpStatus statusCode) {
        final HttpHeaders commonHeaders = getCommonHeaders();
        final HttpHeaders headers = mergeHeaders(commonHeaders, httpHeaders);
        return new ResponseEntity<>(ObjectMapper.write(entity), headers, statusCode);
    }

    /**
     * Creates common headers with defined Content-Type as {@link #MEDIA_TYPE_APPLICATION_JSON}.
     *
     * @return http headers
     */
    protected HttpHeaders getCommonHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MEDIA_TYPE_APPLICATION_JSON);
        return headers;
    }

    /**
     * Merges http headers. The same headers from first container will be replaced by headers from second container.
     *
     * @param headers1 first headers container
     * @param headers2 second headers container
     * @return merged http headers
     */
    protected HttpHeaders mergeHeaders(final HttpHeaders headers1, final HttpHeaders headers2) {
        final HttpHeaders headers = new HttpHeaders();
        final List<HttpHeaders> list = Arrays.asList(headers1, headers2);
        for (final HttpHeaders item : list) {
            if (item != null && !item.isEmpty()) {
                for (final Map.Entry<String, List<String>> entry : item.entrySet()) {
                    headers.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return headers;
    }

    public static class Message {
        public final String message;

        public Message(String message) {
            this.message = message;
        }
    }
}

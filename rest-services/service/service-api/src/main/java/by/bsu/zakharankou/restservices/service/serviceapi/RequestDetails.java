package by.bsu.zakharankou.restservices.service.serviceapi;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.*;

import static by.bsu.zakharankou.restservices.service.serviceapi.Messages.*;
import static org.springframework.util.StringUtils.hasText;

/**
 * Basic class for classes that describe details of some entity (ex.: UserDetails, OrganizationDetails).
 */
public abstract class RequestDetails {

    /* Space, comma, semicolon and pipe separators. */
    public static final String SEPARATOR_CHARS = " ,;|";
    public static final String SEPARATOR_PIPE = "|";

    private Object details;

    protected RequestDetails(final Object details) {
        Assert.notNull(details, "Details cannot be null.");
        this.details = details;
    }

    /**
     * Returns the string with removed leading and trailing whitespace.
     *
     * @param string the string to be trimmed, may be null
     * @return the trimmed string or <code>null</code> if input string is null
     */
    protected String trim(final String string) {
        return StringUtils.trim(string);
    }

    /**
     * Returns the string with whitespace normalized by removing leading and trailing whitespace and then replacing
     * sequences of whitespace characters by a single space.
     *
     * @param string the source string to normalize whitespaces from, may be null
     * @return the modified string with whitespace normalized or <code>null</code> if input string is null
     */
    protected String normalizeString(final String string) {
        return StringUtils.normalizeSpace(string);
    }

    protected Set<String> splitString(final String string) {
        return splitString(string, SEPARATOR_CHARS);
    }

    protected Set<String> splitString(final String string, final String separators) {
        final Set<String> strings = new HashSet<>();
        if (hasText(string)) {
            final String[] patterns = StringUtils.split(string, separators);
            strings.addAll(Arrays.asList(patterns));
        }
        return strings;
    }

    protected String getString(final Map<String, ?> details, final String key) {
        final Object value = details.get(key);
        if (value == null) {
            return null;
        }
        if (!(value instanceof String)) {
            throw new RequestDetailsNotValidException(String.format(ERROR_OBJECT_DETAILS_STRING_PROPERTY_INVALID_TYPE, key));
        }
        return (String) value;
    }

    protected boolean getBoolean(final Map<String, ?> details, final String key) {
        final Object value = details.get(key);
        if (value == null) {
            return false;
        }
        if (!(value instanceof Boolean)) {
            throw new RequestDetailsNotValidException(String.format(ERROR_OBJECT_DETAILS_BOOLEAN_PROPERTY_INVALID_TYPE, key));
        }
        return (boolean) value;
    }

    protected Number getNumber(final Map<String, ?> details, final String key) {
        final Object value = details.get(key);
        if (value == null) {
            return null;
        }
        if (!(value instanceof Number)) {
            throw new RequestDetailsNotValidException(String.format(ERROR_OBJECT_DETAILS_NUMBER_PROPERTY_INVALID_TYPE, key));
        }
        return (Number) value;
    }

    protected Integer getInteger(final Map<String, ?> details, final String key) {
        final Object value = details.get(key);
        if (value == null) {
            return null;
        }
        if (!(value instanceof Integer)) {
            //Throwing EntityValidationException in order to get FORBIDDEN HTTP code instead of BAD_REQUEST in case of RequestDetailsNotValidException
            //JSON format does not distinguish between number classes: Integer, Float, etc. Therefore it is a valid JSON.
            throw new EntityValidationException(String.format(ERROR_OBJECT_DETAILS_INTEGER_PROPERTY_INVALID_TYPE, key));
        }
        return (Integer) value;
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> getList(final Map<String, ?> details, final String key, Class<T> type) {
        final Object value = details.get(key);
        if (value == null) {
            return Collections.emptyList();
        }
        if (!(value instanceof List)) {
            throw new RequestDetailsNotValidException(String.format(ERROR_OBJECT_DETAILS_ARRAY_PROPERTY_INVALID_TYPE, key));
        }
        List<T> list = (List<T>) value;

        for (T element : list) {
            if (element != null && !element.getClass().isAssignableFrom(type)) {
                throw new RequestDetailsNotValidException(String.format(ERROR_OBJECT_DETAILS_ARRAY_OBJECTS_INVALID_TYPE, key, type.getSimpleName()));
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    protected List<Map<String, Object>> getMapsList(final Map<String, ?> details, final String key) {
        final Object value = details.get(key);
        if (value == null) {
            return Collections.emptyList();
        }
        if (!(value instanceof List)) {
            throw new RequestDetailsNotValidException(String.format(ERROR_OBJECT_DETAILS_ARRAY_PROPERTY_INVALID_TYPE, key));
        }
        for (Object item : (List) value) {
            if (!(item instanceof Map)) {
                throw new RequestDetailsNotValidException(String.format(ERROR_OBJECT_DETAILS_ARRAY_PROPERTY_INVALID_ITEMS_TYPE, key));
            }

            for (Map.Entry entry : ((Map<Object, Object>) item).entrySet()) {
                if (!(entry.getKey() instanceof String)) {
                    throw new RequestDetailsNotValidException(String.format(ERROR_OBJECT_DETAILS_ARRAY_PROPERTY_INVALID_ITEM_KEY_TYPE, key));
                }
//                if (!(entry.getValue() instanceof String)) {
//                    throw new RequestDetailsNotValidException(String.format(ERROR_OBJECT_DETAILS_ARRAY_PROPERTY_INVALID_ITEM_VALUE_TYPE, key));
//                }
            }
        }
        return (List<Map<String, Object>>) value;
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> getObject(final Map<String, ?> details, final String key) {
        final Object value = details.get(key);
        if (value == null) {
            return null;
        }
        if (!(value instanceof Map)) {
            throw new RequestDetailsNotValidException(String.format(ERROR_OBJECT_DETAILS_ARRAY_PROPERTY_INVALID_ITEMS_TYPE, key));
        }
        for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) value).entrySet()) {
            if (!(entry.getKey() instanceof String)) {
                throw new RequestDetailsNotValidException(String.format(ERROR_OBJECT_DETAILS_ARRAY_PROPERTY_INVALID_ITEM_KEY_TYPE, key));
            }
        }

        return (Map<String, Object>) value;
    }

    protected Map<String, Map<String, String>> getMapOfMaps(final Map<String, ?> details, final String key) {
        final Object value = details.get(key);

        if (value == null) {
            return null;
        }

        if (!(value instanceof Map)) {
            throw new RequestDetailsNotValidException(String.format(ERROR_OBJECT_DETAILS_PROPERTY_INVALID_TYPE, key));
        }

        for (Map.Entry entry : ((Map<Object, Object>) value).entrySet()) {
            if (!(entry.getKey() instanceof String)) {
                throw new RequestDetailsNotValidException(String.format(ERROR_OBJECT_DETAILS_PROPERTY_INVALID_TYPE, key));
            }
            if (!(entry.getValue() instanceof Map)) {
                throw new RequestDetailsNotValidException(String.format(ERROR_OBJECT_DETAILS_PROPERTY_INVALID_TYPE, key));
            }

            for (Map.Entry entry2 : ((Map<Object, Object>) entry.getValue()).entrySet()) {
                if (!(entry2.getKey() instanceof String)) {
                    throw new RequestDetailsNotValidException(String.format(ERROR_OBJECT_DETAILS_PROPERTY_INVALID_TYPE, key));
                }
                if (!(entry2.getValue() instanceof String)) {
                    throw new RequestDetailsNotValidException(String.format(ERROR_OBJECT_DETAILS_PROPERTY_INVALID_TYPE, key));
                }
            }
        }

        return (Map<String, Map<String, String>>) value;
    }

    public Object getDetails() {
        return details;
    }
}

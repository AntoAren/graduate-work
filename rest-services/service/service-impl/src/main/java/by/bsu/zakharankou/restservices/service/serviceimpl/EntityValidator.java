package by.bsu.zakharankou.restservices.service.serviceimpl;

import by.bsu.zakharankou.restservices.service.serviceapi.EntityValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;

@Component
public class EntityValidator {

    public void validateNotEmpty(final String value, final String message) {
        if (!StringUtils.hasText(value)) {
            throw new EntityValidationException(message);
        }
    }

    public void validateNotEmpty(final Set value, final String message) {
        if (value == null || value.isEmpty()) {
            throw new EntityValidationException(message);
        }
    }

    public void validateLength(final String value, final String message, final int maxValue) {
        if (StringUtils.hasText(value) && value.length() > maxValue) {
            throw new EntityValidationException(message);
        }
    }

    public void validateRange(final int value, final int min, final int max, final String message) {
        if (value < min || value > max) {
            throw new EntityValidationException(message);
        }
    }

    public void validateMinValue(final int value, final int min, final String message) {
        if (value < min) {
            throw new EntityValidationException(message);
        }
    }

    public void validateMaxValue(final int value, final int max, final String message) {
        if (value > max) {
            throw new EntityValidationException(message);
        }
    }

    public void validateNotNull(final Object value, final String message) {
        if (value == null) {
            throw new EntityValidationException(message);
        }
    }
}

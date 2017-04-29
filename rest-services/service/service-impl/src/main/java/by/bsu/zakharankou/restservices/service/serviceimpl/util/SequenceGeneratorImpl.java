package by.bsu.zakharankou.restservices.service.serviceimpl.util;

import by.bsu.zakharankou.restservices.service.serviceapi.util.SequenceGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SequenceGeneratorImpl implements SequenceGenerator {

    /* The minimum length for a decent random string. */
    public static final int DEFAULT_LENGTH = 10;
    public static final char[] COMMON_CHARACTERS = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };
    public static final char[] EXTRA_CHARACTERS = {
            '!', '=', '~', '@', '#', '%', '^', '&', '(', ')', '-', '_', '.'
    };
    public static final String SIMPLE_ALPHABET = new String(COMMON_CHARACTERS);
    public static final String EXTENDED_ALPHABET = SIMPLE_ALPHABET.concat(new String(EXTRA_CHARACTERS));

    @Override
    public String uniqueIdentifier() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generates a random string {@value #DEFAULT_LENGTH} characters long.
     * Uses simple alphabet [a-zA-Z0-9] for string generation.
     *
     * @return the generated string
     */
    @Override
    public String secret() {
        return RandomStringUtils.random(DEFAULT_LENGTH, SIMPLE_ALPHABET);
    }

    /**
     * Generates a random string {@value #DEFAULT_LENGTH} characters long.
     * Should be used with careful because it uses extended alphabet with some specific characters, e.g. [%^&()-].
     *
     * @return the generated string
     */
    @Override
    public String password() {
        return RandomStringUtils.random(DEFAULT_LENGTH, EXTENDED_ALPHABET);
    }
}

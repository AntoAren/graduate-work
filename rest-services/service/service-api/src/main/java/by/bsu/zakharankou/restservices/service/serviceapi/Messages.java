package by.bsu.zakharankou.restservices.service.serviceapi;

/**
 * Contains constants with info, warning and error messages.
 */

public class Messages {
    /* Util messages */
    public static final String ERROR_JSON_INVALID = "Request should have a valid JSON string.";
    public static final String ERROR_JSON_UNEXPECTED_TYPE = "Request should have a JSON of object type.";
    public static final String ERROR_OBJECT_DETAILS_PROPERTY_INVALID_TYPE = "Property %s has not valid data type.";
    public static final String ERROR_OBJECT_DETAILS_STRING_PROPERTY_INVALID_TYPE = "Property %s should have a String data type.";
    public static final String ERROR_OBJECT_DETAILS_BOOLEAN_PROPERTY_INVALID_TYPE = "Property %s should have a Boolean data type.";
    public static final String ERROR_OBJECT_DETAILS_NUMBER_PROPERTY_INVALID_TYPE = "Property %s should have a Number data type.";
    public static final String ERROR_OBJECT_DETAILS_INTEGER_PROPERTY_INVALID_TYPE = "Property %s should have an Integer data type.";
    public static final String ERROR_OBJECT_DETAILS_ARRAY_PROPERTY_INVALID_TYPE = "Property %s should have an Array data type.";
    public static final String ERROR_OBJECT_DETAILS_ARRAY_OBJECTS_INVALID_TYPE = "Array %s should contain elements of type %s.";
    public static final String ERROR_OBJECT_DETAILS_ARRAY_PROPERTY_INVALID_ITEMS_TYPE = "Property %s should contain items with an Object data type.";
    public static final String ERROR_OBJECT_DETAILS_ARRAY_PROPERTY_INVALID_ITEM_KEY_TYPE = "Property %s should contain Object items with the keys of String data type.";

    /* User messages */
    public static final String ERROR_USER_NAME_NULL = "The user name cannot be null.";
    public static final String ERROR_USER_DETAILS_NULL = "The user details cannot be null.";
    public static final String ERROR_USER_NOT_FOUND = "The user %s is not found.";
    public static final String ERROR_USER_ALREADY_EXISTS = "The user %s already exists.";
    public static final String ERROR_USER_NAME_EMPTY = "The user name cannot be empty.";
    public static final String ERROR_USER_NAME_MUST_BE_EMAIL = "The user name has to be valid email.";
    public static final String ERROR_USER_NAME_MAX_SIZE = "The user name cannot be longer than %s characters.";
    public static final String ERROR_USER_PASSWORD_EMPTY = "The password cannot be empty.";
    public static final String ERROR_USER_PASSWORD_INVALID_CHARACTERS = "The password can contain only Latin letters, numbers and special characters [_@.].";
    public static final String ERROR_USER_PASSWORDS_NOT_MATCH = "The password and its confirmation don't match.";
    public static final String ERROR_USER_DISPLAY_NAME_EMPTY = "The user display name cannot be empty.";
    public static final String ERROR_USER_DISPLAY_NAME_MAX_SIZE = "The user display name cannot be longer than %s characters";
    public static final String ERROR_USER_ROLES_EMPTY = "The user roles cannot be empty.";
    public static final String ERROR_USER_NOT_HUMAN = "The user is not a human.";
    public static final String INFO_USER_HAS_BEEN_ADDED = "The user %s has been added successfully.";
    public static final String INFO_USER_HAS_BEEN_ADDED_CHECK_EMAIL = "The user %s has been added successfully. Please check your e-mail and confirm the registration otherwise your account will be deleted in 24h.";
    public static final String INFO_USER_HAS_BEEN_DELETED = "The user %s has been deleted successfully.";

    /* Authority messages */
    public static final String ERROR_AUTHORITY_NOT_FOUND = "The role %s is not found.";
    public static final String ERROR_AUTHORITY_NAME_EMPTY = "The role cannot be empty.";

    /* Token messages */
    public static final String ERROR_BAD_CREDENTIALS = "Bad credentials.";

    /* Authorization messages*/
    public static final String ERROR_INVALID_USERNAME_PASSWORD = "Invalid username or password.";

    /* Test messages */
    public static final String INFO_TEST_HAS_BEEN_ADDED = "Тест %s был успешно создан.";
    public static final String INFO_TEST_HAS_BEEN_ASSIGNED = "Тест был успешно назначен.";
    public static final String INFO_TEST_HAS_BEEN_COMPLETED = "Тест был успешно завершен.";
    public static final String INFO_TEST_HAS_BEEN_EDITED = "Тест был успешно изменен.";
    public static final String INFO_TEST_HAS_BEEN_DELETED = "Тест был успешно удален.";

    /* Answer messages */
    public static final String INFO_ANSWERS_HAVE_BEEN_SAVED = "Ответы были успешно сохранены.";
}

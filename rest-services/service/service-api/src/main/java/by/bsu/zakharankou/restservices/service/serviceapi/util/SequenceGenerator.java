package by.bsu.zakharankou.restservices.service.serviceapi.util;

public interface SequenceGenerator {

    String uniqueIdentifier();

    String secret();

    String password();
}

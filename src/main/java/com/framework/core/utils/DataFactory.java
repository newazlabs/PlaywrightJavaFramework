package com.framework.core.utils;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataFactory {

    private static final Faker faker = new Faker(Locale.US);

    private DataFactory() {}

    public static String randomEmail() {
        return faker.internet().emailAddress();
    }

    public static String randomPassword() {
        return faker.internet().password(10, 20, true, true);
    }

    public static String randomFirstName() {
        return faker.name().firstName();
    }

    public static String randomLastName() {
        return faker.name().lastName();
    }

    public static String randomFullName() {
        return faker.name().fullName();
    }

    public static String randomPhone() {
        return faker.phoneNumber().cellPhone();
    }

    public static String randomCity() {
        return faker.address().city();
    }

    public static String randomCompany() {
        return faker.company().name();
    }

    public static int randomInt(int min, int max) {
        return faker.number().numberBetween(min, max);
    }

    public static String randomUUID() {
        return java.util.UUID.randomUUID().toString();
    }

    public static String randomAlphanumeric(int length) {
        return faker.regexify("[a-zA-Z0-9]{" + length + "}");
    }
}

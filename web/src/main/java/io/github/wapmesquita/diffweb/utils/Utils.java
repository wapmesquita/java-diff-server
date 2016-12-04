package io.github.wapmesquita.diffweb.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    public static ObjectMapper getDefaultObjectMapper() {
        return objectMapper;
    }

}

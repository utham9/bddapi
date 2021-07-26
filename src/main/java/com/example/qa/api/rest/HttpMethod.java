package com.example.qa.api.rest;

import com.example.qa.api.TestException;

import java.util.Arrays;

public enum HttpMethod {
    GET("get"),
    POST("post"),
    PUT("put"),
    DELETE("delete");

    private final String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public static HttpMethod parse(String method) {
        return Arrays.stream(HttpMethod.values()).filter(httpMethod -> httpMethod.method.equalsIgnoreCase(method)).findFirst()
                .orElseThrow(() -> new TestException("%s is not a valid HTTP method", method));
    }
}

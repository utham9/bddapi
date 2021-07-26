package com.example.qa.api.rest;

import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

public class RequestConstructor {
    private final RequestSpecification requestSpecification = RestClient.given();

    public RequestSpecification generateRequest(List<Map<String, String>> maps) {
        maps.forEach(e -> configure(e));
        return requestSpecification;
    }

    private void configure(Map<String, String> specs) {
        String resourceType = specs.get("resourceType");
        RequestSpecs requestSpecs = RequestSpecs.getRequestConfig(resourceType);
        switch (requestSpecs) {
            case PARAM:
                requestSpecification.param(specs.get("key"), specs.get("value"));
            case URI:
                requestSpecification.baseUri(specs.get("value"));
        }
    }


}

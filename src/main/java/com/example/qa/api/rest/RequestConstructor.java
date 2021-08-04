package com.example.qa.api.rest;

import com.example.qa.api.properties.PropertyManager;
import com.google.common.base.Preconditions;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;

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
    RestSpecs requestSpecs = RestSpecs.getRequestConfig(resourceType);
    switch (requestSpecs) {
      case PARAM:
        requestSpecification.param(specs.get("key"), specs.get("value"));
        break;
      case URI:
        requestSpecification.baseUri(resolve(specs.get("value")));
        break;
      case HEADER:
        requestSpecification.header(new Header(specs.get("key"), specs.get("value")));
        break;
    }
  }

  private String resolve(String value) {
    if (StringUtils.startsWith(value, "$")) {
      Preconditions.checkState(
          !StringUtils.containsAny(value, " "), "Property names cannot have spaces");
      PropertyManager.PropertyKey propKey =
          PropertyManager.PropertyKey.valueOf(
              StringUtils.substringBetween(value, "$", "$").toUpperCase());
      return PropertyManager.getProperty(propKey, StringUtils.substringAfterLast(value, "$"));
    }
    return value;
  }
}

package com.example.qa.api.rest;

import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.apache.commons.codec.Charsets;

import java.util.List;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

public class ResponseValidator {

  private final Response response;

  public ResponseValidator(Response response) {
    this.response = response;
  }

  public void validate(List<Map<String, String>> maps) {
    maps.forEach(e -> validator(e));
  }

  private void validator(Map<String, String> specs) {
    String resourceType = specs.get("resourceType");
    RestSpecs requestSpecs = RestSpecs.getRequestConfig(resourceType);
    switch (requestSpecs) {
      case HEADER:
        String actualValue = response.getHeader(specs.get("key"));
        assertThat(actualValue).isEqualTo(specs.get("value"));
        break;
      case JSON_PATH:
        String jsonValue =
            response
                .jsonPath(new JsonPathConfig().charset(Charsets.UTF_8.toString()))
                .get(specs.get("key"));
        assertThat(jsonValue).isEqualTo(specs.get("value"));
        break;
      case XML_PATH:
        String xpathValue = response.xmlPath().get(specs.get("key")).toString();
        assertThat(xpathValue).isEqualTo(specs.get("value"));
        break;
    }
  }
}

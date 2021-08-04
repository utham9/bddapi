package com.example.qa.api.rest;

import com.example.qa.api.TestException;

import java.util.Arrays;

public enum RestSpecs {
  PARAM("parameter"),
  URI("uri"),
  HEADER("header"),
  JSON_PATH("jsonPath"),
  XML_PATH("xPath");

  private final String restAssuredKey;

  RestSpecs(String restAssuredKey) {
    this.restAssuredKey = restAssuredKey;
  }

  public static RestSpecs getRequestConfig(String value) {
    return Arrays.stream(RestSpecs.values())
        .filter(requestSpecs -> requestSpecs.restAssuredKey.equalsIgnoreCase(value))
        .findFirst()
        .orElseThrow(() -> new TestException("%s is not a valid rest config", value));
  }
}

package com.example.qa.api.rest;

import com.example.qa.api.TestException;

import java.util.Arrays;

public enum RequestSpecs {
  PARAM("parameter"),
  URI("uri");

  private final String restAssuredKey;

  RequestSpecs(String restAssuredKey) {
    this.restAssuredKey = restAssuredKey;
  }

  public static RequestSpecs getRequestConfig(String value) {
    return Arrays.stream(RequestSpecs.values())
        .filter(requestSpecs -> requestSpecs.restAssuredKey.equalsIgnoreCase(value))
        .findFirst()
        .orElseThrow(() -> new TestException("%s is not a valid rest config", value));
  }
}

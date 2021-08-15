package com.example.qa.api.rest;

import com.example.qa.api.models.TextConstants;
import com.example.qa.api.properties.PropertyManager;
import com.example.qa.api.reports.Reporter;
import com.example.qa.api.utils.FileOperations;
import com.google.common.base.Preconditions;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

public class RequestConstructor {
  private RequestSpecification requestSpecification;

  public RequestSpecification generateRequest(List<Map<String, String>> maps) {
    requestSpecification = RestClient.reset().given();
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
      case CONTENT_TYPE:
        requestSpecification.contentType(resolve(specs.get("value")));
        break;
      case BODY:
        File requestFile =
            FileOperations.getUniqueFileName(
                FileOperations.generateRRPairFolder(), "serviceName" + "-req.txt");
        String body = resolve(specs.get("value"));
        FileOperations.save(requestFile, body);
        Reporter.attach(
            TextConstants.RRPAIR_REPORT_PATH + requestFile.getName(), requestFile.getName());
        requestSpecification.body(body);
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

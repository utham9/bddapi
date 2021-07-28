package org.cukes.api;

import com.example.qa.api.rest.HttpMethod;
import com.example.qa.api.rest.RequestConstructor;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StepDefinitions {

  private final RequestConstructor requestConstructor = new RequestConstructor();
  private Response response;
  private RequestSpecification requestSpecification;

  @Given("request with parameters")
  public void request_with_parameters(DataTable dataTable) {
    requestSpecification = requestConstructor.generateRequest(dataTable.asMaps());
  }

  @When("api is triggered with {string} method")
  public void api_is_triggered_with_string_method(String httpMethod) {
    HttpMethod method = HttpMethod.parse(httpMethod);
    switch (method) {
      case GET:
        response = requestSpecification.get();
        break;
      case POST:
        response = requestSpecification.post();
        break;
      case PUT:
        response = requestSpecification.put();
        break;
      case DELETE:
        response = requestSpecification.delete();
        break;
    }
  }

  @Then("status code should be {int}")
  public void status_code_should_be(Integer int1) {
    response.then().statusCode(200);
  }
}

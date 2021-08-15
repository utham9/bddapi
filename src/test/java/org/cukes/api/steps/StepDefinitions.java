package org.cukes.api.steps;

import com.example.qa.api.models.TextConstants;
import com.example.qa.api.reports.Reporter;
import com.example.qa.api.rest.HttpMethod;
import com.example.qa.api.rest.RequestConstructor;
import com.example.qa.api.rest.ResponseValidator;
import com.example.qa.api.utils.FileOperations;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class StepDefinitions {

  private static final Logger log = LogManager.getLogger(StepDefinitions.class);
  private final RequestConstructor requestConstructor = new RequestConstructor();
  private final Reporter reporter = new Reporter();
  private Response response;
  private RequestSpecification requestSpecification;

  @Given("request with parameters")
  public void request_with_parameters(DataTable dataTable) {
    requestSpecification = requestConstructor.generateRequest(dataTable.asMaps());
    log.debug("request with the given parameters is generated");
    Reporter.codeBlock(dataTable.asLists());
  }

  @When("api {string} is triggered with {string} method")
  public void api_is_triggered_with_method(String serviceName, String httpMethod) {
    HttpMethod method = HttpMethod.parse(httpMethod);
    File responseFile =
        FileOperations.getUniqueFileName(
            FileOperations.generateRRPairFolder(), serviceName + "-rsp.txt");
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
    // TODO - Request logs
    FileOperations.save(responseFile, response.body().asPrettyString());
    Reporter.codeBlock(response.getHeaders());
    Reporter.attach(
        TextConstants.RRPAIR_REPORT_PATH + responseFile.getName(), responseFile.getName());
  }

  @Then("status code should be {int}")
  public void status_code_should_be(Integer int1) {
    Reporter.log(
        "Status code | Expected : %s | Actual : %s ",
        String.valueOf(int1), String.valueOf(response.statusCode()));
    response.then().statusCode(int1);
  }

  @Then("response should contain")
  public void response_should_contain(DataTable dataTable) {
    new ResponseValidator(response).validate(dataTable.asMaps());
  }
}

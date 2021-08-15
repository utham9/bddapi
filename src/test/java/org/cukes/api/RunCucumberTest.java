package org.cukes.api;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    plugin = {
      "pretty",
      "com.example.qa.api.reports.ReportListener",
      "summary",
      "rerun:target/rerun1.txt"
    },
    glue = {"org.cukes.api.steps", "com.example.qa.api.reports"},
    monochrome = true,
    dryRun = false,
    features = {"src/test/resources/features.weather"})
public class RunCucumberTest extends AbstractTestNGCucumberTests {
  @DataProvider(parallel = true)
  @Override
  public Object[][] scenarios() {
    return super.scenarios();
  }
}

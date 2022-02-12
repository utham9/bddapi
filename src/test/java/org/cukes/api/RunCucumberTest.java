package org.cukes.api;

import com.example.qa.api.reports.HtmlSummaryReporter;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    plugin = {
      "pretty",
      "summary",
      "rerun:target/rerun1.txt",
      "com.example.qa.api.reports.ReportListener"
    },
    glue = {"org.cukes.api.steps", "com.example.qa.api.reports"},
    monochrome = true,
   // dryRun = true,
    features = {"src/test/resources/features.weather"})
public class RunCucumberTest extends AbstractTestNGCucumberTests {
  @DataProvider()
  @Override
  public Object[][] scenarios() {
    return super.scenarios();
  }

  @AfterClass
  public void afterEverything() {
    HtmlSummaryReporter.flush();
  }
}

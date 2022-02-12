package org.cukes.api.steps;

import com.example.qa.api.reports.HtmlSummaryReporter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.testng.annotations.AfterClass;

public class Hooks {

    @Before
    public void beforeScenario(Scenario scenario) {
       //do nothing
    }

    @After
    public void afterScenario(Scenario scenario) {
        HtmlSummaryReporter.addToHtmlReporter(scenario);
    }


}

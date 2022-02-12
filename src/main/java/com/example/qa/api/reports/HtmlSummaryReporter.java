package com.example.qa.api.reports;

import com.example.qa.api.utils.FileOperations;
import io.cucumber.java.Scenario;

import java.io.File;

public class HtmlSummaryReporter {

  public static final String RESULTS_TABLE_HEADER =
      "<tr>\n"
          + "        <th>Tags</th>\n"
          + "        <th>Scenario</th>\n"
          + "        <th>Status</th>\n"
          + "    </tr>";
  public static final String REPORT_HEADER =
      "<!DOCTYPE html>\n"
          + "<html>\n"
          + "<head>\n"
          + "    <style>\n"
          + "table, th, td {\n"
          + "  border: 1px solid black;\n"
          + "  border-collapse: collapse;\n"
          + "}\n"
          + "</style>\n"
          + "<h1>Test Results Summary</h1></head>\n"
          + "<body>";
  public static final String REPORT_FOOTER = "\n" + "</body>\n" + "</html>";
  public static StringBuilder RESULTS_TABLE = new StringBuilder();

  public static void addToHtmlReporter(Scenario scenario) {
    RESULTS_TABLE.append("<tr>");
    RESULTS_TABLE.append("<td>" + String.join("|", scenario.getSourceTagNames()) + "</td>");
    RESULTS_TABLE.append("<td>" + scenario.getName() + "</td>");
    RESULTS_TABLE.append("<td>" + scenario.getStatus().name() + "</td>");
    RESULTS_TABLE.append("</tr>");
  }

  public static void flush() {
    StringBuilder report = new StringBuilder();
    report.append(REPORT_HEADER);
    report.append("<table>");
    report.append(RESULTS_TABLE_HEADER);
    report.append(RESULTS_TABLE);
    report.append("</table>");
    report.append(REPORT_FOOTER);
    FileOperations.save(new File("report.html"), report.toString());
  }
}

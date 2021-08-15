package com.example.qa.api.reports;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class Reporter extends ReportListener {

  public static void log(String message) {
    step.get().info(message);
  }

  public static void log(String message, String... args) {
    String format = String.format(message, args);
    step.get().info(format);
  }

  public static void codeBlock(String message) {
    step.get().info(MarkupHelper.createCodeBlock(message, CodeLanguage.JSON));
  }

  public static void codeBlock(Object message) {
    step.get().info(MarkupHelper.toTable(message));
  }

  public static void attach(String filePath, String fileName) {
    step.get().info("<a href=\"" + filePath + "\" target=\"_blank\">" + fileName + "</a>");
  }

  public static ExtentTest scenario() {
    return scenario.get();
  }
}

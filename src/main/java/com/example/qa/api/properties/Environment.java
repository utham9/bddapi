package com.example.qa.api.properties;

import com.example.qa.api.TestException;
import com.example.qa.api.utils.FileOperations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class Environment {

  private static final Logger log = LogManager.getLogger(Environment.class);

  public static String getEnvironmentPropertiesFileName() {
    String env = JVMProperties.getValues().getProperty("env");
    String fileName;
    switch (env.toUpperCase()) {
      case "PROD":
        fileName = "openweathermap-prod.properties";
        break;
      default:
        throw new TestException("Unknown environment : %s", env);
    }
    log.info("Running in environment : {}", env);
    return fileName;
  }

  public static Properties getEnvironmentProperties() {
    return FileOperations.getProperties(getEnvironmentPropertiesFileName());
  }
}

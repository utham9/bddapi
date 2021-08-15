package com.example.qa.api.utils;

import com.example.qa.api.TestException;
import com.example.qa.api.data.CacheManager;
import com.example.qa.api.models.TextConstants;
import com.example.qa.api.properties.PropertyManager;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class FileOperations {
  public static Properties getProperties(String filePath) {
    String props = readFileFromJar(filePath);
    return generateProperties(props);
  }

  private static Properties generateProperties(String props) {
    try {
      Properties properties = new Properties();
      properties.load(new StringReader(props));
      return properties;
    } catch (Exception e) {
      throw new TestException("Properties invalid");
    }
  }

  public static String readFileIntoString(String filePath) {
    String content = "";
    try {
      InputStream is = FileOperations.class.getResourceAsStream(filePath);
      content = CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));
      return content;
    } catch (Exception e) {
      System.out.println(filePath + "not found");
      throw new TestException("%s not found", filePath);
    }
  }

  public static String readFileFromJar(String filePath) {
    String content = "";
    try {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      InputStream is = classLoader.getResourceAsStream(filePath);
      content = CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));
      return content;
    } catch (Exception e) {
      System.out.println(filePath + "not found");
      throw new TestException("%s not found", filePath, e);
    }
  }

  public static File save(File out, String content) {
    try {
      Files.createParentDirs(out);
      Files.append(content, out, Charsets.UTF_8);
      return out;
    } catch (Exception e) {
      throw new TestException(e.getMessage(), e);
    }
  }

  public static String generateReportFolder() {
    if (!CacheManager.getInstance().contains(TextConstants.REPORT_PATH_KEY)) {
      String reportFolder =
          PropertyManager.getProperty(PropertyManager.PropertyKey.EXTENT, "basefolder.name");
      String dateTimeFormat =
          PropertyManager.getProperty(
              PropertyManager.PropertyKey.EXTENT, "basefolder.datetimepattern");
      String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimeFormat));
      String reportPath = reportFolder + "-" + date;
      createDirectory(reportPath);
      CacheManager.getInstance().put(TextConstants.REPORT_PATH_KEY, reportPath);
      return reportPath;
    }
    return (String) CacheManager.getInstance().get(TextConstants.REPORT_PATH_KEY);
  }

  public static String generateRRPairFolder() {
    String rrPairPath = generateReportFolder() + TextConstants.RRPAIR_FOLDER_NAME;
    createDirectory(rrPairPath);
    return rrPairPath;
  }

  public static void createDirectory(String paths) {
    File directory = new File(paths);
    if (!directory.exists()) {
      directory.mkdirs();
    }
  }

  public static File getUniqueFileName(String folderName, String searchedFilename) {
    int num = 1;
    String extension = "." + StringUtils.substringAfterLast(searchedFilename, ".");
    String filename = searchedFilename.substring(0, searchedFilename.lastIndexOf("."));
    File file = new File(folderName, searchedFilename);
    while (file.exists()) {
      searchedFilename = filename + "-" + (num++) + extension;
      file = new File(folderName, searchedFilename);
    }
    return file;
  }
}

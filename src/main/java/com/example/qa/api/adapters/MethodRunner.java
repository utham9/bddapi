package com.example.qa.api.adapters;

import com.example.qa.api.utils.FileOperations;
import org.testng.annotations.Test;

public class MethodRunner {

  public <T> Object execute(Class<T> tClass, String name, Class<?>... params) throws Exception {
    T object = tClass.newInstance();
    return tClass.getMethod(name, params).invoke(object, null);
  }

  @Test
  public void s() throws Exception {
    //        FileOperations.generateRRPairFolder();
    String generateRRPairFolder =
        (String) execute(FileOperations.class, "generateRRPairFolder", null);
    System.out.println(generateRRPairFolder);
  }
}

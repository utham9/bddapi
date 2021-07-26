package com.example.qa.api.utils;

import com.example.qa.api.TestException;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
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
}

package com.jmeplay.core.utils;

import com.jmeplay.core.JMEPlayEditorResources;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

/**
 * Store and load settings for whole application
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class Settings {

    private Properties settings;

    @PostConstruct
    private void init() {
        settings = new Properties();
        loadSettings();
    }

    @PreDestroy
    private void destroy() {
        writeSettings();
    }

    private void loadSettings() {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(JMEPlayEditorResources.SETTINGSFILE);
            settings.loadFromXML(inputStream);
            inputStream.close();
        } catch (IOException e) {
        }
    }

    private void writeSettings() {
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(JMEPlayEditorResources.SETTINGSFILE);
            settings.storeToXML(outputStream, "Settings for whole application");
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't write settings file", e);
        }
    }

    private String getProperty(String key) {
        String option = settings.getProperty(key);
        return option == null || option.equals("null") ? null : option;
    }

    public void setOption(String key, Boolean value) {
        settings.setProperty(key, "" + value);
    }

    public void setOption(String key, String value) {
        settings.setProperty(key, value);
    }

    public void setOption(String key, Integer value) {
        settings.setProperty(key, "" + value);
    }

    public void setOption(String key, Double value) {
        settings.setProperty(key, "" + value);
    }

    public void removeOption(String key) {
        settings.remove(key);
    }

    public Boolean getOptionAsBoolean(String key, Boolean defaultValue) {
        String option = getProperty(key);
        if (option == null) {
            setOption(key, "" + defaultValue);
            return defaultValue;
        }
        return Boolean.parseBoolean(option);
    }

    public String getOptionAsStringFromEditor(String key, String defaultValue) {
        // TODO read from editor settings
        return defaultValue;
    }

    public String getOptionAsString(String key, String defaultValue) {
        String option = getProperty(key);
        if (option == null) {
            setOption(key, defaultValue);
            return defaultValue;
        }
        return option;
    }

    public Integer getOptionIntegerFromEditor(String key, Integer defaultValue) {
        // TODO read from editor settings
        return defaultValue;
    }

    public Integer getOptionInteger(String key, Integer defaultValue) {
        String option = getProperty(key);
        if (option == null) {
            setOption(key, "" + defaultValue);
            return defaultValue;
        }
        return Integer.parseInt(option);
    }

    public Double getOptionDoubleFromEditor(String key, Double defaultValue) {
        // TODO read from editor settings
        return defaultValue;
    }

    public Double getOptionDouble(String key, Double defaultValue) {
        String option = getProperty(key);
        if (option == null) {
            setOption(key, "" + defaultValue);
            return defaultValue;
        }
        return Double.parseDouble(option);
    }
}

package com.socialnetwork.config;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Config instance;
    private final InputStream configLocation;

    private Config() {
        configLocation = getClass().getClassLoader().getResourceAsStream("config.properties");
    }

    public Properties getProperties() {
        Properties properties=new Properties();
        try {
            properties.load(configLocation);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config properties");
        }
    }

    public static Config getInstance() {
        if (instance == null)
            instance = new Config();
        return instance;
    }
}

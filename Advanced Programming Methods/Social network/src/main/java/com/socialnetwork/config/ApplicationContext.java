package com.socialnetwork.config;

import com.socialnetwork.domain.entities.User;

import java.util.Properties;

public class ApplicationContext {
    private static ApplicationContext instance;
    private final Properties properties;
    private User user;

    private ApplicationContext() {
        properties = Config.getInstance().getProperties();
    }

    public static ApplicationContext getInstance() {
        if (instance == null)
            instance = new ApplicationContext();
        return instance;
    }

    public Properties getProperties() {
        return properties;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

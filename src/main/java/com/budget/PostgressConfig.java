package com.budget;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PostgressConfig {

    private static PostgressConfig instance = null;
    private String url, user, password;

    private PostgressConfig(){
    }

    public static PostgressConfig getInstance() {
        if (instance == null)
            instance = new PostgressConfig();
        return instance;
    }

    public void load() {
        Properties p = new Properties();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("/app.properties");
        try {
            p.load(inputStream);
            url = p.getProperty("url");
            user = p.getProperty("user");
            password = p.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}

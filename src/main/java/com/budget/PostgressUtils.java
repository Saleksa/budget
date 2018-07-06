package com.budget;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgressUtils {

    private static PostgressUtils instance = null;
    private String url, user, password;

    private PostgressUtils(){
    }

    public static PostgressUtils getInstance() {
        if (instance == null)
            instance = new PostgressUtils();
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

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
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

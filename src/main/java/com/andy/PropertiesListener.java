package com.andy;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        loadProperties();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void loadProperties() {
        InputStream in = PropertiesListener.class
                .getClassLoader()
                .getResourceAsStream("/important.properties");
        Properties p = new Properties();
        try {
            p.load(in);
            Constant.QQ_APP_ID = p.getProperty("QQ_APP_ID");
            Constant.QQ_APP_KEY = p.getProperty("QQ_APP_KEY");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

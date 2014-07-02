package se.leinonen.drugweb.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * Created by leinonen on 2014-03-27.
 */
public class Settings {

    static final Logger logger = Logger.getLogger(Settings.class);

    private static final String SETTINGS_FILE = "settings.properties";

    private static Settings settings;

    private Properties properties = new Properties();

    public static Settings getInstance() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }

    private Settings() {
        try {
            loadSettingsAsResource();

            if (getBoolean("config.useExternalFile")) {
                loadSettingsFromFile(getString("config.externalFile"));
            }

        } catch (FileNotFoundException e) {
            logger.error("Settings file " + SETTINGS_FILE + " not found!");
        } catch (IOException e) {
            logger.error("Error loading Settings file " + SETTINGS_FILE);
        }
    }

    public void loadSettingsFromFile(String externalFile) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(externalFile));
        properties.clear();
        properties.load(fileInputStream);
        logger.info("Loaded external Settings file " + externalFile);
    }

    private void loadSettingsAsResource() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(SETTINGS_FILE);
        properties.load(inputStream);
        logger.info("Successfully loaded Settings file " + SETTINGS_FILE);
    }

    public String getString(String name) {
        String value = getProperty(name, "");
        logger.info("Loaded String property " + name + " -> " + value);
        return value;
    }

    public String getString(String name, String def) {
        String value = getProperty(name, def);
        logger.info("Loaded String property " + name + " -> " + value);
        return value;
    }

    public boolean getBoolean(String name) {
        boolean value = Boolean.parseBoolean(getProperty(name, "false"));
        logger.info("Loaded Boolean property " + name + " -> " + value);
        return value;
    }

    public int getInteger(String name) {
        int value = Integer.parseInt(getProperty(name, "0"));
        logger.info("Loaded Integer property " + name + " -> " + value);
        return value;
    }

    private String getProperty(String name, String defaultValue) {
        return properties.getProperty(name, defaultValue).trim();
    }
}

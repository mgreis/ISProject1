package is.project1.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Helper class for the configuration file.
 *
 * @author Flávio J. Saraiva
 */
public class Config {

    public static final String DEFAULT_USER = "user";
    public static final String DEFAULT_PASS = "pass";
    public static final String DEFAULT_DIR = ".";
    public static final String DEFAULT_TOPIC_FACTORY = "jms/RemoteConnectionFactory";
    public static final String DEFAULT_TOPIC_NAME = "IS/Project1/WebCrawlerTopic";

    /**
     * Configuration file.
     *
     * The file is in the current working directory.
     */
    public static final String CONFIG_FILE = "config.properties";

    /**
     * Reads the configuration file and joins them with the default properties.
     *
     * @param defaults default properties.
     * @return the properties.
     * @throws IOException if an error occurred when reading.
     */
    public static Properties load(Properties defaults) throws IOException {
        assert (defaults != null);
        final Properties properties = new Properties(defaults);
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            properties.load(reader);
        } catch (FileNotFoundException ex) {
            // use defaults
        }
        return properties;
    }

    /**
     * Reads the configuration file and joins them with the default properties.
     *
     * @return the properties.
     * @throws IOException if an error occurred when reading.
     */
    public static Properties load() throws IOException {
        return load(defaultProperties());
    }

    /**
     * Writes the configuration file with the provided properties.
     *
     * @param properties the properties.
     * @throws IOException if an error occurred when writing.
     */
    public static void store(Properties properties) throws IOException {
        assert (properties != null);
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            properties.store(writer, "Project #1 - IS 2015/16 – 1st Semester MEI");
        }
    }

    /**
     * Returns the default properties.
     *
     * @return Default properties.
     */
    public static Properties defaultProperties() {
        final Properties defaults = new Properties();
        defaults.setProperty("user", DEFAULT_USER);
        defaults.setProperty("pass", DEFAULT_PASS);
        defaults.setProperty("dir", DEFAULT_DIR);
        defaults.setProperty("topicFactory", DEFAULT_TOPIC_FACTORY);
        defaults.setProperty("topicName", DEFAULT_TOPIC_NAME);
        return defaults;
    }
}

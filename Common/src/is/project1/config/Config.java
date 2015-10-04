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

    /**
     * Configuration file.
     *
     * The file is in the current working directory.
     */
    public static final String CONFIG_FILE = "config.properties";

    /**
     * Reads the configuration file properties.
     *
     * @return the properties.
     * @throws IOException if an error occurred when reading.
     */
    public static Properties load() throws IOException {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            final Properties properties = new Properties();
            properties.load(reader);
            return properties;
        } catch (FileNotFoundException ex) {
            return new Properties(); // use defaults
        }
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

}

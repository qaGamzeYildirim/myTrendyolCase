package propManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesReader {
    static String filePath = "config.properties";

    public static Map<String, String> readPropertiesAsMap() {
        Map<String, String> map = new HashMap<>();
        Properties properties = new Properties();

        try (InputStream input = PropertiesReader.class.getClassLoader().getResourceAsStream(filePath)) {
            properties.load(input);
            for (String key : properties.stringPropertyNames()) {
                map.put(key, properties.getProperty(key));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return map;
    }

}


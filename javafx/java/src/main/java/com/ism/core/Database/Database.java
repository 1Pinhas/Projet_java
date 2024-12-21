package com.ism.core.Database;

import org.yaml.snakeyaml.Yaml;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.InputStream;
import java.util.Map;

public class Database {

    public static String getActiveDatabase(String key, String key2) {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = Database.class.getResourceAsStream("/database.yml")) {
            Map<String, Object> config = yaml.load(inputStream);
            return (String) ((Map<String, Object>) config.get(key)).get(key2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static EntityManagerFactory getEntityManagerFactory(String key, String key2) {
        String activeDb = getActiveDatabase(key,key2);

        if ("postgres".equalsIgnoreCase(activeDb)) {
            return Persistence.createEntityManagerFactory("DETTES_POSTGRES");
        } else if ("mysql".equalsIgnoreCase(activeDb)) {
            return Persistence.createEntityManagerFactory("DETTES_MYSQL");
        } else {
            throw new IllegalArgumentException("Database configuration n'est pas reconnue: " + activeDb);
        }
    }
}


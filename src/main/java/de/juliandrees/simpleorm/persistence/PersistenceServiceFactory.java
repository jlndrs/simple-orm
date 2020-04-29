package de.juliandrees.simpleorm.persistence;

import com.google.gson.Gson;
import de.juliandrees.simpleorm.EntityManager;
import de.juliandrees.simpleorm.persistence.sql.AbstractSqlConnection;
import de.juliandrees.simpleorm.persistence.sql.PostgreSqlConnection;
import de.juliandrees.simpleorm.persistence.sql.SqlConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 27.04.2020
 */
public final class PersistenceServiceFactory {

    private static final HashMap<String, Class<? extends AbstractSqlConnection>> connections = new HashMap<>();

    static {
        connections.put("postgresql", PostgreSqlConnection.class);
    }

    private PersistenceServiceFactory() { }

    public static PersistenceService newInstance(EntityManager entityManager) {
        return newInstance(entityManager, DefaultPersistenceService.class);
    }

    public static <T extends PersistenceService> T newInstance(EntityManager entityManager, Class<T> persistenceClass) {
        try {
            PersistenceConfig config = PersistenceServiceFactory.getConfig();
            SqlConnection sqlConnection = newSqlConnection(config.getJdbcType(), config);
            sqlConnection.openConnection();

            T persistenceService = newPersistenceService(persistenceClass, entityManager, sqlConnection);
            persistenceService.onInitialize();
            return persistenceService;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static InputStream getConfigAsStream() {
        ClassLoader classLoader = PersistenceServiceFactory.class.getClassLoader();
        if (classLoader == null) {
            throw new IllegalStateException("Can not get class loader for class " + PersistenceServiceFactory.class.getName());
        }
        InputStream resource = classLoader.getResourceAsStream("persistence.json");
        if (resource == null) {
            throw new NullPointerException("no persistence.json found on classpath.");
        }
        return resource;
    }

    private static PersistenceConfig getConfig() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (InputStream in = getConfigAsStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            return new Gson().fromJson(reader, PersistenceConfig.class);
        }
    }

    private static SqlConnection newSqlConnection(String jdbcType, PersistenceConfig config)  {
        Class<? extends AbstractSqlConnection> sqlConnection = connections.get(jdbcType.toLowerCase());
        if (sqlConnection == null) {
            throw new IllegalArgumentException("jdbc type " + jdbcType + " is not valid.");
        }
        SqlConnection connection = null;
        try {
            connection = sqlConnection.getConstructor(PersistenceConfig.class).newInstance(config);
        } catch (Exception e) {
            throw new IllegalStateException("can not instantiate sql connection");
        }
        connection.onInitialize();
        return connection;
    }

    static <T extends PersistenceService> T newPersistenceService(Class<T> persistenceServiceClass,
                                                            EntityManager entityManager, SqlConnection sqlConnection) throws NoSuchMethodException {
        try {
            Constructor<T> constructor = persistenceServiceClass.getConstructor(EntityManager.class, SqlConnection.class);
            return constructor.newInstance(entityManager, sqlConnection);
        } catch (Exception e) {
            throw new NoSuchMethodException("required constructor does not exists");
        }
    }

}

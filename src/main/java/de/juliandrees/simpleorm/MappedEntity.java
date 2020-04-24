package de.juliandrees.simpleorm;

import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
@Getter
public class MappedEntity {

    private final Class<?> entityClass;
    private final HashMap<String, MappedSetter> mappedSetters = new HashMap<>();

    public MappedEntity(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public void addFieldMapping(Field field, Method setter, Class<?> setterType) {
        mappedSetters.put(field.getName(), new MappedSetter(setter, setterType));
    }

    /**
     * Ein gemappter Setter der Klasse.
     * Enthält die Methode, die zum setzen zu invoken ist, sowie den Typ des Parameters, der übergeben werden muss.
     */
    @Getter
    public static class MappedSetter {

        private final Method setter;
        private final Class<?> setterType;

        public MappedSetter(Method setter, Class<?> setterType) {
            this.setter = setter;
            this.setterType = setterType;
        }
    }
}

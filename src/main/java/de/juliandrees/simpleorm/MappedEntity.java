package de.juliandrees.simpleorm;

import de.juliandrees.simpleorm.annotation.EntityMapping;
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
    private final String entityName;
    private final HashMap<String, MappedSetter> mappedSetters = new HashMap<>();
    private MappedSetter primaryKeySetter;

    public MappedEntity(Class<?> entityClass, String entityName) {
        this.entityClass = entityClass;
        this.entityName = entityName;
    }

    public void addFieldMapping(Field field, Method setter, Class<?> setterType, boolean primaryKey) {
        MappedSetter mappedSetter = new MappedSetter(setter, setterType);
        if (primaryKey) {
            primaryKeySetter = mappedSetter;
        }
        mappedSetters.put(field.getName(), mappedSetter);
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

        public boolean isDefaultType() {
            return setterType.getPackageName().equalsIgnoreCase("java.lang");
        }

        public boolean isEntityReference() {
            EntityMapping entityMapping = setterType.getAnnotation(EntityMapping.class);
            return !isDefaultType() && entityMapping != null;
        }
    }
}

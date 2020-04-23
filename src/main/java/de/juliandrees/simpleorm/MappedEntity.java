package de.juliandrees.simpleorm;

import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
@Getter
public class MappedEntity {

    private final Class<?> entityClass;
    private final List<MappedSetter> mappedSetters = new ArrayList<>();

    public MappedEntity(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public void addSetter(Field field, Method setter, Class<?> setterType) {
        mappedSetters.add(new MappedSetter(field, setter, setterType));
    }

    @Getter
    public static class MappedSetter {

        private final Field field;
        private final Method setter;
        private final Class<?> setterType;

        public MappedSetter(Field field, Method setter, Class<?> setterType) {
            this.field = field;
            this.setter = setter;
            this.setterType = setterType;
        }
    }
}

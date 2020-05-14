package de.juliandrees.simpleorm.entity.property;

import de.juliandrees.simpleorm.annotation.EnumMapping;
import de.juliandrees.simpleorm.entity.FieldMapping;
import de.juliandrees.simpleorm.persistence.EntityPersistence;
import de.juliandrees.simpleorm.type.EnumType;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 13.05.2020
 */
public class EnumPropertyMapping extends PropertyMapping {

    @Getter(AccessLevel.PROTECTED)
    private final List<EnumConstant> constants = new ArrayList<>();
    private EnumType enumType;

    public EnumPropertyMapping(FieldMapping fieldMapping) {
        super(fieldMapping);
    }

    @Override
    public void onInitialize() {
        EnumMapping optional = getAnnotation(EnumMapping.class);
        if (optional == null) {
            throw new NullPointerException("@EnumMapping annotation is required!");
        }
        enumType = optional.type();
        Class<? extends Enum<?>> enumClass = optional.enumClass();
        for (Enum<?> constant : enumClass.getEnumConstants()) {
            constants.add(new EnumConstant(constant));
        }
    }

    @Override
    public Object getStorableValue(Object value, EntityPersistence persistence) {
        if (value instanceof Enum<?>) {
            final Enum<?> enumeration = (Enum<?>) value;
            switch (enumType) {
                case NAME -> value = enumeration.name();
                case ORDINAL -> value = enumeration.ordinal();
            }
        }
        return value;
    }

    @Override
    public Object getEntityValue(Object value, EntityPersistence persistence) {
        if (value == null) {
            return null;
        }

        Function<EnumConstant, Object> consumer = (constant) -> {
            if (enumType == EnumType.NAME) {
                return constant.getName();
            } else {
                return constant.getOrdinal();
            }
        };
        for (EnumConstant constant : this.constants) {
            Object enumValue = consumer.apply(constant);
            if (value.equals(enumValue)) {
                return constant.getConstant();
            }
        }
        return null;
    }

    @Override
    public Class<?> getFieldType() {
        if (enumType == EnumType.NAME) {
            return String.class;
        } else {
            return int.class;
        }
    }

    private static class EnumConstant {

        private final Enum<?> constant;
        private final String name;
        private final int ordinal;

        EnumConstant(Enum<?> constant) {
            this.constant = constant;
            this.name = constant.name();
            this.ordinal = constant.ordinal();
        }

        public Enum<?> getConstant() {
            return constant;
        }

        public String getName() {
            return name;
        }

        public int getOrdinal() {
            return ordinal;
        }
    }
}

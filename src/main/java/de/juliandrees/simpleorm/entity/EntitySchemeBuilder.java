package de.juliandrees.simpleorm.entity;

import de.juliandrees.simpleorm.annotation.ColumnMapping;
import de.juliandrees.simpleorm.annotation.EntityMapping;
import de.juliandrees.simpleorm.annotation.EnumMapping;
import de.juliandrees.simpleorm.annotation.PrimaryKeyColumn;
import de.juliandrees.simpleorm.annotation.SuperclassMapping;
import de.juliandrees.simpleorm.annotation.handler.AnnotationHandler;
import de.juliandrees.simpleorm.annotation.handler.EnumMappingHandler;
import de.juliandrees.simpleorm.annotation.handler.PrimaryKeyColumnHandler;
import de.juliandrees.simpleorm.entity.property.EntityReferencePropertyMapping;
import de.juliandrees.simpleorm.entity.property.EnumPropertyMapping;
import de.juliandrees.simpleorm.entity.property.PropertyMapping;
import de.juliandrees.simpleorm.exception.MethodMappingException;
import de.juliandrees.simpleorm.exception.NoPrimaryKeyException;
import de.juliandrees.simpleorm.exception.WrongAnnotationUsageException;
import de.juliandrees.simpleorm.type.MethodPrefix;
import de.juliandrees.simpleorm.type.PropertyType;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
class EntitySchemeBuilder {

    private HashMap<Class<?>, List<Class<?>>> classMapping = new HashMap<>();
    private List<AnnotationHandler<?>> annotationHandlers = new ArrayList<>();

    public EntitySchemeBuilder() {
        annotationHandlers.add(new EnumMappingHandler());
        annotationHandlers.add(new PrimaryKeyColumnHandler());
    }

    public EntityScheme newEntityScheme(Class<?> clazz) throws WrongAnnotationUsageException {
        classMapping.put(clazz, this.getClassHierarchy(clazz));
        EntityScheme entityScheme = new EntityScheme(clazz, this.determineEntityName(clazz));
        for (Method method : clazz.getMethods()) {
            if (!isGetter(method.getName()) || !this.isMappedColumn(method)) {
                continue;
            }
            PropertyMapping propertyMapping = newPropertyMapping(clazz, method);
            String databaseColumn = getMappedFieldName(propertyMapping.getFieldMapping().getField(), propertyMapping.getFieldMapping().getGetter());
            PrimaryKeyColumn pkAnnotation = propertyMapping.getFieldMapping().getGetter().getAnnotation(PrimaryKeyColumn.class);

            entityScheme.addMapping(databaseColumn, propertyMapping, pkAnnotation != null);
        }
        checkPrimaryKey(entityScheme);
        for (AnnotationHandler<?> handler : annotationHandlers) {
            Class<? extends Annotation> annotationType = handler.getAnnotationType();
            for (PropertyMapping propertyMapping : entityScheme.getPropertyMappings().values()) {
                handler.handle(propertyMapping.getFieldMapping().getGetter(), annotationType);
            }
        }
        return entityScheme;
    }

    /**
     * Nimmt die set-Methode, die als erste passende in der Methodenliste angeführt wird.
     * Methoden überladen funktioniert bei diesem Prinzip nicht und sollte
     * bei ORMs auf nicht angewendet werden.
     *
     * @param getter die passende get-Methode
     * @param clazz die Klasse, aus der die Methode geladen werden soll
     * @return ein entsprechender Getter
     */
    public Method getSetter(Method getter, Class<?> clazz) {
        Optional<Method> optionalGetter = getMethod(this.getFieldName(getter), MethodPrefix.SET, clazz);
        if (optionalGetter.isEmpty()) {
            throw new MethodMappingException("No setter found for getter " + getter.getName() + " (" + clazz.getSimpleName() + ")");
        }
        return optionalGetter.get();
    }

    /**
     * Ermittelt anhand eines getters das entsprechende Feld, auf welches
     * der Getter vermutlich zeigt.
     *
     * @param getter die passende get-Methode
     * @param clazz die Klasse, aus der das Feld geladen werden soll
     * @return ein entsprechendes Feld
     */
    public Field getField(Method getter, Class<?> clazz) {
        String fieldName = this.getFieldName(getter);
        List<Class<?>> classes = classMapping.get(clazz);

        Field field = null;
        for (Class<?> mappingClass : classes) {
            try {
                field = mappingClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) { }
        }

        if (field == null) {
            throw new MethodMappingException("No field found for getter " + getter.getName() + " (" + clazz.getSimpleName() + ")");
        }
        return field;
    }

    public boolean isMappedColumn(Method method) {
        for (Class<? extends Annotation> annotationClass : getAnnotations()) {
            Annotation annotation = method.getAnnotation(annotationClass);
            if (annotation != null) {
                return true;
            }
        }
        return false;
    }

    private List<Class<? extends Annotation>> getAnnotations() {
        return Arrays.asList(ColumnMapping.class, EnumMapping.class);
    }

    public String getFieldName(Method getter) {
        if (!isGetter(getter.getName())) {
            throw new IllegalArgumentException("requires getter, but another method type is given.");
        }

        final String getterName = getter.getName();
        String fieldName = getterName.substring(getMethodPrefix(getterName).name().length());
        return Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    public Optional<Method> getMethod(String fieldName, MethodPrefix methodPrefix, Class<?> clazz) {
        final String methodName = methodPrefix.name().toLowerCase() + fieldName; // case-sensitive doesnt matter
        for (Method method : clazz.getMethods()) {
            if (method.getName().equalsIgnoreCase(methodName)) {
                return Optional.of(method);
            }
        }
        return Optional.empty();
    }

    public String determineEntityName(Class<?> clazz) {
        EntityMapping annotation = clazz.getAnnotation(EntityMapping.class);
        if (annotation == null) {
            throw new IllegalArgumentException("Given class has no entity mapping");
        }
        String entityName = annotation.value();
        if (StringUtils.isBlank(entityName)) {
            entityName = clazz.getSimpleName();
        }
        return entityName;
    }

    public List<Class<?>> getClassHierarchy(Class<?> entityClass) {
        Objects.requireNonNull(entityClass);
        List<Class<?>> classes = new ArrayList<>();
        classes.add(entityClass);
        Class<?> superclass = entityClass;
        while ((superclass = superclass.getSuperclass()) != null) {
            if (superclass.getAnnotation(EntityMapping.class) != null || superclass.getAnnotation(SuperclassMapping.class) != null) {
                classes.add(superclass);
            } else {
                break;
            }
        }
        return classes;
    }

    private PropertyType determinePropertyType(Class<?> fieldType) {
        PropertyType propertyType;
        if (fieldType.getPackageName().equalsIgnoreCase("java.lang")) {
            propertyType = PropertyType.JAVA_DEFAULT;
        } else if (fieldType.isEnum()) {
            propertyType = PropertyType.ENUMERATION;
        } else {
            propertyType = PropertyType.ENTITY_REFERENCE;
        }
        return propertyType;
    }

    private String getMappedFieldName(Field field, Method getter) {
        ColumnMapping columnMapping = getter.getAnnotation(ColumnMapping.class);
        String mapped;
        if (StringUtils.isBlank(columnMapping.value())) {
            mapped = field.getName();
        } else {
            mapped = columnMapping.value();
        }
        return mapped;
    }

    final void checkPrimaryKey(EntityScheme entityScheme) {
        if (entityScheme.getPrimaryKeyMapping() == null) {
            throw new NoPrimaryKeyException(entityScheme.getEntityName());
        }
    }

    final FieldMapping newFieldMapping(Class<?> entityClass, Method getter) {
        Field field = this.getField(getter, entityClass);
        Method setter = this.getSetter(getter, entityClass);
        PropertyType propertyType = determinePropertyType(field.getType());
        return new FieldMapping(field, getter, setter, propertyType);
    }

    final PropertyMapping newPropertyMapping(Class<?> entityClass, Method getter) {
        FieldMapping fieldMapping = newFieldMapping(entityClass, getter);
        PropertyMapping propertyMapping;
        switch (fieldMapping.getPropertyType()) {
            default:
            case JAVA_DEFAULT:
                propertyMapping = new PropertyMapping(fieldMapping) {
                    @Override
                    public void onInitialize() { }
                };
                break;
            case ENTITY_REFERENCE:
                propertyMapping = new EntityReferencePropertyMapping(fieldMapping);
                break;
            case ENUMERATION:
                propertyMapping = new EnumPropertyMapping(fieldMapping);
                break;
        }
        propertyMapping.onInitialize();
        return propertyMapping;
    }

    final boolean isGetter(String methodName) {
        return methodName.toLowerCase().startsWith(MethodPrefix.GET.name().toLowerCase()) ||
                       methodName.toLowerCase().startsWith(MethodPrefix.IS.name().toLowerCase());
    }

    final MethodPrefix getMethodPrefix(String methodName) {
        if (methodName.toLowerCase().startsWith(MethodPrefix.GET.name().toLowerCase())) {
            return MethodPrefix.GET;
        } else if (methodName.toLowerCase().startsWith(MethodPrefix.IS.name().toLowerCase())) {
            return MethodPrefix.IS;
        } else {
            return MethodPrefix.SET;
        }
    }

}

package de.juliandrees.simpleorm.entity;

import de.juliandrees.simpleorm.model.BaseEntity;
import de.juliandrees.simpleorm.model.TestEntity;
import de.juliandrees.simpleorm.type.MethodPrefix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class EntitySchemeBuilderTest {

    private EntitySchemeBuilder schemeBuilder;

    @BeforeEach
    public void before() {
        this.schemeBuilder = new EntitySchemeBuilder();
    }

    @Test
    public void getExistingMethodTest() {
        Optional<Method> optionalMethod = schemeBuilder.getMethod("id", MethodPrefix.GET, TestEntity.class);
        assertTrue(optionalMethod.isPresent());
    }

    @Test
    public void isExistingMethodTest() {
        Optional<Method> optionalMethod = schemeBuilder.getMethod("compatible", MethodPrefix.IS, TestEntity.class);
        assertTrue(optionalMethod.isPresent());
    }

    @Test
    public void getNonExistingMethodTest() {
        Optional<Method> optionalMethod = schemeBuilder.getMethod("nonExistingField", MethodPrefix.GET, TestEntity.class);
        assertTrue(optionalMethod.isEmpty());
    }

    @Test
    public void onFieldNameExtractionTest() throws Exception {
        String fieldName = schemeBuilder.getFieldName(BaseEntity.class.getDeclaredMethod("getId"));
        assertEquals("id", fieldName);
    }

    @Test
    public void onSetterFieldNameExtractionTest() {
        try {
            String fieldName = schemeBuilder.getFieldName(BaseEntity.class.getDeclaredMethod("setId", Long.class));
            fail("Expected an exception, but none is thrown");
        } catch (Exception ignored) { }
    }

    @Test
    public void getClassHierarchyTest() {
        List<Class<?>> classes = schemeBuilder.getClassHierarchy(TestEntity.class);
        assertEquals(2, classes.size());
    }

}

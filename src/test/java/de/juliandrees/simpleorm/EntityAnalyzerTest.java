package de.juliandrees.simpleorm;

import de.juliandrees.simpleorm.model.BaseEntity;
import de.juliandrees.simpleorm.model.TestEntity;
import de.juliandrees.simpleorm.type.MethodPrefix;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.Assert.*;

public class EntityAnalyzerTest {

    private EntityAnalyzer analyzer;

    @Before
    public void before() {
        this.analyzer = new EntityAnalyzer();
    }

    @Test
    public void getExistingMethodTest() {
        Optional<Method> optionalMethod = analyzer.getMethod("id", MethodPrefix.GET, TestEntity.class);
        assertTrue(optionalMethod.isPresent());
    }

    @Test
    public void isExistingMethodTest() {
        Optional<Method> optionalMethod = analyzer.getMethod("compatible", MethodPrefix.IS, TestEntity.class);
        assertTrue(optionalMethod.isPresent());
    }

    @Test
    public void getNonExistingMethodTest() {
        Optional<Method> optionalMethod = analyzer.getMethod("nonExistingField", MethodPrefix.GET, TestEntity.class);
        assertTrue(optionalMethod.isEmpty());
    }

    @Test
    public void onFieldNameExtractionTest() throws Exception {
        String fieldName = analyzer.getFieldName(BaseEntity.class.getDeclaredMethod("getId"));
        assertEquals("id", fieldName);
    }

    @Test
    public void onSetterFieldNameExtractionTest() {
        try {
            String fieldName = analyzer.getFieldName(BaseEntity.class.getDeclaredMethod("setId", Long.class));
            fail("Expected an exception, but none is thrown");
        } catch (Exception ex) { }
    }

}

package de.juliandrees.simpleorm.entity.property;

import de.juliandrees.simpleorm.annotation.EnumMapping;
import de.juliandrees.simpleorm.entity.FieldMapping;
import de.juliandrees.simpleorm.model.TestEntity;
import de.juliandrees.simpleorm.model.TransactionType;
import de.juliandrees.simpleorm.persistence.EntityPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 14.05.2020
 */
public class EnumPropertyMappingTest {

    private EnumPropertyMapping propertyMapping;
    private final EntityPersistence entityPersistence = mock(EntityPersistence.class);

    @BeforeEach
    public void before() throws Exception {
        FieldMapping fieldMapping = new FieldMapping(TestEntity.class.getDeclaredField("transactionType"),
                TestEntity.class.getDeclaredMethod("getTransactionType"),
                TestEntity.class.getDeclaredMethod("setTransactionType",
                        TransactionType.class), null);
        propertyMapping = new EnumPropertyMapping(fieldMapping);
        propertyMapping.onInitialize();
    }

    @Test
    public void onObjectToEnumTest() {
        Object value = propertyMapping.getEntityValue("SELL", entityPersistence);
        assertEquals(TransactionType.SELL, value);
    }

    @Test
    public void onEnumToObjectTest() {
        Object value = propertyMapping.getStorableValue(TransactionType.BUY, entityPersistence);
        assertEquals("BUY", value);
    }

    @Test
    public void onEnumMappingAnnotationTest() {
        assertNotNull(propertyMapping.getAnnotation(EnumMapping.class));
    }

    @Test
    public void onEnumConstantsTest() {
        assertEquals(2, propertyMapping.getConstants().size());
    }

}

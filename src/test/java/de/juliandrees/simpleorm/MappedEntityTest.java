package de.juliandrees.simpleorm;

import de.juliandrees.simpleorm.model.TestEntity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 24.04.2020
 */
public class MappedEntityTest {

    private MappedEntity mappedEntity;

    @Before
    public void before() {
        mappedEntity = new MappedEntity(TestEntity.class, "testEntity");
    }

    @Test
    public void onDefaultSetterTypeTest() {
        MappedEntity.MappedSetter mappedSetter = new MappedEntity.MappedSetter(null, Long.class);
        assertTrue(mappedSetter.isDefaultType());
    }

    @Test
    public void onEntitySetterTypeTest() {
        MappedEntity.MappedSetter mappedSetter = new MappedEntity.MappedSetter(null, TestEntity.class);
        assertTrue(mappedSetter.isEntityReference());
    }

}

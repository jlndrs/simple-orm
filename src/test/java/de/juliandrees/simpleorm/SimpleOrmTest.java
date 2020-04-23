package de.juliandrees.simpleorm;

import de.juliandrees.simpleorm.model.TestEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
public class SimpleOrmTest {

    @Test
    public void run() {
        EntityManager manager = EntityManager.scanPackage("de.juliandrees.simpleorm", true);
    }

    @Test
    public void testFieldNameExtraction() throws NoSuchMethodException {
        EntityAnalyzer analyzer = new EntityAnalyzer();
        String fieldName = analyzer.getFieldName(TestEntity.class.getDeclaredMethod("getId"));
        assertEquals("id", fieldName);
    }

}

package de.juliandrees.simpleorm;

import org.junit.Test;

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

}

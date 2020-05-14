package de.juliandrees.simpleorm.entity.property;

import de.juliandrees.simpleorm.entity.EntityScheme;
import de.juliandrees.simpleorm.entity.FieldMapping;
import de.juliandrees.simpleorm.persistence.EntityPersistence;
import de.juliandrees.simpleorm.persistence.query.select.SelectQueryFactory;
import de.juliandrees.simpleorm.persistence.query.select.Where;
import de.juliandrees.simpleorm.persistence.query.type.EqualityType;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 14.05.2020
 */
public class EntityReferencePropertyMapping extends PropertyMapping {

    public EntityReferencePropertyMapping(FieldMapping fieldMapping) {
        super(fieldMapping);
    }

    @Override
    public void onInitialize() {

    }

    @Override
    public Object getStorableValue(Object value, EntityPersistence persistence) {
        if (value == null) {
            return null;
        }
        EntityScheme scheme = persistence.getEntityManager().getEntityScheme(getFieldMapping().getFieldType());
        Long foreignKey = -1L;
        try {
            foreignKey = (Long) scheme.getPrimaryKeyMapping().getPropertyMapping().getFieldMapping().getGetter().invoke(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return foreignKey;
    }

    @Override
    public Object getEntityValue(Object value, EntityPersistence persistence) {
        if (value == null) {
            return null;
        }
        EntityScheme scheme = persistence.getEntityManager().getEntityScheme(getFieldMapping().getFieldType());
        EntityScheme.PrimaryKeyPropertyMapping primaryKeyMapping = scheme.getPrimaryKeyMapping();
        SelectQueryFactory selectEntity = new SelectQueryFactory().selectFrom(getFieldMapping().getFieldType()).where(Where.of(primaryKeyMapping.getDatabaseColumn(), value, EqualityType.EQUALS));
        return persistence.loadEntity(scheme.getEntityClass(), selectEntity);
    }

    @Override
    public Class<?> getFieldType() {
        return Long.class; // primary key
    }
}

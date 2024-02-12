package org.example.map;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityMapper {
    public <T> T map(ResultSet resultSet, Class<T> classEntity) throws SQLException {
        T entity = null;
        try {
            entity = (T) classEntity.getConstructor().newInstance();

            for (Field field : entity.getClass().getDeclaredFields()) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    try {
                        field.setAccessible(true);
                        if (field.getType() == int.class) {
                            field.set(entity, resultSet.getInt(column.name()));
                        }
                        if (field.getType() == String.class) {
                            field.set(entity, resultSet.getString(column.name()));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }
}
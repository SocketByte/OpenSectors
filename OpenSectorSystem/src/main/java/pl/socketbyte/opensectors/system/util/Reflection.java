package pl.socketbyte.opensectors.system.util;

import java.lang.reflect.Field;

public class Reflection {

    public static Field getField(Class<?> clazz, String name) throws Exception {
        Field field = null;
        try {
            field = clazz.getDeclaredField(name);
        } catch (Exception e) {
            field = clazz.getField(name);
        }
        field.setAccessible(true);
        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & 0xFFFFFFEF);
        return field;
    }

    public static Object get(Class<?> clazz, String name) throws Exception {
        return getField(clazz, name)
                .get(null);
    }

    public static Object get(Object object, String name) throws Exception {
        return getField(object.getClass(), name)
                .get(object);
    }

    public static Object get(Class<?> clazz, Object obj, String name) throws Exception {
        return getField(clazz, name)
                .get(obj);
    }



}

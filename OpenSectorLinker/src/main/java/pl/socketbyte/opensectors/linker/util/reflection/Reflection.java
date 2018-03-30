package pl.socketbyte.opensectors.linker.util.reflection;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Reflection {

    private static String _versionString;
    private static final Class<?> craftPlayerClass = Reflection.getBukkitClass("entity.CraftPlayer");
    private static Method handleMethod;

    static {
        try {
            handleMethod = craftPlayerClass.getMethod("getHandle");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static Object getHandle(Object entity) {
        Object nms_entity = null;
        Method entity_getHandle = getMethod(entity.getClass(), "getHandle");
        try {
            assert entity_getHandle != null;
            nms_entity = entity_getHandle.invoke(entity);
        } catch (IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
        return nms_entity;
    }

    public static Field getField(Class<?> cl, String field_name) {
        try {
            return cl.getDeclaredField(field_name);
        } catch (SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Field getField(Class<?> target, String name, Class<?> fieldType, int index) {
        for (final Field field : target.getDeclaredFields()) {
            if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
                field.setAccessible(true);

                return field;
            }
        }

        if (target.getSuperclass() != null) {
            return getField(target.getSuperclass(), name, fieldType, index);
        }
        throw new IllegalArgumentException("Cannot find field with type " + fieldType);
    }
    public static Method getMethod(Class<?> cl, String method, Class<?>[] args) {
        for (Method m : cl.getMethods()) {
            if (m.getName().equals(method)
                    && ClassListEqual(args, m.getParameterTypes())) {
                return m;
            }
        }
        return null;
    }

    public static Method getMethod(Class<?> cl, String method, Integer args) {
        for (Method m : cl.getMethods()) {
            if (m.getName().equals(method)
                    && args.equals(m.getParameterTypes().length)) {
                return m;
            }
        }
        return null;
    }
    public static Method getMethod(Class<?> cl, String method) {
        for (Method m : cl.getMethods()) {
            if (m.getName().equals(method)) {
                return m;
            }
        }
        return null;
    }

    public static Method getTypedMethod(Class<?> cl, String method, Class<?> type, Class<?>... params) {
        for (final Method m : cl.getDeclaredMethods()) {
            assert m != null;
            if (m.getName().equals(m) && type == null || m.getReturnType().equals(type) && Arrays.equals(m.getParameterTypes(), params)) {
                m.setAccessible(true);
                return m;
            }
        }
        return null;
    }

    public static Object getConstructor(Class<?> clazz, Object... params) {
        for (java.lang.reflect.Constructor<?> cons : clazz.getDeclaredConstructors()) {
            if (Arrays.equals(cons.getParameterTypes(), params)) {
                cons.setAccessible(true);

                try {
                    return cons.newInstance(params);
                } catch (InstantiationException | IllegalAccessException
                        | IllegalArgumentException| InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void setValue(Field field, Object constructor, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(constructor, newValue);
    }

    public static void setValue(Object instance, String fieldName, Object value)
            throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
    }

    public static Object getValue(Object instance, String fieldName)
            throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }

    private static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
        boolean equal = true;

        if (l1.length != l2.length)
            return false;
        for (int i = 0; i < l1.length; i++) {
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
        }

        return equal;
    }

    public synchronized static String getVersion() {
        if(_versionString == null){
            if(Bukkit.getServer() == null){
                return null;
            }
            String name = Bukkit.getServer().getClass().getPackage().getName();
            _versionString = name.substring(name.lastIndexOf('.') + 1) + ".";
        }

        return _versionString;
    }


    private static final Map<String, Class<?>> _loadedNMSClasses = new HashMap<>();

    private static final Map<String, Class<?>> _loadedOBCClasses = new HashMap<>();


    public static Class<?> getCraftClass(final String name) {
        final String className = "net.minecraft.server." + getVersion() + name;
        Class<?> c = null;
        try {
            c = Class.forName(className);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    public static Class<?> getBukkitClass(final String name) {
        final String className = "org.bukkit.craftbukkit." + getVersion() + name;

        Class<?> c = null;
        try {
            c = Class.forName(className);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

}

package pl.socketbyte.opensectors.system.util;

public class ClassUtils {

    public static Class<?> getArrayClass(Class<?> componentType) throws ClassNotFoundException{
        ClassLoader classLoader = componentType.getClassLoader();
        String name;
        if(componentType.isArray()){
            name = "["+componentType.getName();
        }else if(componentType == boolean.class){
            name = "[Z";
        }else if(componentType == byte.class){
            name = "[B";
        }else if(componentType == char.class){
            name = "[C";
        }else if(componentType == double.class){
            name = "[D";
        }else if(componentType == float.class){
            name = "[F";
        }else if(componentType == int.class){
            name = "[I";
        }else if(componentType == long.class){
            name = "[J";
        }else if(componentType == short.class){
            name = "[S";
        }else{
            name = "[L"+componentType.getName()+";";
        }
        return classLoader != null ? classLoader.loadClass(name) : Class.forName(name);
    }

}

package com.bajuh.shearablechickenmod.helper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionUtils {

    public static class InstanceMethod<T> {

        private final Class<?> classType;
        private final String methodName;
        private final Class<?>[] methodParams;

        public InstanceMethod(Class<?> classType, String methodName, Class<?>... methodParams){
            this.classType = classType;
            this.methodName = methodName;
            this.methodParams = methodParams;
        }

        public T invoke(Object instance, Object... args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
        {
            Method m = classType.getDeclaredMethod(methodName, methodParams);
            m.setAccessible(true);
            return (T)m.invoke(instance, args);
        }
    }

    public static void setStaticFinalField(Field field, Object newValue)
        throws NoSuchFieldException, IllegalAccessException
    {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

    public static void setStaticField(Field field, Object newValue) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(null, newValue);
    }

    public static Object getStaticField(Field field) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(null);
    }

    public static Object getInstanceField(Field field, Object instance) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(instance);
    }

    public static void setInstanceFinalField(Field field, Object newValue, Object instance) throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(instance, newValue);
    }

    public static <T> void copyFields(Class<T> type, T fromInstance, T toInstance)
        throws IllegalAccessException, NoSuchFieldException
    {
        for (Field field: type.getDeclaredFields()){
            if ((field.getModifiers() & Modifier.STATIC) == 0){
                Object obj = getInstanceField(field, fromInstance);
                setInstanceFinalField(field, obj, toInstance);
            }
        }
    }
}

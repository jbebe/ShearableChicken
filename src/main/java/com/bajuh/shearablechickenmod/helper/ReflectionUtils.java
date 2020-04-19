package com.bajuh.shearablechickenmod.helper;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Consumer;

// Generic reflection helpers to aid MC source modification
public class ReflectionUtils {

    public static <T> Field getField(final Class<? super T> $class, final String fieldName, final Boolean shouldTranslate)
        throws NoSuchFieldException
    {
        if (shouldTranslate){
            return ObfuscationReflectionHelper.findField($class, fieldName);
        }
        else {
            return $class.getDeclaredField(fieldName);
        }
    }

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
            throws InvocationTargetException, IllegalAccessException
        {
            Method method = ObfuscationReflectionHelper.findMethod(classType, methodName, methodParams);
            method.setAccessible(true);
            return (T)method.invoke(instance, args);
        }
    }

    public static <T> void setStaticFinalField(Class<? super T> $class, String fieldName, Object newValue, Boolean shouldTranslate)
        throws NoSuchFieldException, IllegalAccessException
    {
        Field field = getField($class, fieldName, shouldTranslate);
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

    public static <T> void setStaticField(
        Class<? super T> $class, String fieldName, Object newValue, Boolean shouldTranslate)
        throws IllegalAccessException, NoSuchFieldException
    {
        Field field = getField($class, fieldName, shouldTranslate);
        field.setAccessible(true);
        field.set(null, newValue);
    }

    public static <T> Object getStaticField(Class<? super T> $class, String fieldName, Boolean shouldTranslate)
        throws IllegalAccessException, NoSuchFieldException
    {
        Field field = getField($class, fieldName, shouldTranslate);

        field.setAccessible(true);
        return field.get(null);
    }

    public static <T> Object getInstanceField(
        Class<? super T> $class, String fieldName, Object instance, Boolean shouldTranslate)
        throws IllegalAccessException, NoSuchFieldException
    {
        Field field = getField($class, fieldName, shouldTranslate);
        field.setAccessible(true);
        return field.get(instance);
    }

    public static <T> void setInstanceFinalField(
        Class<? super T> $class, String fieldName, Object newValue, Object instance, Boolean shouldTranslate)
        throws NoSuchFieldException, IllegalAccessException
    {
        Field field = getField($class, fieldName, shouldTranslate);

        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(instance, newValue);
    }

    public static <T> void setInstanceField(
        Class<? super T> $class, String fieldName, Object newValue, Object instance, Boolean shouldTranslate)
        throws IllegalAccessException, NoSuchFieldException
    {
        Field field = getField($class, fieldName, shouldTranslate);
        field.setAccessible(true);
        field.set(instance, newValue);
    }

    public static <T> void copyInstanceFields(Class<T> type, T fromInstance, T toInstance)
        throws IllegalAccessException, NoSuchFieldException
    {
        for (Field field: type.getDeclaredFields()){
            boolean isStatic = (field.getModifiers() & Modifier.STATIC) != 0;
            if (!isStatic){
                field.setAccessible(true);
                Object obj = field.get(fromInstance);

                field.setAccessible(true);
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                field.set(toInstance, obj);
            }
        }
    }

    public static <T> void printFields(Class<? super T> $class, Consumer<String> printCallback){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n%s fields:\n", $class.getName()));
        for (Field f: $class.getDeclaredFields()){
            sb.append(String.format("\t- %s (type: %s)\n", f.getName(), f.getType().getName()));
        }
        printCallback.accept(sb.toString());
    }
}

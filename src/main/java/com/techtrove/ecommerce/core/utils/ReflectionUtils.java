package com.techtrove.ecommerce.core.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.experimental.UtilityClass;
import org.springframework.util.Assert;

@UtilityClass
public class ReflectionUtils {

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo estatico para obtener los atributos  de la clase padre.
     * @Date: 26/07/23
     * @param fromClass
     * @param stopWhenClass
     * @return List<Field>
     */
    public static List<Field> getInheritedDeclaredFields(Class<?> fromClass, Class<?> stopWhenClass) {
        if (stopWhenClass == null) {
            stopWhenClass = Object.class;
        }
        final List<Field> fields = new ArrayList<>();
        final List<Class<?>> classes = new ArrayList<>();

        Class<?> cls = fromClass;
        do {
            classes.add(cls);
            cls = cls.getSuperclass();
        } while (cls != null && !cls.equals(stopWhenClass));

        for (int i = classes.size() - 1; i >= 0; i--) {
            fields.addAll(Arrays.asList(classes.get(i).getDeclaredFields()));
        }

        return fields;
    }

    /**
     *
     * @Author: Miguel A.R.S.
     * @Email: miguel.reyes@spinbyoxxo.com.mx
     * @Description: metodo para asignarle valor a un atributo a travez de reflection
     * @Date: 27/07/23
     * @param target
     * @param name
     * @param value
     */
    public static void setField(Object target, String name, Object value) {
        Assert.notNull(target, "Target object must not be null");
        Field field = org.springframework.util.ReflectionUtils.findField(target.getClass(), name);
        if (field == null) {
            throw new IllegalStateException("Could not find field [" + name + "] on target [" + target + "]");
        }
        org.springframework.util.ReflectionUtils.makeAccessible(field);
        org.springframework.util.ReflectionUtils.setField(field, target, value);
    }

}

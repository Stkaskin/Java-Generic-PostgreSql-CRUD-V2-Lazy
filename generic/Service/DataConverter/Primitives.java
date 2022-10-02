
package com.asstnavi.generic.Service.DataConverter;

import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Contains static utility methods pertaining to primitive types and their
 * corresponding wrapper types.
 *
 * @author Kevin Bourrillion
 */
public final class Primitives
{
    private Primitives()
    {
    }

    /**
     * Returns true if this type is a primitive.
     */
    public static boolean isPrimitive(Type type)
    {
        return type instanceof Class<?> && ((Class<?>) type).isPrimitive();
    }

    /**
     * Returns {@code true} if {@code type} is one of the nine
     * primitive-wrapper types, such as {@link Integer}.
     *
     * @see Class#isPrimitive
     */
    public static boolean isWrapperType(Type type)
    {
        return type == Integer.class
                || type == Float.class
                || type == Byte.class
                || type == Double.class
                || type == Long.class
                || type == Character.class
                || type == Boolean.class
                || type == Short.class
                || type == Void.class;
    }

    /**
     * Returns the corresponding wrapper type of {@code type} if it is a primitive
     * type; otherwise returns {@code type} itself. Idempotent.
     * <pre>
     *     wrap(int.class) == Integer.class
     *     wrap(Integer.class) == Integer.class
     *     wrap(String.class) == String.class
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> wrap(Class<T> type)
    {
        if (type == int.class) return (Class<T>) Integer.class;
        if (type == float.class) return (Class<T>) Float.class;
        if (type == byte.class) return (Class<T>) Byte.class;
        if (type == double.class) return (Class<T>) Double.class;
        if (type == long.class) return (Class<T>) Long.class;
        if (type == char.class) return (Class<T>) Character.class;
        if (type == boolean.class) return (Class<T>) Boolean.class;
        if (type == short.class) return (Class<T>) Short.class;
        if (type == void.class) return (Class<T>) Void.class;
        return type;
    }

    /**
     * Returns the corresponding primitive type of {@code type} if it is a
     * wrapper type; otherwise returns {@code type} itself. Idempotent.
     * <pre>
     *     unwrap(Integer.class) == int.class
     *     unwrap(int.class) == int.class
     *     unwrap(String.class) == String.class
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> unwrap(Class<T> type)
    {
        if (type == Integer.class) return (Class<T>) int.class;
        if (type == Float.class) return (Class<T>) float.class;
        if (type == Byte.class) return (Class<T>) byte.class;
        if (type == Double.class) return (Class<T>) double.class;
        if (type == Long.class) return (Class<T>) long.class;
        if (type == Character.class) return (Class<T>) char.class;
        if (type == Boolean.class) return (Class<T>) boolean.class;
        if (type == Short.class) return (Class<T>) short.class;
        if (type == Void.class) return (Class<T>) void.class;
        return type;
    }


    public static <T> Object Convert(Class<T> type, Object data)
    {
        if (type == Integer.class) return (Integer) (data);
        if (type == String.class) return (String)data;
        if (type == java.sql.Date.class) return new java.sql.Date(((Timestamp) data).getTime());
        if (type == java.util.Date.class) return new java.util.Date(((Timestamp) data).getTime());
        if (type == Float.class) return (Float) (data);
        if (type == Byte.class) return (Byte) (data);
        if (type == Double.class) return (Double) (data);
        if (type == Long.class) return (Long) (data);
        if (type == Character.class) return data;
        if (type == Boolean.class) return (Boolean) (data);
        if (type == Short.class) return (Short) (data);
        if (type == Number.class) return (Number) (data);
        if (type == int.class) return (int) data;
        if (type == float.class) return (float) data;
        if (type == byte.class) return (byte) data;
        if (type == double.class) return (double) data;
        if (type == long.class) return (long) data;
        if (type == char.class) return (char) data;
        if (type == boolean.class) return (boolean) data;
        if (type == short.class) return (short) data;
        return type;
    }
}

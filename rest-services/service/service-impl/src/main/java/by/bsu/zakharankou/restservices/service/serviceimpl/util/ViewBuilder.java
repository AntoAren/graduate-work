package by.bsu.zakharankou.restservices.service.serviceimpl.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ViewBuilder {

    public static <V, E> List<V> build(final List<E> entities, final Class<V> view, final Class<E> entity) {
        final List<V> views = new ArrayList<>();
        for (E item : entities) {
            try {
                final Constructor<V> constructor = view.getConstructor(entity);
                views.add(constructor.newInstance(item));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return views;
    }
}

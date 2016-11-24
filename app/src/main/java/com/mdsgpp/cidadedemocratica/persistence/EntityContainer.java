package com.mdsgpp.cidadedemocratica.persistence;

import com.mdsgpp.cidadedemocratica.model.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by andreanmasiro on 08/11/16.
 */

public class EntityContainer<T extends Entity> {

    private static HashMap<Class, EntityContainer> instances = new HashMap<>();

    public static <T extends Entity> EntityContainer<T> getInstance(Class<? extends Entity> T) {
        EntityContainer<T> instance = instances.get(T);
        if (instance == null) {
            instance = new EntityContainer<>();
            instance.entityType = T;
            instances.put(T, instance);
        } else { }
        return instance;
    }

    private Class<? extends Entity> entityType;

    private ArrayList<T> data = new ArrayList<>();
    private ArrayList<DataUpdateListener> updateListeners = new ArrayList<>();

    private EntityContainer() {
    }

    public ArrayList<T> getAll() {
        return this.data;
    }

    public T getForId(long id) {
        T record = null;

        for (T t : data) {
            if (t.getId() == id) {
                record = t;
                break;
            }
        }

        return record;
    }

    public void add(T t) {
        data.add(t);
        notifyDataUpdate();
    }

    public void addAll(Collection<T> t) {
        data.addAll(t);
        notifyDataUpdate();
    }

    public void setData(Collection<T> t) {
        data = new ArrayList<>(t);
        notifyDataUpdate();
    }

    public void clear() {
        data.clear();
        notifyDataUpdate();
    }

    public void setDataUpdateListener(DataUpdateListener dataUpdateListener) {
        if (!updateListeners.contains(dataUpdateListener)) {
            updateListeners.add(dataUpdateListener);
        } else { }
    }

    public void notifyDataUpdate() {
        for (DataUpdateListener l : updateListeners) {
            l.dataUpdated(entityType);
        }
    }
}

package com.mdsgpp.cidadedemocratica.model;

/**
 * Created by andreanmasiro on 08/11/16.
 */

public class Entity implements Comparable<Entity> {

    private long id;

    public Entity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public int compareTo(Entity entity) {
        return id > entity.id ? (id == entity.id ? 0 : -1) : 1;
    }
}

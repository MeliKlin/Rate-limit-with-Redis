package com.practicing.crudforfun.repositories;

public interface CacheRepository<K, T> {

    void set(K key, T obj);
    T get(K key);

}

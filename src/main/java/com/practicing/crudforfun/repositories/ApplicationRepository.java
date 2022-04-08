package com.practicing.crudforfun.repositories;

public interface ApplicationRepository<K, T> {

    void set(K key, T obj);
    T get(K key);

}

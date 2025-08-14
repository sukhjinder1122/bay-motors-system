package com.baymotors.repo;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    T save(T t);
    Optional<T> findById(ID id);
    boolean existsById(ID id);
    List<T> findAll();
    void deleteById(ID id);
}

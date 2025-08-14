package com.baymotors.repo;

import java.util.*;
import java.util.stream.Collectors;

import com.baymotors.domain.customer.Customer;

public class CustomerRepository implements Repository<Customer, String> {
    private final Map<String, Customer> data = new HashMap<>();

    @Override
    public Customer save(Customer t) {
        data.put(t.getId(), t);
        return t;
    }

    @Override
    public Optional<Customer> findById(String id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return data.containsKey(id);
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void deleteById(String id) {
        data.remove(id);
    }

    public List<Customer> findByRegistered(boolean registered) {
        return data.values().stream().filter(c -> c.isRegistered() == registered).collect(Collectors.toList());
    }
}

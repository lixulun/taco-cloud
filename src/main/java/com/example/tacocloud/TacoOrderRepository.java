package com.example.tacocloud;

import org.springframework.data.repository.CrudRepository;

public interface TacoOrderRepository extends CrudRepository<TacoOrder, Integer> {
}

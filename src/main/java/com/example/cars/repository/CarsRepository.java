package com.example.cars.repository;

import com.example.cars.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CarsRepository extends ElasticsearchRepository<Car, String> {
    Page<Car> findByMake(String make, Pageable pageable);
    List<Car> findByMakeAndModel(String make, String model);
}

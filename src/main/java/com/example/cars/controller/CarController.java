package com.example.cars.controller;

import com.example.cars.model.Car;
import com.example.cars.repository.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarsRepository repository;

    @GetMapping
    public Iterable<Car> getCars() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Car> getCar(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @GetMapping("/make/{make}")
    public Iterable<Car> getCarByMake(@PathVariable("make") String make, Pageable pageable) {
        return repository.findByMake(make, pageable);
    }

    @GetMapping("/make/{make}/model/{model}")
    public Iterable<Car> getCarByMakeAndModel(@PathVariable("make") String make, @PathVariable("model") String model) {
        return repository.findByMakeAndModel(make, model);
    }

    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return repository.save(car);
    }

    @DeleteMapping("{id}")
    public void deleteCar(@PathVariable("id") String id) {
        repository.deleteById(id);
    }
}

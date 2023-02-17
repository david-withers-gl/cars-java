package com.example.cars.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "cars")
public record Car(@Id String id, String colour, String make, String model, String year) {
}

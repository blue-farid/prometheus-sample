package com.bluefarid.prometheussample.controller;

import com.bluefarid.prometheussample.metric.MetricUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/prom")
@RequiredArgsConstructor
public class PrometheusController {
    private final MetricUtil metricUtil;

    @GetMapping
    public ResponseEntity<Void> promTest() {
        try {
            // Simulate random exceptions
            maybeThrowException();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Increment the counter with the specific exception type label
            metricUtil.incrementExceptions(e.getClass().getSimpleName());
            return ResponseEntity.internalServerError().build();
        }
    }

    private void maybeThrowException() throws Exception {
        // Define a list of exception constructors
        List<Supplier<Exception>> exceptions = Arrays.asList(
                () -> new IllegalArgumentException("Illegal argument provided"),
                () -> new IllegalStateException("Illegal state encountered"),
                () -> new RuntimeException("Generic runtime exception"),
                () -> new NullPointerException("Null pointer accessed"),
                () -> new IndexOutOfBoundsException("Index out of bounds"),
                () -> new UnsupportedOperationException("Unsupported operation attempted"),
                () -> new NumberFormatException("Number format exception")
        );

        Random random = new Random();
        // Generate a random index to throw a random exception from the list
        int randomIndex = random.nextInt(exceptions.size());
        throw exceptions.get(randomIndex).get();
    }

}

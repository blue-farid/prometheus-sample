package com.bluefarid.prometheussample.metric;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MetricUtil {
    private final TagCounter exceptions;

    public MetricUtil(MeterRegistry registry) {
        this.exceptions = new TagCounter("exceptions", registry, "type");
    }

    public void incrementExceptions(String tag) {
        exceptions.increment(tag);
    }
}

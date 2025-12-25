package com.dimas519.api2;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.opentracing.shim.OpenTracingShim;
import io.opentracing.util.GlobalTracer;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OtelBridgeConfig {

    @PostConstruct
    public void initBridge() {
        io.opentracing.Tracer shim =
                OpenTracingShim.createTracerShim(GlobalOpenTelemetry.get());
        GlobalTracer.registerIfAbsent(shim);
        System.out.println("✅ Dynatrace–OpenTelemetry bridge aktif");
    }
}
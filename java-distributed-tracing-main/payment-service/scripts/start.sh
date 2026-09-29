#!/bin/bash

# ============================================================
# payment-service startup script with OpenTelemetry Java Agent
# Sends traces to OpenObserve via OTLP HTTP
# ============================================================

export OTEL_SERVICE_NAME=payment-service
export OTEL_RESOURCE_ATTRIBUTES=service.name=payment-service,deployment.environment=dev
export OTEL_TRACES_EXPORTER=otlp
export OTEL_METRICS_EXPORTER=none
export OTEL_LOGS_EXPORTER=otlp
export OTEL_EXPORTER_OTLP_ENDPOINT=http://192.168.223.110:5080/api/_meta
export OTEL_EXPORTER_OTLP_PROTOCOL=http/protobuf
export OTEL_EXPORTER_OTLP_HEADERS="Authorization=Basic dGVzdDpvMm9pX3hKaGRXQ3R3M1VyeXp3TTduTzNleTYzZzZNRktuZFY5"

java \
  -Xms256m \
  -Xmx512m \
  -javaagent:../agents/opentelemetry-javaagent.jar \
  -Dlogging.config=logback-spring.xml \
  -jar target/payment-service-0.0.1-SNAPSHOT.jar


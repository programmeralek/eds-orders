The Orders Service is a Java 25 + Spring Boot 4 microservice responsible for authoritatively managing order creation and persistence within an event-driven architecture.

It enforces ACID-compliant transactional boundaries using Spring Data JPA (Hibernate) with a MySQL datastore managed via Flyway migrations. The service exposes a minimal REST API for order intake and publishes post-commit Kafka domain events, ensuring only durable state changes are propagated.

The service is fully dockerized, registers with Eureka for service discovery, and is designed as an autonomous, single-responsibility component within a polyglot microservices ecosystem.

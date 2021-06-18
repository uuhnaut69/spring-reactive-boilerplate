# Spring Reactive Example
![CircleCI](https://img.shields.io/circleci/build/github/uuhnaut69/spring-reactive-example/master?logo=circleci&style=for-the-badge)
![Codecov](https://img.shields.io/codecov/c/github/uuhnaut69/spring-reactive-example?logo=codecov&style=for-the-badge)
![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-parent?color=green&label=spring-boot&logo=spring-boot&logoColor=green&style=for-the-badge)
![GitHub](https://img.shields.io/github/license/uuhnaut69/spring-reactive-example?style=for-the-badge)

An example spring boot application to demo some concepts:
- Clean Architecture
- Building reactive application with Spring Webflux

## Get started

### 1. Setup environment

```shell
docker-compose up -d
```

### 2. Build project

```shell
./mvnw clean install package
```

### 3. Run test
```shell
./mvnw test
```

### 4. Start application

```shell
./mvnw spring-boot:run
```
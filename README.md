# reactive-spring-transactions [![Build Status](https://travis-ci.org/daggerok/reactive-spring-transactions-example.svg?branch=master)](https://travis-ci.org/daggerok/reactive-spring-transactions-example)

```bash
./mvnw docker:run
./mvnw ; java -jar target/*.jar
http :8080
# ctrl+c
```

_in dev mode_

```bash
./mvnw docker:start
# ...
./mvnw spring-boot:run # ctrl+c
./mvnw docker:stop
```

_other commands_

```bash
./mvnw docker:stop
./mvnw docker:remove
./mvnw docker:volume-remove
```

_cleanup_

```bash
./mvnw -Ddocker.removeMode=all docker:remove
```

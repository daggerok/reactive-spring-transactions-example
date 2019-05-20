Travis CI: https://travis-ci.org/daggerok/reactive-spring-transactions-example

./mvnw docker:run
./mvnw ; java -jar target/*.jar
http :8080
# ctrl+c

# or in dev mode:
./mvnw docker:start
# ...
./mvnw spring-boot:run # ctrl+c
./mvnw docker:stop

# other commands:
./mvnw docker:stop
./mvnw docker:remove
./mvnw docker:volume-remove

# cleanup:
./mvnw -Ddocker.removeMode=all docker:remove


./mvnw docker:run
./mvnw spring-boot:build-info spring-boot:run
# ctrl+c

# or:
./mvnw docker:start
docker rm -f -v mongo-1

# other commands:
./mvnw docker:stop
./mvnw docker:remove
./mvnw docker:volume-remove

# cleanup:
./mvnw -Ddocker.removeMode=all docker:remove

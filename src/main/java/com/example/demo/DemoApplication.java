package com.example.demo;

import lombok.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Data
@Document
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class MyData {
  @Id
  private String id;
  @NonNull
  private String body;
}

@Repository
@Transactional
interface MyDataRepository extends ReactiveMongoRepository<MyData, String> {}

@Component
@RequiredArgsConstructor
class TestData implements ApplicationRunner {

  private final MyDataRepository myData;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Flux.just("one", "two", "three")
        .map(MyData::of)
        .flatMap(myData::save)
        .subscribe(System.out::println);
  }
}

@RestController
@RequiredArgsConstructor
class RestApi {

  private final MyDataRepository repository;

  @GetMapping
  Flux<MyData> index() {
    return repository.findAll();
  }
}

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackageClasses = MyData.class)
public class DemoApplication {
  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}

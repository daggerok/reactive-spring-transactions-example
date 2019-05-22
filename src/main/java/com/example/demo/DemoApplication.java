package com.example.demo;

import lombok.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

@Data
@Document
@Setter(AccessLevel.PROTECTED)
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class MyData {
  @Id
  private String id;
  @NonNull
  private String body;
}

@Repository
interface MyDataRepository extends ReactiveMongoRepository<MyData, String> {}

@Service
@RequiredArgsConstructor
class MyDataService {

  @Getter
  public final MyDataRepository myDataRepository;

  @Transactional
  public Flux<MyData> saveAll(String... args) {
    return Flux.just(args)
               .map(MyData::of)
               .doOnNext(md -> Assert.isTrue(!md.getBody().contains("invalid"),
                                             "body should not contains word: invalid"))
               .flatMap(myDataRepository::save)
        ;
  }
}

@RestController
@RequiredArgsConstructor
class RestApi {

  private final MyDataService myDataService;

  @PostConstruct
  public void init() {
    myDataService.saveAll("one", "two", "three")
                 .subscribe(System.out::println);
  }

  @GetMapping
  public Flux<MyData> index() {
    return myDataService.getMyDataRepository().findAll();
  }
}

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackageClasses = MyData.class)
public class DemoApplication {
  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}

package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MyDataServiceTest {

  @Autowired
  MyDataService myDataService;

  @Autowired
  MyDataRepository myDataRepository;

  @Test
  public void test_that_transaction_does_not_worked() {

    StepVerifier.create(myDataRepository.deleteAll())
                .verifyComplete();

    StepVerifier.create(myDataService.saveAll("one", "two"))
                .expectNextCount(2)
                .verifyComplete();

    StepVerifier.create(myDataRepository.findAll())
                .expectNextCount(2)
                .verifyComplete();

    // here storing data will failed, but arg "1" will be saved...
    StepVerifier.create(myDataService.saveAll("1", "this invalid data should fail")
                                     .log())
                .expectError()
                .verify();

    StepVerifier.create(myDataRepository.findAll()
                                        .log())
                .expectNextCount(3)
                .verifyComplete();
  }
}

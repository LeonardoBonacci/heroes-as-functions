package com.example.ignite;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class App {

  public static void main(String... args) {
    SpringApplication.run(App.class, args);
  }
  
  @Bean
  CommandLineRunner demo(PersonService serv, PersonRepository repo) {
    return args -> {
        repo.deleteById(123L);
        
        Person p = new Person();
        p.setId(123L);
        p.setName("foo");
        p.setEmployed(true);
        repo.save(p.getId(), p);

        log.info("exists {}", repo.existsById(p.getId()));
        repo.findById(p.getId()).ifPresent(pr -> log.info("findById {}", pr));
        repo.findByName(p.getName()).forEach(pr -> log.info("findByName {}", pr));
        
        try {
          serv.insert(p);
        } catch (DataIntegrityViolationException e) {
          log.error(e.getMessage());
        }
        log.info("done");
    };
  }
}

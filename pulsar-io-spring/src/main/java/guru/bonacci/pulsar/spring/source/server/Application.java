package guru.bonacci.pulsar.spring.source.server;

import java.io.Serializable;
import java.util.Optional;

import org.apache.pulsar.functions.api.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.pulsar.spring.source.SpringSource;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@SpringBootApplication
@RequiredArgsConstructor
public class Application implements ApplicationRunner {

  private static final Logger logger = LoggerFactory.getLogger(SpringServer.class);

  private final SpringSource springSource;

  @GetMapping
  public String echo() {
    return "echo 123";
  }

  @GetMapping("/foos/{foo}")
  public String foos(@PathVariable String foo) {
    logger.info("incoming " + foo);
    springSource.consume(new SpringHttpRecord(Optional.ofNullable(""), foo.getBytes()));
    return "there we go " + foo;
  }

  @Override
  public void run(ApplicationArguments args) {
    args.getOptionNames().forEach(System.out::println);
  }
  
  @Data
  static private class SpringHttpRecord implements Record<byte[]>, Serializable {
      private final Optional<String> key;
      private final byte[] value;
  }
}
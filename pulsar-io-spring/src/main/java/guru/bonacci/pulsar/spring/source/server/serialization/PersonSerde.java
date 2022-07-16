package guru.bonacci.pulsar.spring.source.server.serialization;

import java.util.regex.Pattern;

import org.apache.pulsar.functions.api.SerDe;
import org.springframework.stereotype.Component;

@Component
public class PersonSerde implements SerDe<Person> {

  public Person deserialize(byte[] input) {
      String s = new String(input);
      String[] fields = s.split(Pattern.quote("|"));
      return new Person(fields[0], fields[1]);
  }

  public byte[] serialize(Person input) {
    return String.format("%s|%s", input.getId(), input.getName()).getBytes();
  }
}
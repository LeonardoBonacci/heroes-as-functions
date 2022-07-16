package guru.bonacci.pulsar.spring.source.server;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

import guru.bonacci.pulsar.spring.source.SpringSource;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpringSourceInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

    private final SpringSource source;
  
    @Override
    public void initialize(GenericApplicationContext context) {
      context.registerBean(SpringSource.class, () -> source);      
    }
}
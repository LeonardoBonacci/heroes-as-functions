package guru.bonacci.pulsar.spring.source.server;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.google.common.collect.ImmutableMap;

import guru.bonacci.pulsar.spring.source.SpringSource;

public class SpringServer {

    private static final Logger logger = LoggerFactory.getLogger(SpringServer.class);

    private String host;
    private int port;
    private SpringSource springSource;

    private ConfigurableApplicationContext springContext; 

    private SpringServer(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.springSource = builder.springSource;
    }

    public void run() {
        try {
            runForrest();
        } catch (Exception ex) {
            logger.error("Error occurred when Spring Server is running", ex);
            shutdownGracefully();
        } 
    }

    public void shutdownGracefully() {
      logger.info("shutting down...");
      springContext.close();
    }

    private void runForrest() throws InterruptedException {
      logger.info(host + ":" + port);
      SpringApplication app = new SpringApplication(Application.class);
      app.setDefaultProperties(ImmutableMap.of("server.port", this.port, "server.address", this.host));
      app.addInitializers(new SpringSourceInitializer(this.springSource));
      springContext = app.run();
    }
    
    public static class Builder {

        private String host;
        private int port;
        private SpringSource springSource;

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setSpringSource(SpringSource springSource) {
            this.springSource = springSource;
            return this;
        }

        public SpringServer build() {
            checkNotNull(this.host, "host cannot be blank/null");
            checkArgument(this.port >= 1024, "port must be set equal or bigger than 1024");
            checkNotNull(this.springSource, "springSource must be set");

            return new SpringServer(this);
        }
    }
}

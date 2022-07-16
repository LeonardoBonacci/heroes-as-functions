package guru.bonacci.pulsar.spring.source;

import java.util.Map;

import org.apache.pulsar.io.core.PushSource;
import org.apache.pulsar.io.core.SourceContext;
import org.apache.pulsar.io.core.annotations.Connector;
import org.apache.pulsar.io.core.annotations.IOType;

import guru.bonacci.pulsar.spring.source.server.SpringServer;

  @Connector(
    name = "spring",
    type = IOType.SOURCE,
    help = "A Spring connector to listen for incoming messages and write to user-defined Pulsar topic",
    configClass = SpringSourceConfig.class)
public class SpringSource extends PushSource<byte[]> {

    private SpringServer springServer;
    private Thread thread;

    @Override
    public void open(Map<String, Object> config, SourceContext sourceContext) throws Exception {
        SpringSourceConfig springSourceConfig = SpringSourceConfig.load(config);
        if (springSourceConfig.getHost() == null || springSourceConfig.getPort() <= 0) {
            throw new IllegalArgumentException("Required property not set.");
        }

        thread = new Thread(new PulsarServerRunnable(springSourceConfig, this));
        thread.start();
    }

    @Override
    public void close() throws Exception {
        springServer.shutdownGracefully();
    }

    private class PulsarServerRunnable implements Runnable {

        private SpringSourceConfig springSourceConfig;
        private SpringSource springSource;

        public PulsarServerRunnable(SpringSourceConfig  vertxSourceConfig, SpringSource vertxSource) {
            this.springSourceConfig = vertxSourceConfig;
            this.springSource = vertxSource;
        }

        @Override
        public void run() {
            springServer = new SpringServer.Builder()
                    .setHost(springSourceConfig.getHost())
                    .setPort(springSourceConfig.getPort())
                    .setSpringSource(springSource)
                    .build();
            springServer.run();
        }
    }

}
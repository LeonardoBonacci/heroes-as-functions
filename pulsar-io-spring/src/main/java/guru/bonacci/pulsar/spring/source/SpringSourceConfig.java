package guru.bonacci.pulsar.spring.source;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.apache.pulsar.io.core.annotations.FieldDoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SpringSourceConfig implements Serializable {

    private static final long serialVersionUID = -7116130435021510496L;

    @FieldDoc(
            required = true,
            defaultValue = "127.0.0.1",
            help = "The host name or address that the source instance to listen on")
    private String host = "127.0.0.1";

    @FieldDoc(
            required = true,
            defaultValue = "8888",
            help = "The port that the source instance to listen on")
    private int port = 8888;


    public static SpringSourceConfig load(Map<String, Object> map) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new ObjectMapper().writeValueAsString(map), SpringSourceConfig.class);
    }

    public static SpringSourceConfig load(String yamlFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(new File(yamlFile), SpringSourceConfig.class);
    }

}
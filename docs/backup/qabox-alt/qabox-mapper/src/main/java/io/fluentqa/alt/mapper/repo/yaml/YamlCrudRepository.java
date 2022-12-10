package io.fluentqa.alt.mapper.repo.yaml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.fluentqa.alt.mapper.core.Kira;
import io.fluentqa.alt.mapper.core.KiraFactory;
import io.fluentqa.alt.mapper.core.exception.KiraDeserializationException;
import io.fluentqa.alt.mapper.core.exception.KiraSerializationException;
import io.fluentqa.alt.mapper.repo.AbstractMapCrudRepository;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;

public class YamlCrudRepository<EntityType> extends
        AbstractMapCrudRepository<EntityType, String> {

    private static final Kira KIRA = KiraFactory.create().createKira();
    private static final DumperOptions DUMPER_OPTIONS = new DumperOptions();
    private static final Yaml DEFAULT_YAML = new Yaml(DUMPER_OPTIONS);

    static {
        DUMPER_OPTIONS.setPrettyFlow(true);
        DUMPER_OPTIONS.setDefaultFlowStyle(FlowStyle.BLOCK);
    }

    private final Yaml yaml;
    private final Path path;

    /**
     * Create a new yaml repository.
     *
     * @param entityClass The entities class.
     * @param yaml        The yaml instance.
     * @param path        The data file path.
     */
    protected YamlCrudRepository(Class<EntityType> entityClass, Yaml yaml, Path path) {
        super(entityClass, new HashMap<>());
        this.yaml = yaml;
        this.path = path;
    }

    /**
     * Create a new yaml repository.
     *
     * @param entityClass The entities class.
     * @param path        The data file path.
     */
    public YamlCrudRepository(Class<EntityType> entityClass, Path path) {
        this(entityClass, DEFAULT_YAML, path);
    }

    public void load() throws PhoenixYamlIOException {

        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {

            Map<String, Map<String, Object>> map = yaml.loadAs(bufferedReader, Map.class);

            map.forEach((key, value) -> {

                EntityType deserialize = null;
                try {
                    deserialize = KIRA.deserialize(value, getEntityClass());
                } catch (KiraDeserializationException e) {
                    throw new RuntimeException(e);
                }
                getStorage().put(key, deserialize);
            });

        } catch (IOException e) {
            throw new PhoenixYamlIOException("Couldn't load yaml config.", e);
        }
    }

    public void save() throws PhoenixYamlIOException {

        Map<String, Map<String, Object>> documentContent = new HashMap<>();
        Map<String, EntityType> storage = getStorage();

        storage.forEach((key, value) -> {

          Map<String, Object> serialize = null;
          try {
            serialize = KIRA.serialize(value);
          } catch (KiraSerializationException e) {
            throw new RuntimeException(e);
          }
          documentContent.put(key, serialize);
        });

        String document = yaml.dumpAsMap(documentContent);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            bufferedWriter.write(document);
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new PhoenixYamlIOException("Couldn't write yaml config.", e);
        }
    }

    @Override
    protected String nextId() {

        return UUID.randomUUID().toString();
    }
}

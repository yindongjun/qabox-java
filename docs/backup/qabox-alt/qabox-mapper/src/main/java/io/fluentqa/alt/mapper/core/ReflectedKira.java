package io.fluentqa.alt.mapper.core;

import io.fluentqa.alt.mapper.core.exception.KiraDeserializationException;
import io.fluentqa.alt.mapper.core.exception.KiraSerializationException;
import io.fluentqa.alt.mapper.core.meta.ModelMetaRegistry;
import com.google.common.base.Preconditions;
import java.util.Map;
import javax.inject.Inject;

public final class ReflectedKira implements Kira {
  private final ModelMetaRegistry metaRegistry;

  @Inject
  private ReflectedKira(
    ModelMetaRegistry metaRegistry
  ) {
    this.metaRegistry = metaRegistry;
  }

  @Override
  public <ModelT> Map<String, Object> serialize(ModelT model)
    throws KiraSerializationException {
    Preconditions.checkNotNull(model);
    Class<ModelT> modelClass = (Class<ModelT>) model.getClass();
    var meta = metaRegistry.forType(modelClass);
    return KiraSerialization.of(model, meta).execute();
  }

  @Override
  public <ModelT> ModelT deserialize(
    Map<String, Object> data,
    Class<ModelT> modelClass
  ) throws KiraDeserializationException {
    Preconditions.checkNotNull(data);
    Preconditions.checkNotNull(modelClass);
    var meta = metaRegistry.forType(modelClass);
    return KiraDeserialization.of(data, meta).execute();
  }
}

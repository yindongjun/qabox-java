package io.fluentqa.alt.mapper.core;

import io.fluentqa.alt.mapper.core.exception.KiraDeserializationException;
import io.fluentqa.alt.mapper.core.exception.KiraSerializationException;
import java.util.Map;

/**
 * You can serialize objects and deserialize map data from here.
 *
 * <p>{@link #serialize(Object)} {@link #deserialize(Map, Class)}
 */
public interface Kira {
  /**
   * Serialize the given model into a map.
   *
   * @param model    The model.
   * @param <ModelT> The generic type of the model.
   * @return The model as a map.
   */
  <ModelT> Map<String, Object> serialize(
    ModelT model
  ) throws KiraSerializationException;

  /**
   * Deserialize the given data into a model.
   *
   * @param data       The data.
   * @param modelClass The model class.
   * @param <ModelT>   The generic type of the model.
   * @return The model.
   */
  <ModelT> ModelT deserialize(
    Map<String, Object> data,
    Class<ModelT> modelClass
  ) throws KiraDeserializationException;
}

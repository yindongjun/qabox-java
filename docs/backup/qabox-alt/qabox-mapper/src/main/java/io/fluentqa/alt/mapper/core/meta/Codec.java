package io.fluentqa.alt.mapper.core.meta;

import io.fluentqa.alt.mapper.core.exception.KiraCodecException;

public interface Codec<PropertyT> {
  Object serialize(PropertyT value) throws KiraCodecException;

  PropertyT deserialize(Object value) throws KiraCodecException;

  default PropertyT deserialize(Object value, Property<PropertyT> property) throws KiraCodecException {
    return deserialize(value);
  }
}

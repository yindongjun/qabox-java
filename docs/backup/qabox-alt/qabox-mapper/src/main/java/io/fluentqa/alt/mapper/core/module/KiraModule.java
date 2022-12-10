package io.fluentqa.alt.mapper.core.module;

import static java.util.Map.entry;

import io.fluentqa.alt.mapper.core.Kira;
import io.fluentqa.alt.mapper.core.ReflectedKira;
import io.fluentqa.alt.mapper.core.meta.Codec;
import io.fluentqa.alt.mapper.core.meta.CodecFactory;
import io.fluentqa.alt.mapper.core.meta.CodecRegistry;
import io.fluentqa.alt.mapper.core.meta.FunctionalCodec;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import java.util.Map;

public final class KiraModule extends AbstractModule {

  private static final Map<Class<?>, Codec<?>> DEFAULT_CODECS =
    Map.ofEntries(
      entry(String.class,
        FunctionalCodec.of(String::toString, String::valueOf)),
      entry(int.class, FunctionalCodec.of(Integer::intValue,
        value -> Integer.parseInt(value.toString()))),
      entry(Integer.class, FunctionalCodec.of(Integer::intValue,
        value -> Integer.parseInt(value.toString()))),
      entry(float.class, FunctionalCodec.of(Float::floatValue,
        value -> Float.parseFloat(value.toString()))),
      entry(Float.class, FunctionalCodec.of(Float::floatValue,
        value -> Float.parseFloat(value.toString()))),
      entry(double.class, FunctionalCodec.of(Double::doubleValue,
        value -> Double.parseDouble(value.toString()))),
      entry(Double.class, FunctionalCodec.of(Double::doubleValue,
        value -> Double.parseDouble(value.toString()))),
      entry(byte.class, FunctionalCodec.of(Byte::byteValue,
        value -> Byte.parseByte(value.toString()))),
      entry(Byte.class, FunctionalCodec.of(Byte::byteValue,
        value -> Byte.parseByte(value.toString()))),
      entry(short.class, FunctionalCodec.of(Short::shortValue,
        value -> Short.parseShort(value.toString()))),
      entry(Short.class, FunctionalCodec.of(Short::shortValue,
        value -> Short.parseShort(value.toString()))),
      entry(long.class, FunctionalCodec.of(Long::longValue,
        value -> Long.parseLong(value.toString()))),
      entry(Long.class, FunctionalCodec.of(Long::longValue,
        value -> Long.parseLong(value.toString()))),
      entry(boolean.class, FunctionalCodec.of(Boolean::booleanValue,
        value -> Boolean.parseBoolean(value.toString()))),
      entry(Boolean.class, FunctionalCodec.of(Boolean::booleanValue,
        value -> Boolean.parseBoolean(value.toString()))),
      entry(char.class, FunctionalCodec.of(Character::charValue,
        value -> value.toString().charAt(0))),
      entry(Character.class, FunctionalCodec.of(Character::charValue,
        value -> value.toString().charAt(0)))
      );

  private KiraModule() {
  }

  public static KiraModule create() {
    return new KiraModule();
  }

  @Override
  protected void configure() {
    bind(Kira.class).to(ReflectedKira.class).asEagerSingleton();
  }

  @Provides
  public CodecRegistry provideCodecRegistry(CodecFactory codecFactory) {
    return CodecRegistry.withCodecs(DEFAULT_CODECS, codecFactory);
  }
}

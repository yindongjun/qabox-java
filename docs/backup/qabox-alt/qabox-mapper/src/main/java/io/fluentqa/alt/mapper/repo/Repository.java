package io.fluentqa.alt.mapper.repo;

/**
 * A repository defines the entry point for managing an entity.
 */
public interface Repository<EntityType, EntityIdType> {

  /**
   * The class of the entity.
   *
   * @return The class of the entity.
   */
  Class<EntityType> getEntityClass();
}

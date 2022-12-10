package io.fluentqa.alt.mapper.repo;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<EntityType, EntityIdType> extends
    Repository<EntityType, EntityIdType> {

  /**
   * Find a single entity by its.
   *
   * @param id The id of the entity.
   * @return The entity.
   */
  Optional<EntityType> find(EntityIdType id);

  /**
   * Find all entities.
   */
  List<EntityType> findAll();

  /**
   * Save the given entity.
   *
   * @param entity The entity.
   * @return The saved entity.
   */
  EntityType save(EntityType entity);

  /**
   * Delete the given entity.
   *
   * @param entity The entity.
   */
  void delete(EntityType entity);

  /**
   * Delete all entities.
   */
  void deleteAll();

  /**
   * Count all available entities.
   *
   * @return The amount of entities.
   */
  long count();
}

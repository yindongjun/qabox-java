package io.fluentqa.alt.mapper.repo;


public abstract class AbstractCrudRepository<EntityType, EntityIdType> extends
    AbstractRepository<EntityType, EntityIdType> implements
    CrudRepository<EntityType, EntityIdType> {

  /**
   * Create a new repository by the class of the entity it should store.
   *
   * @param entityClass The entities class.
   */
  protected AbstractCrudRepository(Class<EntityType> entityClass) {
    super(entityClass);
  }
}

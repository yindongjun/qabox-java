package io.fluentqa.qabox.jpa.repositories;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import io.fluentqa.qabox.jpa.core.database.PersistentEntitySet;
import io.fluentqa.qabox.jpa.model.user.Role;

public class RoleRepository extends PersistentEntitySet<Role> {

    /**
     * Looks for a Role with a given name.
     * 
     * @param name to look for in role table.
     * @return a Role or empty
     * @throws EntityNotFoundException when no role was found
     */
    public Optional<Role> findByName(String name) {
        try {
            return Optional.of((Role) entityManager()
                    .createQuery(String.format("FROM %s R WHERE R.name LIKE :name", getTableName()))
                    .setParameter("name", name).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}

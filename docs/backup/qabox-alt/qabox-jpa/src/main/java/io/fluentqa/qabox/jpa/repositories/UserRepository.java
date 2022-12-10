package io.fluentqa.qabox.jpa.repositories;

import java.util.Optional;
import io.fluentqa.qabox.jpa.core.database.PersistentEntitySet;
import io.fluentqa.qabox.jpa.model.user.User;

public class UserRepository extends PersistentEntitySet<User> {

    /**
     * Obtains an user from database that matches unique username.
     * 
     * @param username to match
     * @return An authenticated user
     */
    public Optional<User> findByUsername(String username) {
        try {
            return Optional.ofNullable((User) entityManager()
                    .createQuery(
                            String.format("FROM %s U WHERE U.uname LIKE :username", getTableName()))
                    .setParameter("username", username).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

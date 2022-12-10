package io.fluentqa.qabox.jpa.base;

import java.util.function.Supplier;

import javax.persistence.EntityTransaction;



public interface TransactionalOperation extends WithEntityManager {

   default void withTransaction(Runnable action) {
      withTransaction(() -> {
         action.run();
         return null;
      });
   }

   /**
    * Runs an action within a transaction, committing it if action succeeds, or
    * rolling back it otherwise
    * 
    * @param action
    *           the action to execute
    * @return the suppliers result
    * @throws RuntimeException
    *            if actions fails with a RuntimeException
    */
   default <A> A withTransaction(Supplier<A> action) {
      beginTransaction();
      try {
         A result = action.get();
         commitTransaction();
         return result;
      } catch (Throwable e) {
         rollbackTransaction();
         throw e;
      }
   }

   default EntityTransaction getTransaction() {
      return entityManager().getTransaction();
   }

   /**
    * Begins a transaction if there is no active current transaction yet.
    * 
    * Unlike {@link EntityTransaction#begin()}, this method never fails with
    * {@link IllegalStateException}
    * 
    * @see EntityTransaction#begin()
    * @return the current active transaction
    */
   default EntityTransaction beginTransaction() {
      EntityTransaction tx = getTransaction();
      if (!tx.isActive()) {
         tx.begin();
      }
      return tx;
   }

   default void commitTransaction() {
      EntityTransaction tx = getTransaction();
      if (tx.isActive()) {
         tx.commit();
      }
   }

   default void rollbackTransaction() {
      EntityTransaction tx = getTransaction();
      if (tx.isActive()) {
         tx.rollback();
      }
   }
}
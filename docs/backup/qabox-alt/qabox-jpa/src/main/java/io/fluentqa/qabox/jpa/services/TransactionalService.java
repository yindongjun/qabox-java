package io.fluentqa.qabox.jpa.services;


import io.fluentqa.qabox.jpa.base.EntityManagerOperation;
import io.fluentqa.qabox.jpa.base.TransactionalOperation;
import io.fluentqa.qabox.jpa.base.WithGlobalEntityManager;

/**
 * Convenience superclass for Transactional methods.
 * 
 * <br>
 * </br>
 * 
 * It is recommended to develop the business-logic in a service wich extends this convenient super
 * class.
 * 
 */
public abstract class TransactionalService
                implements WithGlobalEntityManager,
        EntityManagerOperation, TransactionalOperation {

}

package io.fluentqa.qabox.jpa.base;


import javax.persistence.EntityManager;

public interface WithEntityManager {

   EntityManager entityManager();
}
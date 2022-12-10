package io.fluentqa.qabox.jpa.base.test;

import io.fluentqa.qabox.jpa.base.EntityManagerOperation;
import io.fluentqa.qabox.jpa.base.TransactionalOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractPersistenceTest implements
		TransactionalOperation, EntityManagerOperation {

	@BeforeEach
	public void setup() {
		beginTransaction();
	}

	@AfterEach
	public void tearDown() {
		rollbackTransaction();
	}

}
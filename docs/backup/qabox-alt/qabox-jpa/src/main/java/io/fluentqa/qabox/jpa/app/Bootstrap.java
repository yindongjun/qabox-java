package io.fluentqa.qabox.jpa.app;

import io.fluentqa.qabox.jpa.app.seeder.AuthSeeder;
import io.fluentqa.qabox.jpa.base.EntityManagerOperation;
import io.fluentqa.qabox.jpa.base.TransactionalOperation;
import io.fluentqa.qabox.jpa.base.WithGlobalEntityManager;

/**
 * Bootstrap data initilizates.
 */
public class Bootstrap implements WithGlobalEntityManager, EntityManagerOperation, TransactionalOperation {

    public static void main(String[] args) {
        new Bootstrap().bootstrapData();
    }

    /**
     * Initializates an admin user.
     */
    public void bootstrapData() {
        new AuthSeeder().seed();
    }
}

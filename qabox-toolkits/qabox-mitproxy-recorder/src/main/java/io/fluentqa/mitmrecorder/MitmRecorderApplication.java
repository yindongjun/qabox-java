package io.fluentqa.mitmrecorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MitmRecorderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MitmRecorderApplication.class, args);
        System.out.println(context);
    }

}

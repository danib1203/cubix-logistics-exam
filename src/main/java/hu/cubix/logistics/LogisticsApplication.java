package hu.cubix.logistics;

import hu.cubix.logistics.service.InitDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogisticsApplication implements CommandLineRunner {

    @Autowired
    InitDbService initDbService;

    public static void main(String[] args) {
        SpringApplication.run(LogisticsApplication.class, args);
    }

    @Override
    public void run(String... args) {

        initDbService.clearDB();
        initDbService.insertTestData();
        initDbService.insertTestUsers();

    }

}

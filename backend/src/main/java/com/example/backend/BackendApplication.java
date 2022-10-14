package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@SpringBootApplication
public class BackendApplication {

    public final static Logger LOGGER = Logger.getLogger(BackendApplication.class.getName());

    public static void main(String[] args) throws IOException {
        FileHandler fh = new FileHandler("logging/file.log", true);
        LOGGER.addHandler(fh);
        fh.setFormatter(new SimpleFormatter());
        LOGGER.setUseParentHandlers(false);
        SpringApplication.run(BackendApplication.class, args);
    }

}

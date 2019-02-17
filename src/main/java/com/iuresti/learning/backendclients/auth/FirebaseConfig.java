package com.iuresti.learning.backendclients.auth;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


@Configuration
public class FirebaseConfig {

    private String databaseUrl = "https://iuresti-test-2.firebaseio.com";
    @Value("config-fire-base.json")
    private String configPath;

    @Bean
    public DatabaseReference firebaseDatabase() {
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        return firebase;
    }

    @PostConstruct
    public void init() throws IOException {

        /**
         * https://firebase.google.com/docs/server/setup
         *
         * Create service account , download json
         */
        //InputStream inputStream = FirebaseConfig.class.getClassLoader().getResourceAsStream(configPath);
        FileInputStream serviceAccount = new FileInputStream(configPath);


        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(databaseUrl)
                .build();

        FirebaseApp.initializeApp(options);

    }
}

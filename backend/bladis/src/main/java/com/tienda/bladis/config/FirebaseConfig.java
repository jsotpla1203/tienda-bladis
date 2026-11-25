package com.tienda.bladis.config;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.v2.ApacheHttpTransport;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FirebaseConfig {

    private final ResourceLoader resourceLoader;

    @Value("${firebase.credentials.path}")
    private String credentialsPath;

    public FirebaseConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct /*Spring lo ejecuta una sola vez justo después de crear el objeto e inyectar el @Value, dejando Firebase listo antes de que cualquier usuario llame a la API*/
    public void inicializarFirebase() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) { /*Comprueba si firebase esta activo, para evitar fallos*/
            Resource resource = resourceLoader.getResource(credentialsPath); /*Localiza y abre el archivo firebase-service-account.json con las credenciales de la cuenta de servicio.*/
            HttpTransport transport = new ApacheHttpTransport(); /*Define el motor de red que usa el SDK de Firebase*/

            /*Empaqueta tus credenciales y el transporte HTTP*/
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(resource.getInputStream(), () -> transport))
                    .setHttpTransport(transport)
                    .build();

            FirebaseApp.initializeApp(options);
        }
    }
}
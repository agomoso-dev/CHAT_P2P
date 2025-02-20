package com.chat.storage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class FirebaseManager {

    /** Propiedades **/
    private static FirebaseManager instance;            // Singleton
    private Firestore db;                               // Conexión a la base de datos

    /** Constructor privado del Singleton **/
    private FirebaseManager() {
        connect();
    }

    /**
     * Devuelve el Singleton de FirebaseManager
     * @return Singleton de FirebaseManager
     */
    public static synchronized FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }

    /**
     * Realiza la conexión con Firebase Firestore
     */
    private void connect() {
        try {
            FileInputStream serviceAccount = new FileInputStream("serviceAccountKey.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();
            System.out.println("Conexión a Firestore establecida correctmente.");
        } catch (IOException e) {
            System.err.println("Error al conectarse a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        FirebaseManager dbManager = FirebaseManager.getInstance();

    }
}

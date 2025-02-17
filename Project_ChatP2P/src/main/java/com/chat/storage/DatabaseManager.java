package com.chat.storage;

import java.sql.*;

import static com.chat.utils.Constants.CONTACTS_TABLE;
import static com.chat.utils.Constants.DB_URL;

public class DatabaseManager {

    /** Propiedades **/
    private static DatabaseManager instance;          // Singleton
    private Connection conn;                          // Conexión a la base de datos

    /** Constructor privado del Singleton **/
    private DatabaseManager() throws SQLException {
        initializeDatabase();
        System.out.println("BD inicializada");
    }

    /**
     * Devuelve el Singleton de DatabaseManager
     * @return Singleton de DatabaseManager
     */
    public static synchronized DatabaseManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Inicializa la base de datos SQLite
     */
    private void initializeDatabase() throws SQLException {
        connect();
        createTables();
    }

    /**
     * Establece la conexión con la base de datos SQlite
     */
    private void connect() throws SQLException {
        if (conn != null) {
            return;
        }

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:chat.db");
            System.out.println("CONEXION HECHA");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró la clase del driver JDBC de SQLite.");
            e.printStackTrace();
        } catch(SQLException e) {
            System.err.println("Error conectando a la base de datos: " + e.getMessage());
            throw e;
        }
    }


    /**
     * Verifica si existen las tablas necesarias y las crea si es necesario
     * @throws SQLException si hay error
     */
    private void createTables() throws SQLException {
        String sql = "";

        if (!tableExists(CONTACTS_TABLE)) {
            sql = "CREATE TABLE " + CONTACTS_TABLE + " ("
                    + "	peer_id TEXT PRIMARY KEY,"
                    + "	username TEXT,"
                    + "	last_connection TIMESTAMP"
                    + ");";

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
        } else {
            System.out.println("EXISTE");
        }
    }

    /**
     * Verifica si existe una tabla
     * @param tableName Nombre de la tabla
     * @return True si existe
     */
    public boolean tableExists(String tableName) {
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
            return rs.next();
        } catch(SQLException ex) {
            System.out.println("Error al verificar si existe la tabla " + tableName + ": " + ex.getMessage());
            return false;
        }
    }

    /**
     * Cierra la conexión con la base de datos
     */
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println("Error cerrando la conexión " + ex.getMessage());
            }

        }
    }

    public static void main(String[] args) throws SQLException {
        DatabaseManager dbManager = DatabaseManager.getInstance();

    }
}

package com.chat.utils;

/**
 * Clase de constantes
 */
public class Constants {

    /** Configuración de puertos **/
    public static final int MIN_PORT = 1024;
    public static final int MAX_PORT = 65535;

    /** CONFIGURACIÓN DE SQLITE **/
    /** URL de SQLite **/
    public static final String DB_URL = "jdbc:sqlite:chat.db";
    public static final String CONTACTS_TABLE = "contacts";

}

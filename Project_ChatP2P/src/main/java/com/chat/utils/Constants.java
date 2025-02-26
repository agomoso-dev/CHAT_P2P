package com.chat.utils;

/**
 * Clase de constantes
 */
public class Constants {

    /** Configuración de puertos **/
    public static final int MIN_PORT = 1024;
    public static final int MAX_PORT = 65535;

    /** Configuracion de Avatars **/
    public static final long MAX_FILE_SIZE = 5 * 1024 * 1024;              // 5MB máximo
    public static final int MAX_DIMENSION = 1024;                          // 1024px máximo
    public static final int MIN_DIMENSION = 100;                           // 100px mínimo
    public static final String[] ALLOWED_FORMATS = {"jpg", "jpeg", "png"};

}

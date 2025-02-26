package com.chat.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;

import static com.chat.utils.Constants.*;

public class Avatar implements Serializable {

    /** Propiedades **/
    private String localPath;          // Ruta local de la imagen
    private String storageUrl;         // URL en Firebase Storage
    private byte[] imageData;          // Datos de la imagen en memoria

    /** Constructor por parámetros **/
    public Avatar(String localPath) throws IOException {
        if (!isValid(localPath)) {
            throw new IllegalArgumentException("Ruta no válida");
        }
        this.localPath = localPath;
        loadImage();
    }

    /**
     * Comprueba si una imagen es válida
     * @return True si es válida
     * @throws IOException si no cumple algunos de los requisitos
     */
    public boolean isValid(String localPath) throws IOException {

        if (!exists(localPath)) {
            throw new IllegalArgumentException("El archivo no existe");
        }

        if (!isValidFileSize(localPath)) {
            throw new IllegalArgumentException("El archivo excede el tamaño máximo de 5MB");
        }

        if (!isValidFormat(localPath)) {
            throw new IllegalArgumentException("Formato no permitido. Use: jpg, jpeg o png");
        }

        if (!isValidDimensions(localPath)) {
            throw new IllegalArgumentException(
                    "Las dimensiones deben estar entre 100px y 1024px"
            );
        }

        if (!isImageReadable(localPath)) {
            throw new IllegalArgumentException("La imagen está corrupta o no es válida");
        }

        return true;
    }

    /**
     * Comprueba si una imágen existe
     * @param localPath Ruta de la imágen
     * @return True si existe
     */
    private boolean exists(String localPath) {
        return new File(localPath).exists();
    }

    /**
     * Comprueba si una imágen tiene un tamaño válido
     * @param localPath Ruta de la imágen
     * @return True si es válido
     */
    private boolean isValidFileSize(String localPath) {
        return new File(localPath).length() <= MAX_FILE_SIZE;
    }

    /**
     * Comprueba si una imagen tiene un formato válido
     * @param localPath Ruta de la imágen
     * @return True si es válido
     */
    private boolean isValidFormat(String localPath) {
        String extension = localPath.substring(localPath.lastIndexOf(".") + 1).toLowerCase();
        return Arrays.asList(ALLOWED_FORMATS).contains(extension);
    }

    /**
     * Comprueba si una imagen tiene las dimensiones adecuadads
     * @param localPath Ruta de la imagen
     * @return
     * @throws IOException
     */
    private boolean isValidDimensions(String localPath) throws IOException {
        BufferedImage image = ImageIO.read(new File(localPath));
        int width = image.getWidth();
        int height = image.getHeight();

        return width >= MIN_DIMENSION && width <= MAX_DIMENSION &&
                height >= MIN_DIMENSION && height <= MAX_DIMENSION;
    }

    /**
     * Comprueba si una imágen es legible
     * @param localPath Ruta de la imágen
     * @return True si es legible
     */
    private boolean isImageReadable(String localPath) {
        try {
            BufferedImage image = ImageIO.read(new File(localPath));
            return image != null;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Convierte el avatar de un usuario en bytes
     */
    public void loadImage() throws IOException {
        File avatarFile = new File(localPath);
        this.imageData = Files.readAllBytes(avatarFile.toPath());
    }

    /**
     * Convierte un avatar a Base64
     * @return Código Base64
     */
    public String toBase64() {
        if (imageData == null) {
            throw new IllegalStateException("Imagen no cargada.");
        }

        return Base64.getEncoder().encodeToString(imageData);
    }

    /**
     * Getters y Setters
     */
    public String getLocalPath(){
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getStorageUrl(){
        return storageUrl;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}

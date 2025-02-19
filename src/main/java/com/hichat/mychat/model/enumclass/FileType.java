package com.hichat.mychat.model.enumclass;

import org.springframework.http.MediaType;

import java.util.Arrays;

public enum FileType {
    // Enum constants representing file types with their extensions and corresponding media types
    JPG("jpg", MediaType.IMAGE_JPEG),
    JPEG("jpeg", MediaType.IMAGE_JPEG),
    PNG("png", MediaType.IMAGE_PNG),
    HEIF("heif", MediaType.valueOf("image/heif")),
    HEIC("heic", MediaType.valueOf("image/heic")),
    GIF("gif", MediaType.IMAGE_GIF),
    BMP("bmp", MediaType.valueOf("image/bmp")),
    TIF("tif", MediaType.valueOf("image/tiff")),
    TIFF("tiff", MediaType.valueOf("image/tiff")),
    WEBP("webp", MediaType.valueOf("image/webp"));

    // File extension
    private final String extension;

    // Media type associated with the file extension
    private final MediaType mediaType;

    FileType(String txt, MediaType textPlain) {
        this.extension = txt;
        this.mediaType = textPlain;
    }

    // Method to get MediaType based on the filename's extension
    public static MediaType fromFilename(String fileName) {
        // Finding the last index of '.' to get the extension
        int dotIndex = fileName.lastIndexOf('.');
        // Extracting file extension from filename
        String fileExtension = (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
        // Finding matching enum constant for the file extension
        return Arrays.stream(values())
                .filter(e -> e.getExtension().equals(fileExtension))
                .findFirst()
                .map(FileType::getMediaType)
                .orElse(MediaType.APPLICATION_OCTET_STREAM); // Default to octet-stream if no matching media type found
    }

    public String getExtension() {
        return extension;
    }

    public MediaType getMediaType() {
        return mediaType;
    }
}
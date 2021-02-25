package com.leodelmiro.mercadolivre.newproduct;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface Uploader {

    /**
     *
     * @param images
     * @return links para imagens que foram uploadadas
     */
    Set<String> send(List<MultipartFile> images);

}
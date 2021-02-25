package com.leodelmiro.mercadolivre.newproduct;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Primary
public class UploaderFake implements Uploader{

    /**
     * @param images
     * @return links para imagens que foram uploadadas
     */
    @Override
    public Set<String> send(List<MultipartFile> images) {
        return images.stream()
                .map(image -> "http://bucket.io/" + image.getOriginalFilename())
                .collect(Collectors.toSet());
    }
}

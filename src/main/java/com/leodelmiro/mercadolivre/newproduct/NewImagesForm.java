package com.leodelmiro.mercadolivre.newproduct;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class NewImagesForm {

    @Size(min = 1)
    @NotNull
    private List<MultipartFile> images = new ArrayList<>();

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public boolean hasNullFile() {
        for (MultipartFile file : images) {
            if (file.getOriginalFilename() == null || file.getOriginalFilename().equals("")) {
                return true;
            }
        }
        return false;
    }
}

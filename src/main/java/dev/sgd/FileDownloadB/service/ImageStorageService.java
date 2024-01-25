package dev.sgd.FileDownloadB.service;

import dev.sgd.FileDownloadB.entity.ImageData;
import dev.sgd.FileDownloadB.repository.ImageStorageRepository;
import dev.sgd.FileDownloadB.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.io.IOException;

@Service
public class ImageStorageService {

    @Autowired
    private ImageStorageRepository repository;

    public String uploadImage(MultipartFile image) throws IOException {
        ImageData imageData = repository.save(ImageData.builder()
                .name(image.getOriginalFilename())
                .type(image.getContentType())
                .imageData(ImageUtils.compressImage(image.getBytes()))
                .build());
        if(imageData != null){
            return "File uploaded successfully :" + image.getOriginalFilename();
        }
        else{
            return null;
        }
    }

    public byte[] downloadImage(String fileName){
        Optional<ImageData> imageData = repository.findByName(fileName);
        return ImageUtils.decompressImage(imageData.get().getImageData());
    }
}

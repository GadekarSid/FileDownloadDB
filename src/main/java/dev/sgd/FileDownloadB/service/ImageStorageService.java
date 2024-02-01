package dev.sgd.FileDownloadB.service;

import dev.sgd.FileDownloadB.entity.FileData;
import dev.sgd.FileDownloadB.entity.ImageData;
import dev.sgd.FileDownloadB.repository.FileDataRepository;
import dev.sgd.FileDownloadB.repository.ImageStorageRepository;
import dev.sgd.FileDownloadB.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;
import java.io.IOException;

@Service
public class ImageStorageService {

    @Autowired
    private ImageStorageRepository repository;
    @Autowired
    private FileDataRepository fileDataRepository;

    private final String FOLDER_PATH="C:\\Projects\\";

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

    public String uploadImageToFileSystem(MultipartFile file) throws  IOException{
        String filePath = FOLDER_PATH + file.getOriginalFilename();
        FileData fileData = fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                        .filePath(filePath)
                .build());

        file.transferTo(new File(filePath));
        if(fileData != null){
            return "File uploaded successfully :" + filePath;
        }
        else{
            return null;
        }
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath = fileData.get().getFilePath();
        byte[] image = Files.readAllBytes(new File(filePath).toPath());
        return image;
    }
}

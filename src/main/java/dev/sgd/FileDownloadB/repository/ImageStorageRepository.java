package dev.sgd.FileDownloadB.repository;

import dev.sgd.FileDownloadB.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageStorageRepository  extends JpaRepository<ImageData,Long> {
    Optional<ImageData> findByName(String fileName);
}

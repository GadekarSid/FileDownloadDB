package dev.sgd.FileDownloadB;

import dev.sgd.FileDownloadB.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@SpringBootApplication
@RestController
@RequestMapping("/image")
public class FileDownloadBApplication {

	@Autowired
	private ImageStorageService service;

	@PostMapping
	public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
		String uploadImageResponse = service.uploadImage(file);
		return ResponseEntity.status(HttpStatus.OK).body(uploadImageResponse);
	}

	@GetMapping("/{fileName}")
	public ResponseEntity<?> downloadImage(@PathVariable String fileName) throws IOException {
		byte[] image = service.downloadImage(fileName);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png"))
				.body(image);
	}

	public static void main(String[] args) {
		SpringApplication.run(FileDownloadBApplication.class, args);
	}

}

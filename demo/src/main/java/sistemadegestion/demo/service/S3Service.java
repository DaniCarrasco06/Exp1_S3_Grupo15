package sistemadegestion.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String subirArchivo(MultipartFile archivo) throws IOException {

        Path tempFile = Files.createTempFile("upload-", archivo.getOriginalFilename());

        archivo.transferTo(tempFile.toFile());

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(archivo.getOriginalFilename())
                        .build(),
                tempFile);

        return archivo.getOriginalFilename();
    }
}
package sistemadegestion.demo.controller;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sistemadegestion.demo.dto.S3ObjectDto;
import sistemadegestion.demo.service.AwsS3Service;
import sistemadegestion.demo.service.EfsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;
    private final EfsService efsService;

    @GetMapping("/{bucket}/objects")
    public ResponseEntity<List<S3ObjectDto>> listObjects(@PathVariable String bucket) {
        return ResponseEntity.ok(awsS3Service.listObjects(bucket));
    }

    @GetMapping("/{bucket}/object")
    public ResponseEntity<byte[]> downloadObject(@PathVariable String bucket, @RequestParam String key) {
        byte[] fileBytes = awsS3Service.downloadAsBytes(bucket, key);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(fileBytes);
    }

    @PostMapping("/{bucket}/guia")
    public ResponseEntity<Void> subirGuia(
            @PathVariable String bucket,
            @RequestParam String transportista,
            @RequestParam String fecha,
            @RequestParam String numeroGuia,
            @RequestParam("file") MultipartFile file) {
        try {
            String key = fecha + "/" + transportista + "/" + numeroGuia;
            efsService.saveToEfs(key, file);
            awsS3Service.upload(bucket, key, file);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{bucket}/guias")
    public ResponseEntity<List<S3ObjectDto>> listarGuias(
            @PathVariable String bucket,
            @RequestParam String transportista,
            @RequestParam String fecha) {
        return ResponseEntity.ok(awsS3Service.listarGuiasPorTransportistaYFecha(bucket, transportista, fecha));
    }

    @PutMapping("/{bucket}/guia")
    public ResponseEntity<Void> moverGuia(
            @PathVariable String bucket,
            @RequestParam String sourceKey,
            @RequestParam String destKey) {
        awsS3Service.moveObject(bucket, sourceKey, destKey);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{bucket}/guia")
    public ResponseEntity<Void> eliminarGuia(
            @PathVariable String bucket,
            @RequestParam String key) {
        awsS3Service.deleteObject(bucket, key);
        return ResponseEntity.noContent().build();
    }
}

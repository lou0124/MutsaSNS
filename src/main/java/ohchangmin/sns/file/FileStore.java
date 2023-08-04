package ohchangmin.sns.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileStore {

    String storeFile(MultipartFile file);

    void deleteFile(String filePath);
}

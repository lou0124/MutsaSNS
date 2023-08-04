package ohchangmin.sns.file;

import lombok.extern.slf4j.Slf4j;
import ohchangmin.sns.exception.FileIsEmpty;
import ohchangmin.sns.exception.FileStoreFail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class FileStoreImpl implements FileStore {

    @Value("${file.dir}")
    private String fileDir;

    @Override
    public String storeFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new FileIsEmpty();
        }

        String storedName = createStoreFileName(file.getOriginalFilename());
        String pathName = fileDir + storedName;
        try {
            file.transferTo(new File(pathName));
        } catch (IOException e) {
            log.info("파일 저장 실패", e);
            throw new FileStoreFail(e);
        }
        return pathName;
    }

    @Override
    public void deleteFile(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            if (file.delete()) {
                log.info("파일 삭제 성공");
            } else {
                log.info("파일 삭제 실패");
            }
        } else {
            log.info("해당 경로에 파일이 존재하지 않습니다.");
        }
    }

    private String createStoreFileName(String originalFilename) {
        return UUID.randomUUID() + "." + getExtension(originalFilename);
    }

    private String getExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }
}

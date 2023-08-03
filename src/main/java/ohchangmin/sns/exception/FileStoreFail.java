package ohchangmin.sns.exception;

public class FileStoreFail extends CustomException {

    private static final String MESSAGE = "파일 저장에 실패하였습니다.";

    public FileStoreFail(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 500;
    }
}

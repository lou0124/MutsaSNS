package ohchangmin.sns.exception;

public class FileIsEmpty extends CustomException {

    private static final String MESSAGE = "요청 파일이 존재하지 않습니다.";

    public FileIsEmpty() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}

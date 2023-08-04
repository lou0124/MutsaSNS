package ohchangmin.sns.exception;

public class AlreadyDeletedArticle extends CustomException {

    private static final String MESSAGE = "이미 삭제된 피드입니다.";

    public AlreadyDeletedArticle() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}

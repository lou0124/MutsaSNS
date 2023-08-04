package ohchangmin.sns.exception;

public class NotFoundArticle extends CustomException {

    private static final String MESSAGE = "존재하지 않는 피드입니다.";

    public NotFoundArticle() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}

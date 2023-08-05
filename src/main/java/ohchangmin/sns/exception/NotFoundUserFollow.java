package ohchangmin.sns.exception;

public class NotFoundUserFollow extends CustomException {

    private static final String MESSAGE = "존재하지 않는 팔로우입니다.";

    public NotFoundUserFollow() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}

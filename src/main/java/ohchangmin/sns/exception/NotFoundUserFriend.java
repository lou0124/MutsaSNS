package ohchangmin.sns.exception;

public class NotFoundUserFriend extends CustomException {

    private static final String MESSAGE = "존재하지 않는 피드 이미지입니다.";

    public NotFoundUserFriend() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}

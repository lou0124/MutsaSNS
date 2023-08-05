package ohchangmin.sns.exception;

public class NotAllowFollowSelf extends CustomException {

    private static final String MESSAGE = "자기 자신을 팔로우 할 수 없습니다.";

    public NotAllowFollowSelf() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}

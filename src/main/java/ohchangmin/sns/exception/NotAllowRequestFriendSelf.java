package ohchangmin.sns.exception;

public class NotAllowRequestFriendSelf extends CustomException {

    private static final String MESSAGE = "자기 자신에게 친구 요청을 할 수 없습니다.";

    public NotAllowRequestFriendSelf() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}

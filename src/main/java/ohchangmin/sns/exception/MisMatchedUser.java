package ohchangmin.sns.exception;

public class MisMatchedUser extends CustomException {

    private static final String MESSAGE = "유저가 일치하지 않습니다.";

    public MisMatchedUser() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}

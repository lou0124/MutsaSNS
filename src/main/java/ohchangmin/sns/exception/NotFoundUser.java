package ohchangmin.sns.exception;

public class NotFoundUser extends CustomException {

    private static final String MESSAGE = "존재하지 않는 유저입니다.";

    public NotFoundUser() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}

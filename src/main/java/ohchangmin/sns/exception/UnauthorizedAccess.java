package ohchangmin.sns.exception;

public class UnauthorizedAccess extends CustomException {

    private static final String MESSAGE = "접근 권한이 없습니다.";

    public UnauthorizedAccess() {
        super(MESSAGE);
    }

    public UnauthorizedAccess(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}

package ohchangmin.sns.exception;

public class AlreadyAccepted extends CustomException {

    private static final String MESSAGE = "이미 수락된 요청입니다.";

    public AlreadyAccepted() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}

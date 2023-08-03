package ohchangmin.sns.exception;

public class UsernameAlreadyExists extends CustomException {

    private static final String MESSAGE = "이미 판매 된 상품입니다.";

    public UsernameAlreadyExists() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}

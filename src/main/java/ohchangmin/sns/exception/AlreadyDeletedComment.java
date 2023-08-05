package ohchangmin.sns.exception;

public class AlreadyDeletedComment extends CustomException {

    private static final String MESSAGE = "이미 삭제된 댓글입니다.";

    public AlreadyDeletedComment() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}

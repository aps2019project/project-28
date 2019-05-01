package exeption;

public class InvalidCellException extends Exception {
    private final String detailMessage = "The cell you have chosen is not valid , Please try again";
        @Override
    public String getMessage() {
        return super.getMessage();
    }
}

package exeption;

public class InvalidCellException extends Exception {
    private final String detailMessage ;

    public InvalidCellException(){ this.detailMessage =  "The cell you have chosen is not valid , Please try again";} ;
    public InvalidCellException(String message) {this.detailMessage = message ;}

        @Override
    public String getMessage() {
        return detailMessage;
    }
}

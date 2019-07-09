package exeption;

public class BuffHasntBeenDeployedYetException extends Exception {

    public BuffHasntBeenDeployedYetException(String message) {
        super(message);
    }

    public BuffHasntBeenDeployedYetException() {
    }
}

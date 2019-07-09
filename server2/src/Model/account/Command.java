package Model.account;

public class Command {
    Account owner;
    private String command;
    private long time;

    public Command(String command,Account owner) {
        this.owner=owner;
        this.command = command;
        this.time = System.currentTimeMillis();
    }
    public Command(String command,Account owner,long time) {
        this.owner=owner;
        this.command = command;
        this.time = time;
    }

    public String getCommand() {
        return command;
    }

    public long getTime() {
        return time;
    }
}

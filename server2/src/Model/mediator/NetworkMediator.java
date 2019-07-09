package Model.mediator;

import network.Message;

public interface NetworkMediator {
    static  boolean isValid(Message message) throws Exception {
        if (message == null) {
            return false;
        }
        if (message.getCarry().size()>0 && message.getCarry().get(0) instanceof Exception) {
            throw (Exception) message.getCarry().get(0);
        }
        return true;
    }

}

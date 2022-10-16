package eapli.base.infrastructure.spomsp.commonrequest;

import eapli.base.warehouse.application.AssignOrderToAgvController;

public abstract class ProtocolV1Request {

    protected final String request;
    protected final AssignOrderToAgvController controller;

    protected ProtocolV1Request(final AssignOrderToAgvController controller, final String inputRequest) {
        this.request = inputRequest;
        this.controller = controller;
    }

    /**
     * Executes the requested action and builds the response to the client.
     *
     * @return the response to send back to the client
     */
    public abstract String execute();

    /**
     * Indicates the object is a goodbye message, that is, a message that will close the
     * connection to the client.
     *
     * @return {@code true} if the object is a a goodbye message.
     */
    public boolean isGoodbye() {
        return false;
    }

    public boolean isToDigitalTwin(){
        return false;
    }

    public byte[] header() {
        return null;
    }
}

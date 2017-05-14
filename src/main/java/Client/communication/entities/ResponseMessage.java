package Client.communication.entities;

/**
 * Created by user on 12/05/2017.
 */
public class ResponseMessage {
    String message;
    Object data;

    public ResponseMessage(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}

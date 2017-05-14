package TexasHoldem.communication.entities;

import org.springframework.http.HttpStatus;

/**
 * Created by user on 12/05/2017.
 */
public class ResponseMessage<T> {
    String message;
    T data;

    public ResponseMessage(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ResponseMessage() {
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}

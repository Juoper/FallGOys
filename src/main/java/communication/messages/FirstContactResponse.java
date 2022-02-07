package communication.messages;

import java.io.Serializable;

public class FirstContactResponse implements Serializable {


    public enum ResponseCodes{
        SUCCESSFULL, WRONG_MESSAGE, NO_MESSAGE, NAME_ALREADY_EXISTS, INVALID_NAME, TOO_MANY_CLIENTS
    }

    private final ResponseCodes responseCode;

    public FirstContactResponse( ResponseCodes responseCode) {

        this.responseCode = responseCode;
    }
    public ResponseCodes getResponseCode() {
        return responseCode;
    }

}
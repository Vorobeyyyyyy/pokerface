package by.bsuir.pokerface.responce;

public class ResponseWithStatus extends AbstractResponse {
    public String status;

    public ResponseWithStatus(String status) {
        this.status = status;
    }
}

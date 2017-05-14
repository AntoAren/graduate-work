package by.bsu.zakharankou.restservices.model.result;

public enum ResultStatus {
    IN_PROGRESS("IN_PROGRESS"), COMPLETED("COMPLETED");

    ResultStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return status;
    }
}

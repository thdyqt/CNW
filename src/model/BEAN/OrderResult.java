package model.BEAN;

public class OrderResult {
    private boolean success;
    private String message;
    private String orderCode;

    public OrderResult() {}

    public OrderResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public OrderResult(boolean success, String message, String orderCode) {
        this.success = success;
        this.message = message;
        this.orderCode = orderCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}

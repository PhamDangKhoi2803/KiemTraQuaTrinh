package vn.iotstar.ui_gk.model;

public class OtpRequest {
    private String email;
    private String otp;
    private String password; // Thêm password

    public OtpRequest(String email, String otp, String password) {
        this.email = email;
        this.otp = otp;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getOtp() {
        return otp;
    }

    public String getPassword() {
        return password; // Getter cho password
    }
}

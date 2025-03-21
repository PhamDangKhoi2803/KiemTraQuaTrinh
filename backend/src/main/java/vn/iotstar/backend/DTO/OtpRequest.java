package vn.iotstar.backend.DTO;

public class OtpRequest {
    private String email;
    private String otp;
    private String password; // Thêm password

    // Constructors
    public OtpRequest() {}

    public OtpRequest(String email, String otp, String password) {
        this.email = email;
        this.otp = otp;
        this.password = password;
    }

    // Getters & Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }

    public String getPassword() { return password; } // Getter cho password
    public void setPassword(String password) { this.password = password; } // Setter cho password
}

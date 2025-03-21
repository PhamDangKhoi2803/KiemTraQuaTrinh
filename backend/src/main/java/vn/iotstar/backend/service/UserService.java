package vn.iotstar.backend.service;


import vn.iotstar.backend.model.LoginModel;
import vn.iotstar.backend.model.RegisterModel;

public interface UserService {
    String register(RegisterModel registerModel);
    String verifyAccount(String email, String otp);
    String regenerateOtp(String email);
    String login(LoginModel loginModel);
}